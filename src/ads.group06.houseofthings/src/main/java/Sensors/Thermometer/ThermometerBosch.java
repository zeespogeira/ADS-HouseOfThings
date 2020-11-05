package Sensors.Thermometer;

public class ThermometerBosch extends Thermometer {

    public ThermometerBosch(Integer temperature) {
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
