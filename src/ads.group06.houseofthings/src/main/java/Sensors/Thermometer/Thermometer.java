package Sensors.Thermometer;


import Models.AbstractActuator;
import Models.AbstractSensor;

public abstract class Thermometer extends AbstractSensor {
    public String temperature;

    public Thermometer(String temperature) {
        this.temperature = temperature;
    }

    public Thermometer() {
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
