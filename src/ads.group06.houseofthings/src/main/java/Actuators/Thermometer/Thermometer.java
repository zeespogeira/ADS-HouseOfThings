package Actuators.Thermometer;


import Actuators.ActuatorsClass;
import Interface.Actuators;

public abstract class Thermometer extends ActuatorsClass {
    public Integer temperature;

    public Integer getTemperature() {
        return temperature;
    }

    public void setTemperature(Integer temperature) {
        this.temperature = temperature;
    }
}
