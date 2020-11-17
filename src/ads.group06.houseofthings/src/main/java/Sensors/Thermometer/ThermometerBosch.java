package Sensors.Thermometer;

import java.util.Random;

public class ThermometerBosch extends Thermometer {

    public ThermometerBosch(String temperature) {
        super(temperature);
    }

    public ThermometerBosch() {
    }

    @Override
    public void sense(boolean flag) {
        if(temperature.isEmpty()){
            Random r = new Random();
            int low = -5;
            int high = 35;
            int result = r.nextInt(high-low) + low;
            setTemperature(String.valueOf(result));
        }

    }

    @Override
    public String toString() {
        return "ThermometerBosch{" + super.toString() +
                ", temperature=" + temperature +
                '}';
    }


}
