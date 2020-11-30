import Models.AbstractActuator;
import Models.AbstractSensor;
import infrastructure.ActionT;
import infrastructure.Condition;
import infrastructure.Operator;
import infrastructure.Action;
import infrastructure.SensorReading;

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
                    AbstractActuator actuator = actuatorList.get(actuatorNumber);
                    System.out.println("The selected actuator is: :" + actuator.getName());
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

                    loadActuatorsMethods(actuator);

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

                    //Falta carregar actions para um ficheiro para guardar ao desligar
                    Action action = actionList.get(actionNumber);

                    //Falta carregar conditions para um ficheiro para guardar ao desligar


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

                    AbstractActuator actuator = actuatorList.get(actuatorNumber);

                    int sensorId = sensorSelection.getSelectedIndex();
                    /*System.out.println("sensorID: " + sensorId);
                    System.out.println("controlValueInput: "+controlValueInput.getText());
                    System.out.println("sensorReading: "+sensorList.get(sensorId).getReading());*/

                    //Command Pattern (Verificar porque é só 1 tipo)
                    //Default value
                    Condition condition01 = new Condition(sensorId, Double.valueOf(controlValueInput.getText()) ,
                            Operator.LOWER);
                    List<Condition> conditions = new ArrayList<>();
                    conditions.add(condition01);

                    SensorReading sensorReadingClass =
                            new SensorReading(sensorId, sensorList.get(sensorId).getReading());

                    //System.out.println(sensorReadingClass.toString());
                    //System.out.println(condition01.toString());

                    //Command Pattern
                    List<AbstractActuator> actuatorsForAction=new ArrayList<>();
                    actuatorsForAction.add(actuator);
                    Action action = new Action(actionName.getText(), actuatorsForAction, conditions);

                    action.execute(sensorReadingClass);
                    System.out.println(action.toString());
                    actionList.add(action);

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
                    refreshActionList();
                    clearActionDetails();
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
                    int sensorId = sensorSelection.getSelectedIndex();

                    SensorReading sensorReadingClass = new SensorReading(sensorId, Double.valueOf((Double) sensor.getReading()));
                    sensorReading.setText(String.valueOf(sensorReadingClass.getValue() + " " + sensor.getMeasuringUnit()));
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

                    Condition condition01 = new Condition(sensorSelection.getSelectedIndex(),
                            Double.valueOf(controlValueInput.getText()),
                            (Operator) operatorSelection.getSelectedItem());

                    AbstractActuator actuator=action.getActuators().get(0);
                    String method= (String) actActionSelection.getSelectedItem();
                    try {
                        actuator.getClass().getMethod("set"+method).invoke(acActionOption.getText());
                    } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                    }

                    //Arrays.stream(Operator.values()).anyMatch((t) -> t.name().equals(controlValueInput.getText()));
                    action.getConditions().set(0,condition01 );
                    action.getActuators().set(0, actuator);

                    refreshActionList();

                    /*Iterator it=actionList.iterator();
                    while (it.hasNext()){
                        System.out.println(it.next());
                    }*/

                    try {
                        save();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
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
            //System.out.println(actuator.toString());
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
            System.out.println("Adding action to list: " + action.getName());
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

    //public void addSensorToList(AbstractSensor sensor){
    public void addSensorToList(List<AbstractSensor> sensor){
        // TODO: Probably not needed (should be part of another component)
        //sensorList.add(sensor);
        synchronized (sensorList){
            this.sensorList = sensor;
        }
        refreshSensorsList();
    }


    public static void main(String[] args) throws IOException {
        MainScreenTest mainscreen = new MainScreenTest();
        mainscreen.setVisible(true);

        DiscoveryModule discoveryModule=new DiscoveryModule(actuatorList, sensorList);
        discoveryModule.loadFiles();
        synchronized (actuatorList){
            //System.out.println(actuatorList.size());
            //Add files actuators to mainscreen
            mainscreen.addActuatorToList(discoveryModule.getActuatorsList());
        }
        synchronized (sensorList){
            //System.out.println(sensorList.size());
            //Add files actuators to mainscreen
            mainscreen.addSensorToList(discoveryModule.getSensorList());
        }

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


    public Integer getNumberOfActuators(){
        return actuatorList.size();
    }

    public void refreshActuatorState(){
        int actuatorNumber = actuatorsList.getSelectedIndex();
        if (actuatorNumber >= 0) {
            AbstractActuator actuator = actuatorList.get(actuatorNumber);
            actuatorState.setText(actuator.getState());
        }
    }

    public void save() throws IOException {
        FileOutputStream fos = new FileOutputStream("actions.txt");
        ObjectOutputStream oos = new ObjectOutputStream(fos);

        System.out.println("Save");
        Iterator it=actionList.iterator();
        while(it.hasNext()){
            System.out.println(it.next());
        }

        oos.writeObject(actionList);

        oos.close();
    }

    public void load() {

        FileInputStream fis = null;
        try {
            fis = new FileInputStream("actions.txt");
            ObjectInputStream ois = new ObjectInputStream(fis);
            List<Action> actions = (List<Action>) ois.readObject();

            actionList.addAll(actions);

            Iterator it=actions.iterator();
            while(it.hasNext()){
                System.out.println(it.next());
            }
            System.out.println(actionList.get(0).getActuators().get(0).getName());

            ois.close();
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        } catch (ClassNotFoundException e) {
        }

    }

}

