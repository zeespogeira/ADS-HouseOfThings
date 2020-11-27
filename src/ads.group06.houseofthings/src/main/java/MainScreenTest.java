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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
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

        sensorReading.setEditable(false);


        actuatorsList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int actuatorNumber = actuatorsList.getSelectedIndex();
                if (actuatorNumber >= 0){
                    AbstractActuator actuator = actuatorList.get(actuatorNumber);
                    System.out.println("The selected actuator is: :" + actuator.getName());
                    //TODO: get list of actions of the actuator
                    //To Test
                    /*int sensorId = 1;
                    System.out.println(sensorId);
                    Condition condition01 = new Condition(sensorId, 123, Operator.EQUAL);
                    Condition condition02 = new Condition(sensorId, 124, Operator.HIGHER);
                    Condition condition03 = new Condition(sensorId, 125, Operator.LOWER);
                    List<Condition> conditions = new ArrayList<Condition>();
                    conditions.add(condition01);
                    conditions.add(condition02);
                    conditions.add(condition03);


                    SensorReading sensorReading = new SensorReading(sensorId, 100);
                    Action action = new Action(actuatorList, conditions);
                    action.execute(sensorReading);*/

                    // Populating actions (for testing)
                    /*ActionT action1 = new ActionT(actuator.getName() + " is cold", Operator.LOWER, 10, actuator);
                    ActionT action2 = new ActionT(actuator.getName() + " is hot", Operator.HIGHER, 25, actuator);

                    actionList.clear();
                    actionList.add(action1);
                    actionList.add(action2);*/
                    // End data for testing

                    refreshActionList();
                }
            }
        });
        actionsList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int actionNumber = actionsList.getSelectedIndex();
                if (actionNumber >= 0){
                    Action action = actionList.get(actionNumber);

                    /*actionName.setText(action.getName());
                    controlValueInput.setText(String.valueOf(action.getControlValue()));
                    action.setOperator((Operator) operatorSelection.getSelectedItem());
                    */

                    operatorSelection.removeAllItems();
                    operatorSelection.setModel(new DefaultComboBoxModel<>(Operator.values()));
                    Operator selectedType = (Operator)operatorSelection.getSelectedItem();

                    sensorSelection.removeAllItems();
                    for (AbstractSensor sensor : sensorList) {
                        sensorSelection.addItem(sensor.getName());
                    }

                    /*int sensorId = action.getID();
                    System.out.println(sensorId);
                    sensorSelection.setSelectedItem(sensorId);*/
                }
            }
        });
        newActionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                // TODO: This logic is repeated... refactor?
                int actuatorNumber = actuatorsList.getSelectedIndex();
                //System.out.println(actuatorNumber);
                if (actuatorNumber >= 0) {
                    AbstractActuator actuator = actuatorList.get(actuatorNumber);

                    //To Test
                    int sensorId = sensorSelection.getSelectedIndex();
                    System.out.println(sensorId);
                    //sensorSelection.setSelectedItem(sensorId);
                    Condition condition01 = new Condition(sensorId, Integer.valueOf(controlValueInput.getText()), Operator.valueOf((String) operatorSelection.getSelectedItem()));

                    List<Condition> conditions = new ArrayList<Condition>();
                    conditions.add(condition01);


                    SensorReading sensorReading = new SensorReading(sensorId, 100);
                    Action action = new Action(actuatorList, conditions);
                    action.execute(sensorReading);
/*
                    ActionT new_action = new ActionT(actuator.getName() + " new action", Operator.HIGHER, 25, actuator);
                    */actionList.add(action);
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
                    // TODO: delete action
                    //action = null;
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
                    System.out.println(sensorId);

                    sensor.sense();
                    SensorReading sensorReadingClass = new SensorReading(sensorId, (Double) sensor.getReading());
                    sensorReading.setText(String.valueOf(sensorReadingClass.getValue()));
                }
            }
        });

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                int actionNumber = actionsList.getSelectedIndex();
                if (actionNumber >= 0) {
                   /* ActionT action = actionList.get(actionNumber);
                    action.setName(actionName.getText());
                    action.setControlValue(Integer.valueOf(controlValueInput.getText()));
                    action.setOperator((Operator) operatorSelection.getSelectedItem());*/
                    refreshActionList();
                }
            }
        });
    }

    public void clearActionDetails(){
        actionName.setText("");
        sensorSelection.removeAllItems();
        operatorSelection.removeAllItems();
        controlValueInput.setText("");
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
        actionListModel.removeAllElements();
       /* for (Action action : actionList) {
            System.out.println("Adding action to list: " + action.getName());
            actionListModel.addElement(action.getName());
        }*/
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

}

