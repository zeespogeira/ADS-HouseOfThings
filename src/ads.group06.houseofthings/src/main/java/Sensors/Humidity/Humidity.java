package Sensors.Humidity;

import Models.AbstractSensor;

import java.util.concurrent.atomic.AtomicInteger;

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


    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }
}
