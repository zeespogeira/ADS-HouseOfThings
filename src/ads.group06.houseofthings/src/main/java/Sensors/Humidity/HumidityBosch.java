package Sensors.Humidity;

public class HumidityBosch extends Humidity {
    String humidity;


    public HumidityBosch(String humidity) {
        this.humidity = humidity;
    }


    public HumidityBosch() {
    }

    @Override
    public void sense(boolean flag) {

    }

}
