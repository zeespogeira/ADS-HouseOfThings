package Sensors.Humidity;

import Models.AbstractSensor;

public abstract class Humidity extends AbstractSensor {
    String humidity;

    public Humidity() {
    }

    public Humidity(String humidity) {
        this.humidity = humidity;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
