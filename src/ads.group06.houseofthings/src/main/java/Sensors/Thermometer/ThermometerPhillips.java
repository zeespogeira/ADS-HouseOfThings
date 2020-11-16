package Sensors.Thermometer;

public class ThermometerPhillips extends Thermometer {

    Integer realFeel;
    public ThermometerPhillips(String temperature) {
        super(temperature);
    }

    public ThermometerPhillips() {
    }

    @Override
    public void sense(boolean flag) {

    }

    @Override
    public String toString() {
        return "ThermometerBosch{" + super.toString() +
                ", realFeel=" + realFeel +
                '}';
    }

    public Integer getRealFeel() {
        return realFeel;
    }

    public void setRealFeel(Integer realFeel) {
        this.realFeel = realFeel;
    }
}
