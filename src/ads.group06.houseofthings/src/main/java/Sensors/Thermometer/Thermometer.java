package Sensors.Thermometer;


import Models.AbstractActuator;
import Models.AbstractSensor;

public abstract class Thermometer extends AbstractSensor {
    public Integer temperature;

    public Thermometer(Integer temperature) {
        this.temperature = temperature;
    }

    public Thermometer() {
    }

    public Integer getTemperature() {
        return temperature;
    }

    public void setTemperature(Integer temperature) {
        this.temperature = temperature;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
