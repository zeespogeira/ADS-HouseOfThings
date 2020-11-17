package Sensors.Thermometer;

import java.util.Random;

public class ThermometerPhillips extends Thermometer {

    Integer realFeel;
    public ThermometerPhillips(String temperature) {
        super(temperature);
    }

    public ThermometerPhillips() {
    }

    @Override
    public void sense(boolean flag) {
        Random r = new Random();
        int low = -5;
        int high = 35;
        int result = r.nextInt(high-low) + low;
        setRealFeel(result);
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
