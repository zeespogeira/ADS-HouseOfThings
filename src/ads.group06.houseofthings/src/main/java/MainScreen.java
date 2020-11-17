import Models.AbstractActuator;
import Models.AbstractSensor;
import infrastructure.Condition;
import infrastructure.Operator;
import infrastructure.SensorReading;
import infrastructure.Action;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.*;
import java.util.Timer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MainScreen extends JFrame {
    private JPanel panelMain;
    private JList actuatorsList;
    private JList sensorsList;
    private JList actionsList;
    private JButton newActionButton;
    private JTextField actionName;
    private JButton saveButton;
    private JButton deleteButton;
    private JComboBox<Operator> operatorSelection;
    private JTextField controlValueInput;
    private JComboBox sensorSelection;
    private JTextField sensorReading;
    private static List<AbstractActuator> actuatorList= Collections.synchronizedList(new ArrayList<AbstractActuator>());
    private static List<AbstractSensor> sensorList= Collections.synchronizedList(new ArrayList<AbstractSensor>());
    private ArrayList<Action> actionList;
    private DefaultListModel actuatorListModel;
    private DefaultListModel actionListModel;
    private DefaultListModel sensorListModel;


    MainScreen() throws IOException {
        super("ADS House Controller");
        this.setContentPane(this.panelMain);
        //this.setPreferredSize(new Dimension(500,1000));
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();

        actuatorListModel = new DefaultListModel();
        actuatorsList.setModel(actuatorListModel);
        System.out.println(actuatorsList.getSelectedIndex());

        //sensorList = new ArrayList<>();
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
                    int sensorId = 1;
                    Condition condition01 = new Condition(sensorId, 123, Operator.EQUAL);
                    Condition condition02 = new Condition(sensorId, 124, Operator.HIGHER);
                    Condition condition03 = new Condition(sensorId, 125, Operator.LOWER);
                    List<Condition> conditions = new ArrayList<Condition>();
                    conditions.add(condition01);
                    conditions.add(condition02);
                    conditions.add(condition03);


                    SensorReading sensorReading = new SensorReading(sensorId, 100);
                    Action action = new Action(actuatorList, conditions);
                    action.execute(sensorReading);

                    // Populating actions (for testing)
                    /*Action action1 = new Action(actuator.getName() + " is cold", "Lower than", 10, actuator);
                    Action action2 = new Action(actuator.getName() + " is hot", "Larger than", 25, actuator);
                    */
                    actionList.clear();
                    actionList.add(action);
                    //actionList.add(action2);
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
//                    actionName.setText(action.getName());
//                    controlValueInput.setText(String.valueOf(action.getControlValue()));
                    operatorSelection.removeAllItems();

                    operatorSelection.setModel(new DefaultComboBoxModel<>(Operator.values()));
                    // TODO: These options should probably come from one component...
                    //operatorSelection.addItem(Operator.LOWER);
                    //operatorSelection.addItem("Smaller than");
                    //operatorSelection.addItem("Equal to");
                    // End
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

                // TODO: This logic is repeated... refactor?
                int actuatorNumber = actuatorsList.getSelectedIndex();
                //System.out.println(actuatorNumber);
                if (actuatorNumber >= 0) {
                    AbstractActuator actuator = actuatorList.get(actuatorNumber);
                    //Action new_action = new Action(actuator.getName() + " new action", "Larger than", 25, actuator);
                    //actionList.add(new_action);

                    //To Test
                    int sensorId = sensorList.get(0).getId();
                    Condition condition01 = new Condition(sensorId, 123, Operator.EQUAL);
                    Condition condition02 = new Condition(sensorId, 124, Operator.HIGHER);
                    Condition condition03 = new Condition(sensorId, 125, Operator.LOWER);
                    List<Condition> conditions = new ArrayList<Condition>();
                    conditions.add(condition01);
                    conditions.add(condition02);
                    conditions.add(condition03);


                    SensorReading sensorReading = new SensorReading(sensorId, 100);
                    Action action = new Action(actuatorList, conditions);
                    action.execute(sensorReading);

                    // Populating actions (for testing)
                    /*Action action1 = new Action(actuator.getName() + " is cold", "Lower than", 10, actuator);
                    Action action2 = new Action(actuator.getName() + " is hot", "Larger than", 25, actuator);
                    */
                    actionList.clear();
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
                    Action action = actionList.get(actionNumber);
                    actionList.remove(action);
                    // TODO: delete action
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
                    //sensorReading.setText(String.valueOf(sensor.getReading()));
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
        for (Action action : actionList) {
//            System.out.println("Adding action to list: " + action.getName());
//            actionListModel.addElement(action.getName());
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
        MainScreen mainscreen = new MainScreen();
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
