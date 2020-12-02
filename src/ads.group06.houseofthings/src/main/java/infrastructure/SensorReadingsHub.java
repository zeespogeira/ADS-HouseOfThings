package infrastructure;

import Interface.IAction;

import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.stream.Collectors;

public class SensorReadingsHub {
    private List<IAction> actions;
    private Queue<SensorReading> sensorReadings;

    //instanciar 1 vez e depois passar como argumento
    private SensorReadingsHub(List<IAction> actions){
        this.actions = actions;
        sensorReadings = new PriorityQueue(100);
    }


    public void addAction(IAction action){
        this.actions.add(action);
    }

    public void addSensorReading(SensorReading sensorReading){
        sensorReadings.add(sensorReading);

        notifyActions(sensorReading);
    }

    private void notifyActions(SensorReading sensorReading) {
        //get actions associated with the sensor
        var sensorActions = actions.stream()
                .filter(a->a.hasConditionWithSensor(sensorReading.getSensorId()))
                .collect(Collectors.toList());

        for (IAction action : sensorActions){
            action.execute(sensorReading);
        }
    }
}
