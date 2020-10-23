package Actuators.Thermometer;


import Actuators.ActuatorsClass;
import Models.AbstractActuator;

public abstract class Thermometer extends AbstractActuator {
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
}
