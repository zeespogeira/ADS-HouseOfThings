package Actuators.Lamp;

public class LampPhilips extends Lamp {

    public LampPhilips() {
    }

    public LampPhilips(boolean isOn) {
        super(isOn);
    }

    @Override
    public String toString() {
        return "LampPhilips{" + super.toString() +"}";
    }
}
