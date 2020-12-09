package infrastructure;

import Interface.IAction;
import Models.AbstractActuator;
import Models.AbstractSensor;

import java.util.*;
import java.util.stream.Collectors;

public class SensorReadingsHub {
    private List<IAction> actions;
    //private Queue<SensorReading> sensorReadings;
    private List<AbstractSensor> sensorReadings;

    public SensorReadingsHub(){
        this.actions = new ArrayList<>();
        sensorReadings = new ArrayList<>();//(100);
    }

    public void addAction(IAction action){
        this.actions.add(action);
        //System.out.println("Ultima Action");
        //System.out.println(actions.get(actions.size()-1));
    }

    public void removeAction(IAction action){
        this.actions.remove(action);
        //System.out.println("Ultima Action");
        //System.out.println(actions.get(actions.size()-1));
    }

    public void addActions(List<Action> actions){
        this.actions.addAll(actions);
        //System.out.println("Actions in Hub");
        //printActionsList();
    }

    public void addSensor(AbstractSensor sensor){
        sensorReadings.add(sensor);
        notifyActions(sensor);
    }

    private void notifyActions(AbstractSensor sensorReading) {

        //get actions associated with the sensor
        var sensorActions = actions.stream()
                .filter(a->a.hasConditionWithSensor(sensorReading.getId()))
                .collect(Collectors.toList());

        for (IAction action : sensorActions){
            //System.out.println("action: "+action.toString());
            action.execute(sensorReading);
        }

    }

    public void printActionsList(){
        Iterator it=actions.iterator();
        while (it.hasNext()){
            System.out.println(it.next());
        }
    }
}
