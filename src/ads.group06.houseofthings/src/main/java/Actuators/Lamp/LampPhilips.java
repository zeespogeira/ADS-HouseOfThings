package Actuators.Lamp;

import java.util.concurrent.atomic.AtomicInteger;

public class LampPhilips extends Lamp {

   // static final AtomicInteger idGen = new AtomicInteger(1);

    public LampPhilips() {
    }

    public LampPhilips(boolean isOn) {
        super(/*idGen.getAndIncrement(),*/ isOn);
    }

    @Override
    public String toString() {
        return "LampPhilips{" +super.toString() +"}";
    }
}
