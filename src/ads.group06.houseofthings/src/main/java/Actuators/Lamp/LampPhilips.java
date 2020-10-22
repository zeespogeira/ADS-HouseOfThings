package Actuators.Lamp;

import Actuators.Lamp.Lamp;

public class LampPhilips extends Lamp {

    public LampPhilips(boolean isOn) {
        this.setOn(isOn);
    }

    public LampPhilips() { }



    @Override
    public void act() {

    }

    @Override
    public String toString() {
        return "LampPhilips{" + super.toString() +"}";
    }
}
