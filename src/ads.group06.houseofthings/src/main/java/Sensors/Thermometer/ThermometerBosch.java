package Sensors.Thermometer;

public class ThermometerBosch extends Thermometer {

    public ThermometerBosch(String temperature) {
        super(temperature);
    }

    public ThermometerBosch() {
    }

    @Override
    public void sense(boolean flag) {

    }

    @Override
    public String toString() {
        return "ThermometerBosch{" + super.toString() +
                ", temperature=" + temperature +
                '}';
    }


}
