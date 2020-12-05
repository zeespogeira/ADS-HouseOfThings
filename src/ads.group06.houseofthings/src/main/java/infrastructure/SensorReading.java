package infrastructure;

import Models.AbstractSensor;

public class SensorReading {
    private AbstractSensor sensor;
    private double value;

    public SensorReading(AbstractSensor sensor, double value){
        this.sensor = sensor;
        this.value = value;
    }

    public AbstractSensor getSensorId(){
        return this.sensor;
    }

    public double getValue(){
        return this.value;
    }

    @Override
    public String toString() {
        return "SensorReading{" +
                "sensor=" + sensor +
                ", value=" + value +
                '}';
    }
}
