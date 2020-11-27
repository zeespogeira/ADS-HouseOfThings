package Sensors.Humidity;


import Models.AbstractSensor;

public class HumidityBosch extends Humidity {

    //String humidity;
    public HumidityBosch(String humidity) {
        super(humidity);
    }

    public HumidityBosch() {
    }

    @Override
    public AbstractSensor sense(boolean flag) {

        return this;
    }

    @Override
    public String toString() {
        return "HumidityBosch{" + super.toString() +
                ", humidity='" + humidity + '\'' +
                '}';
    }
}
