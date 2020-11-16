import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class MainScreen extends JFrame {
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
    private ArrayList<Actuator> actuatorList;
    private ArrayList<Sensor> sensorList;
    private ArrayList<Action> actionList;
    private DefaultListModel actuatorListModel;
    private DefaultListModel actionListModel;
    private DefaultListModel sensorListModel;


    MainScreen() {
        super("ADS House Controller");
        this.setContentPane(this.panelMain);
        //this.setPreferredSize(new Dimension(500,1000));
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        actuatorList = new ArrayList<>();
        actuatorListModel = new DefaultListModel();
        actuatorsList.setModel(actuatorListModel);

        sensorList = new ArrayList<>();
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
                    Actuator actuator = actuatorList.get(actuatorNumber);
                    System.out.println("The selected actuator is: :" + actuator.getName());
                    //TODO: get list of actions of the actuator

                    // Populating actions (for testing)
                    Action action1 = new Action(actuator.getName() + " is cold", "Lower than", 10, actuator);
                    Action action2 = new Action(actuator.getName() + " is hot", "Larger than", 25, actuator);
                    actionList.clear();
                    actionList.add(action1);
                    actionList.add(action2);
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
                    actionName.setText(action.getName());
                    controlValueInput.setText(String.valueOf(action.getControlValue()));
                    operatorSelection.removeAllItems();
                    // TODO: These options should probably come from one component...
                    operatorSelection.addItem("Larger than");
                    operatorSelection.addItem("Smaller than");
                    operatorSelection.addItem("Equal to");
                    // End
                    sensorSelection.removeAllItems();
                    for (Sensor sensor : sensorList) {
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
                if (actuatorNumber >= 0) {
                    Actuator actuator = actuatorList.get(actuatorNumber);
                    Action new_action = new Action(actuator.getName() + " new action", "Larger than", 25, actuator);
                    actionList.add(new_action);
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
                    Sensor sensor = sensorList.get(sensorNumber);
                    sensorReading.setText(String.valueOf(sensor.getReading()));
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
        for (Actuator actuator : actuatorList) {
            actuatorListModel.addElement(actuator.getName());

        }
    }

    public void refreshSensorsList(){
        sensorListModel.removeAllElements();
        for (Sensor sensor : sensorList) {
            sensorListModel.addElement(sensor.getName());
        }
    }

    public void refreshActionList(){
        actionListModel.removeAllElements();
        for (Action action : actionList) {
            System.out.println("Adding action to list: " + action.getName());
            actionListModel.addElement(action.getName());
        }
    }

    public void addActuatorToList(Actuator actuator){
        // TODO: Probably not needed (should be part of another component)
        actuatorList.add(actuator);
        refreshActuatorsList();
    }

    public void addSensorToList(Sensor sensor){
        // TODO: Probably not needed (should be part of another component)
        sensorList.add(sensor);
        refreshSensorsList();
    }


    public static void main(String[] args) {
        MainScreen mainscreen = new MainScreen();
        mainscreen.setVisible(true);

        // Populating actuators (for testing)
        Actuator bosch_actuator = new Actuator("Actuator Bosch");
        mainscreen.addActuatorToList(bosch_actuator);
        Actuator philips_actuator = new Actuator("Actuator Philips");
        mainscreen.addActuatorToList(philips_actuator);
        System.out.print("List of actuators: " + mainscreen.actuatorList.toString());

        // Populating sensors (for testing)
        Sensor bosch_sensor = new Sensor("Sensor Bosch");
        mainscreen.addSensorToList(bosch_sensor);
        Sensor philips_sensor = new Sensor("Sensor Philips");
        mainscreen.addSensorToList(philips_sensor);
        System.out.print("List of sensors: " + mainscreen.sensorList.toString());

    }

}

