package Sensors.Humidity;


import Models.AbstractSensor;

public class HumidityBosch extends Humidity {

    /*public HumidityBosch(String humidity) {
        super(humidity);
    }*/

    public HumidityBosch() {
    }



    @Override
    public String toString() {
        return super.toString() +
                ", humidity='" + humidity;
    }
}
