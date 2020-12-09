package Actuators.Lamp;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicInteger;

public class LampPhilips extends Lamp implements Serializable {
    public LampPhilips() {
    }


    @Override
    public String toString() {
        return "LampPhilips{" +super.toString() +"}";
    }
}
