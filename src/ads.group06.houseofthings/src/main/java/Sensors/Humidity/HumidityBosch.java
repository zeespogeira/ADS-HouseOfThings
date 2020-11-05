package Sensors.Humidity;

public class HumidityBosch extends Humidity {

    String humidity;
    public HumidityBosch(String humidity) {
        //super(humidity);
        this.humidity=humidity;
    }

    public HumidityBosch() {
    }

    @Override
    public void sense(boolean flag) {

    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    @Override
    public String toString() {
        return "HumidityBosch{" + super.toString() +
                ", humidity='" + humidity + '\'' +
                '}';
    }
}
