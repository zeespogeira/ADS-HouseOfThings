package Actuators.Lamp;

import java.util.concurrent.atomic.AtomicInteger;

public class LampPhilips extends Lamp {

    public LampPhilips() {
    }

    /*public LampPhilips(boolean isOn) {
        super( isOn);
    }*/

    @Override
    public String toString() {
        return "LampPhilips{" +super.toString() +"}";
    }
}
