import Actuators.ActuatorAction;
import Models.AbstractActuator;
import Models.AbstractSensor;
import infrastructure.*;
import infrastructure.Action;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.plaf.ComponentUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Timer;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//Quando crio a acao nao esta a ver se esta ser comprida ou nao
public class MainScreenTest extends JFrame {
    private JPanel panelMain;
    private JList actuatorsList;
    private JList sensorsList;
    private JList actionsList;
    private JButton newActionButton;
    private JTextField actionName;
    private JButton saveButton;
    private JButton deleteButton;
    private JComboBox operatorSelection;
    private JTextField controlValueInput;
    private JComboBox sensorSelection;
    private JTextField sensorReading;
    private JTextField actuatorState;
    private JComboBox actActionSelection;
    private JPanel actuatorsOptions;
    private JTextField acActionOption;
    private static List<AbstractActuator> actuatorList= Collections.synchronizedList(new ArrayList<AbstractActuator>());
    private static List<AbstractSensor> sensorList= Collections.synchronizedList(new ArrayList<AbstractSensor>());
    private ArrayList<Action> actionList;
    private DefaultListModel actuatorListModel;
    private DefaultListModel actionListModel;
    private DefaultListModel sensorListModel;

    //Instanciar o hub
    SensorReadingsHub sensorReadingsHub= HubProvider.getReadingHub();

    MainScreenTest() throws IOException {
        super("ADS House Controller");
        this.setContentPane(this.panelMain);
        //this.setPreferredSize(new Dimension(500,1000));
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();

        actuatorListModel = new DefaultListModel();
        actuatorsList.setModel(actuatorListModel);

        sensorListModel = new DefaultListModel();
        sensorsList.setModel(sensorListModel);

        actionList = new ArrayList<>();
        actionListModel = new DefaultListModel();
        actionsList.setModel(actionListModel);

        load();

        sensorReading.setEditable(false);

        actuatorsList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int actuatorNumber = actuatorsList.getSelectedIndex();
                if (actuatorNumber >= 0){
                    System.out.println("The selected actuator is: :" + actuatorList.get(actuatorNumber).getName());
                    //TODO: get list of actions of the actuator

                    operatorSelection.removeAllItems();
                    operatorSelection.setModel(new DefaultComboBoxModel<>(Operator.values()));
                    Operator selectedType = (Operator)operatorSelection.getSelectedItem();

                    //System.out.println(operatorSelection.getSelectedItem());
                    sensorSelection.removeAllItems();
                    for (AbstractSensor sensor : sensorList) {
                        sensorSelection.addItem(sensor.getName());
                    }

                    actActionSelection.removeAllItems();

                    loadActuatorsMethods(actuatorList.get(actuatorNumber));

                    refreshActionList();
                    refreshActuatorState();
                }
            }
        });
        actionsList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int actionNumber = actionsList.getSelectedIndex();
                if (actionNumber >= 0){
                    Action action = actionList.get(actionNumber);

                    operatorSelection.removeAllItems();
                    operatorSelection.setModel(new DefaultComboBoxModel<>(Operator.values()));
                    Operator selectedType = (Operator)operatorSelection.getSelectedItem();

                    sensorSelection.removeAllItems();
                    for (AbstractSensor sensor : sensorList) {
                        sensorSelection.addItem(sensor.getName());
                    }

                }
            }
        });

        newActionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //System.out.println("newActionButton");
                // TODO: This logic is repeated... refactor?
                int actuatorNumber = actuatorsList.getSelectedIndex();
                if (actuatorNumber >= 0) {
                    controlValueInput.setText("0");
                    actionName.setText("New action");

                    int sensorId = sensorSelection.getSelectedIndex();
                    if(sensorId>=0){
                        AbstractSensor sensor=sensorList.get(sensorId);
                        //Command Pattern (Verificar porque é só 1 tipo)
                        //Default value
                        Condition condition01 = new Condition(sensor, Double.valueOf(controlValueInput.getText()) ,
                                Operator.LOWER);
                        List<Condition> conditions = new ArrayList<>();
                        conditions.add(condition01);

                        //Command Pattern
                        List<AbstractActuator> actuatorsForAction=new ArrayList<>();
                        actuatorsForAction.add(actuatorList.get(actuatorNumber));

                        ActuatorAction actuatorAction=new ActuatorAction("", "");

                        Action action = new Action(actionName.getText(), actuatorsForAction, conditions, actuatorAction);
                        action.execute(sensorList.get(sensorId));
                        actionList.add(action);
                        //updateActuatorListFromActionsFile();
                    }
                    refreshActionList();
                }

            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO: This logic is repeated... refactor?
                int actionNumber = actionsList.getSelectedIndex();
                if (actionNumber >= 0) {
                    /*ActionT action = actionList.get(actionNumber);
                    actionList.remove(action);*/
                    Action action = actionList.get(actionNumber);
                    actionList.remove(action);
                    action = null;

                    //TEmos de tirar do hub
                    refreshActionList();
                    clearActionDetails();
                    save();
                }
            }
        });
        sensorsList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                // TODO: This logic is repeated... refactor?
                int sensorNumber = sensorsList.getSelectedIndex();
                if (sensorNumber >= 0){
                    AbstractSensor sensor = sensorList.get(sensorNumber);
                    sensorReading.setText(String.valueOf(sensor.getReading() + " " + sensor.getMeasuringUnit()));
                }
            }
        });

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                //System.out.println("saveButton");
                int actionNumber = actionsList.getSelectedIndex();
                if (actionNumber >= 0) {

                    Action action=actionList.get(actionNumber);
                    action.setName(actionName.getText());

                    int sensorId = sensorSelection.getSelectedIndex();
                    System.out.println(sensorId + "\n " + sensorList.get(sensorId).getName());
                    if(sensorId>=0) {
                        AbstractSensor sensor = sensorList.get(sensorId);
                        Condition condition01 = new Condition(sensor,
                                Double.valueOf(controlValueInput.getText()),
                                (Operator) operatorSelection.getSelectedItem());

                        AbstractActuator actuator = action.getActuators().get(0);
                        String method = (String) actActionSelection.getSelectedItem();
                        //System.out.println("set"+method);
                        //System.out.println(acActionOption.getText());

                        action.getConditions().set(0, condition01);
                        action.getActuators().set(0, action.getActuators().get(0));
                        action.getActuatorAction().setName(method);
                        action.getActuatorAction().setValue(acActionOption.getText());

                        //TESTAR ISTO
                        actuatorList.set(actuatorList.indexOf(actuator), action.getActuators().get(0));

                        refreshActionList();
                        sensorReadingsHub.addAction(action);
                        action.execute(sensorList.get(action.getConditions().get(0).getSensor().getId()));
                        updateActuatorListFromActionsFile();

                    /*Iterator it=actionList.iterator();
                    while (it.hasNext()){
                        System.out.println(it.next());
                    }*/
                    }
                    save();
                }
            }
        });
    }

    public List<String> loadActuatorsMethods(AbstractActuator actuator){
        List<String> methods=new ArrayList<>();

        for (var method : actuator.getClass().getMethods()) {
            if (method.getName().contains("set") && !method.getName().contains("setName")){
                String variableName=method.getName().replaceAll("set","");
                actActionSelection.addItem(variableName);
                //System.out.println(variableName);
                methods.add(variableName);
            }
        }
        return methods;
    }


    public void clearActionDetails(){
        actionName.setText("");
        sensorSelection.removeAllItems();
        operatorSelection.removeAllItems();
        controlValueInput.setText("0");
    }

    public void refreshActuatorsList(){
        actuatorListModel.removeAllElements();
        for (AbstractActuator actuator : actuatorList) {
            actuatorListModel.addElement(actuator.getName());
        }
    }

    public void refreshSensorsList(){
        sensorListModel.removeAllElements();
        for (AbstractSensor sensor : sensorList) {
            sensorListModel.addElement(sensor.getName());
        }
    }

    public void refreshActionList(){
        //System.out.println("refreshActionList ");
        actionListModel.removeAllElements();
        for (Action action : actionList) {
            //System.out.println("Adding action to list: " + action.getName());
            actionListModel.addElement(action.getName());
        }
    }

    //public void addActuatorToList(AbstractActuator actuator) throws IOException
    public void addActuatorToList(List<AbstractActuator> actuatorList) throws IOException {
        synchronized (actuatorList){
            this.actuatorList = actuatorList;
        }
        refreshActuatorsList();
    }


    public void addSensorToList(List<AbstractSensor> sensor){
        // TODO: Probably not needed (should be part of another component)
        //sensorList.add(sensor);
        synchronized (sensorList){
            this.sensorList = sensor;
        }
        refreshSensorsList();
    }



    public Integer getNumberOfActuators(){
        return actuatorList.size();
    }

    public void refreshActuatorState(){
        int actuatorNumber = actuatorsList.getSelectedIndex();

        if (actuatorNumber >= 0) {
            System.out.println(getActuatorFromList(actuatorList.get(actuatorNumber)));
            AbstractActuator actuator = actuatorList.get(actuatorNumber);
            actuatorState.setText(actuator.getState());
        }
    }

    public void save() {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream("actions.txt");
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            /*System.out.println("Save");
            Iterator it=actionList.iterator();
            while(it.hasNext()){
                System.out.println(it.next());
            }
*/
            oos.writeObject(actionList);

            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void load() {
        FileInputStream fis = null;
        try {
            fis = new FileInputStream("actions.txt");
            ObjectInputStream ois = new ObjectInputStream(fis);
            List<Action> actions = (List<Action>) ois.readObject();

            actionList.addAll(actions);

           /* Iterator it=actions.iterator();
            while(it.hasNext()){
                System.out.println(it.next());
            }*/
            ois.close();
            //updateActuatorListFromActionsFile();
        } catch (IOException e) {
           e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public void updateActuatorListFromActionsFile(){
        for (Action action:actionList
        ) {
            System.out.println("update: "+action);
            //actuatorList.stream().anyMatch(act->act.equals(actuator))
            for(int i=0; i<actuatorList.size(); i++){
                if(actuatorList.get(i).equals(action.getActuators().get(0))){
                    actuatorList.set(i, action.getActuators().get(0));
                }
            }
        }
    }

    public AbstractSensor getSensorFromList(AbstractSensor abstractSensor){
        for (AbstractSensor sensor:sensorList
             ) {
            if(sensor.equals(abstractSensor))
                return sensor;
        }
        return null;
    }

    public AbstractActuator getActuatorFromList(AbstractActuator abstractActuator){
        for (AbstractActuator actuator:actuatorList
        ) {
            if(actuator.equals(abstractActuator))
                return actuator;
        }
        return null;
    }

    public void checkActionsFromFile(){
       //
        for (Action action: actionList
        ) {
            //System.out.println("execute actions");
            //System.out.println(getSensorFromList(action.getConditions().get(0).getSensor()));
            action.execute(getSensorFromList(action.getConditions().get(0).getSensor()));
        }
        updateActuatorListFromActionsFile();
        refreshActuatorState();

        sensorReadingsHub.addActions(actionList);
    }

    public static void main(String[] args) throws IOException {
        MainScreenTest mainscreen = new MainScreenTest();
        mainscreen.setVisible(true);

        DiscoveryModule discoveryModule=new DiscoveryModule(actuatorList, sensorList, mainscreen.sensorReadingsHub);
        discoveryModule.loadFiles();

        synchronized (sensorList){
            mainscreen.addSensorToList(discoveryModule.getSensorList());
        }

        synchronized (actuatorList){
            //Add files actuators to mainscreen
            mainscreen.addActuatorToList(discoveryModule.getActuatorsList());
        }

        mainscreen.checkActionsFromFile();

        ExecutorService service = Executors.newFixedThreadPool(4);
        service.submit(new Runnable() {
            public void run() {
                try {
                    discoveryModule.processEvents();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });


        // Temporario
        Timer t = new Timer();
        t.schedule(new TimerTask() {
            @Override
            public void run() {
                mainscreen.refreshActuatorsList();
                mainscreen.refreshSensorsList();
            }
        }, 0, 5000);
                   /*System.out.println("******* Actuators List Main *******");
            Iterator it =actuatorList.iterator();
            while (it.hasNext()) {
                System.out.println(it.next());
            }*/

    }

}

