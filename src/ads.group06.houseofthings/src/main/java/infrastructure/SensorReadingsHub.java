package infrastructure;

import Interface.IAction;
import Models.AbstractSensor;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.stream.Collectors;

public class SensorReadingsHub {
    private List<IAction> actions;
    //private Queue<SensorReading> sensorReadings;
    private List<AbstractSensor> sensorReadings;

    //thread safe
    //instanciar 1 vez e depois passar como argumento
   /* public SensorReadingsHub(List<IAction> actions){
        this.actions = actions;
        sensorReadings = new ArrayList<>();//(100);
    }*/

    public SensorReadingsHub(){
        this.actions = new ArrayList<>();
        sensorReadings = new ArrayList<>();//(100);
    }

    public void addAction(IAction action){
        this.actions.add(action);
    }

    public void addActions(List<Action> actions){
        this.actions.addAll(actions);
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
            action.execute(sensorReading);
        }
    }
}
