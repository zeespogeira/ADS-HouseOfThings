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
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
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
    private JComboBox actActionSelection;
    private JPanel actuatorsOptions;
    private JTextField acActionOption;
    private JTextField actuatorState;
    private static List<AbstractActuator> actuatorList = Collections.synchronizedList(new ArrayList<AbstractActuator>());
    private static List<AbstractSensor> sensorList = Collections.synchronizedList(new ArrayList<AbstractSensor>());
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

        //actionList = new ArrayList<>();
        actionListModel = new DefaultListModel();
        actionsList.setModel(actionListModel);

        sensorReading.setEditable(false);

        actuatorState.setEditable(false);

        actuatorsList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int actuatorNumber = actuatorsList.getSelectedIndex();
                if (actuatorNumber >= 0) {
                    AbstractActuator actuator = actuatorList.get(actuatorNumber);
                    System.out.println("The selected actuator is: :" + actuator.getName());
                    //TODO: get list of actions of the actuator

                    operatorSelection.removeAllItems();
                    operatorSelection.setModel(new DefaultComboBoxModel<>(Operator.values()));
                    Operator selectedType = (Operator) operatorSelection.getSelectedItem();

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
                if (actionNumber >= 0) {

                    //Falta carregar actions para um ficheiro para guardar ao desligar
                    Action action = actionList.get(actionNumber);

                    //Falta carregar conditions para um ficheiro para guardar ao desligar

                    /*actionName.setText(action.getName());
                    controlValueInput.setText(String.valueOf(action.getControlValue()));
                    action.setOperator((Operator) operatorSelection.getSelectedItem());
                    */

                    operatorSelection.removeAllItems();
                    operatorSelection.setModel(new DefaultComboBoxModel<>(Operator.values()));
                    Operator selectedType = (Operator) operatorSelection.getSelectedItem();

                    sensorSelection.removeAllItems();
                    for (AbstractSensor sensor : sensorList) {
                        sensorSelection.addItem(sensor.getName());
                    }

                    //loadActuatorsMethods(actuator);
                    /*int sensorId = action.getID();
                    System.out.println(sensorId);
                    sensorSelection.setSelectedItem(sensorId);*/
                }
            }
        });
        //Feitas alteracoes
        newActionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                // TODO: This logic is repeated... refactor?
                int actuatorNumber = actuatorsList.getSelectedIndex();
                //System.out.println(actuatorNumber);
                if (actuatorNumber >= 0) {
                   /* AbstractActuator actuator = actuatorList.get(actuatorNumber);

                    //To Test
                    int sensorId = sensorSelection.getSelectedIndex();
                    //System.out.println(sensorId);
                    //sensorSelection.setSelectedItem(sensorId);

                    //Command Pattern (Verificar porque é só 1 tipo)
                    Condition condition01 = new Condition(sensorId, Integer.valueOf(controlValueInput.getText()), Operator.valueOf((String) operatorSelection.getSelectedItem()));
                    List<Condition> conditions = new ArrayList<Condition>();
                    conditions.add(condition01);


                    SensorReading sensorReadingClass =
                            new SensorReading(sensorId, sensorList.get(sensorId).getReading());

                   // loadActuatorsMethods(actuator);

                    //Command Pattern
                    List<AbstractActuator> actuatorsForAction=new ArrayList<>();
                    actuatorsForAction.add(actuator);
                    Action action = new Action(actuatorsForAction, conditions);
                    action.execute(sensorReadingClass);

                    actionList.add(action);*/
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
                if (sensorNumber >= 0) {
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
                int actionNumber = actionsList.getSelectedIndex();
                int actuatorNumber = actuatorsList.getSelectedIndex();
                if (actionNumber >= 0) {
                   /* ActionT action = actionList.get(actionNumber);
                    action.setName(actionName.getText());
                    action.setControlValue(Integer.valueOf(controlValueInput.getText()));
                    action.setOperator((Operator) operatorSelection.getSelectedItem());*/
                    AbstractActuator actuator = actuatorList.get(actuatorNumber);

                    //To Test
                    int sensorId = sensorSelection.getSelectedIndex();
                    //System.out.println(sensorId);
                    //sensorSelection.setSelectedItem(sensorId);

                    //Command Pattern (Verificar porque é só 1 tipo)
                    Condition condition01 = new Condition(sensorId, Integer.valueOf(controlValueInput.getText()), Operator.valueOf((String) operatorSelection.getSelectedItem()));
                    List<Condition> conditions = new ArrayList<>();
                    conditions.add(condition01);

                    SensorReading sensorReadingClass =
                            new SensorReading(sensorId, sensorList.get(sensorId).getReading());

                    //Command Pattern
                    List<AbstractActuator> actuatorsForAction = new ArrayList<>();
                    actuatorsForAction.add(actuator);
                    Action action = new Action(actuatorsForAction, conditions);
                    action.execute(sensorReadingClass);

                    actionList.add(action);
                    refreshActionList();
                }
            }
        });
    }

    public List<String> loadActuatorsMethods(AbstractActuator actuator) {
        List<String> methods = new ArrayList<>();

        for (var method : actuator.getClass().getMethods()) {
            if (method.getName().contains("set") && !method.getName().contains("setName")) {
                String variableName = method.getName().replaceAll("set", "");
                actActionSelection.addItem(variableName);
                System.out.println(variableName);
                methods.add(variableName);
            }
        }
        return methods;
    }

    public void refreshActuatorState(){
        int actuatorNumber = actuatorsList.getSelectedIndex();
        if (actuatorNumber >= 0) {
            AbstractActuator actuator = actuatorList.get(actuatorNumber);
            actuatorState.setText(actuator.getState());
        }
    }


    public void clearActionDetails() {
        actionName.setText("");
        sensorSelection.removeAllItems();
        operatorSelection.removeAllItems();
        controlValueInput.setText("");
    }

    public void refreshActuatorsList() {
        actuatorListModel.removeAllElements();
        for (AbstractActuator actuator : actuatorList) {
            //System.out.println(actuator.toString());
            actuatorListModel.addElement(actuator.getName());
        }
    }

    public void refreshSensorsList() {
        sensorListModel.removeAllElements();
        for (AbstractSensor sensor : sensorList) {
            sensorListModel.addElement(sensor.getName());
        }
    }

    public void refreshActionList() {
        actionListModel.removeAllElements();
       /* for (Action action : actionList) {
            System.out.println("Adding action to list: " + action.getName());
            actionListModel.addElement(action.getName());
        }*/
    }

    //public void addActuatorToList(AbstractActuator actuator) throws IOException
    public void addActuatorToList(List<AbstractActuator> actuatorList) throws IOException {
        synchronized (actuatorList) {
            this.actuatorList = actuatorList;
        }

        refreshActuatorsList();
    }

    //public void addSensorToList(AbstractSensor sensor){
    public void addSensorToList(List<AbstractSensor> sensor) {
        // TODO: Probably not needed (should be part of another component)
        //sensorList.add(sensor);
        synchronized (sensorList) {
            this.sensorList = sensor;
        }
        refreshSensorsList();
    }


    public static void main(String[] args) throws IOException {
        MainScreenTest mainscreen = new MainScreenTest();
        mainscreen.setVisible(true);

        DiscoveryModule discoveryModule = new DiscoveryModule(actuatorList, sensorList);
        discoveryModule.loadFiles();
        synchronized (actuatorList) {
            //System.out.println(actuatorList.size());
            //Add files actuators to mainscreen
            mainscreen.addActuatorToList(discoveryModule.getActuatorsList());
        }
        synchronized (sensorList) {
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

    public Integer getNumberOfActuators() {
        return actuatorList.size();
    }

}

