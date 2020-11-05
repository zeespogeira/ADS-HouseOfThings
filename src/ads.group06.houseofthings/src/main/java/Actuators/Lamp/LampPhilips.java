package Actuators.Lamp;

import java.util.concurrent.atomic.AtomicInteger;

public class LampPhilips extends Lamp {

   // static final AtomicInteger idGen = new AtomicInteger(1);

    public LampPhilips() {
        //super(idGen.getAndIncrement());
    }

    public LampPhilips(boolean isOn) {
        super(isOn);
    }

    @Override
    public String toString() {
        return "LampPhilips{" + super.toString() +"}";
    }
}
