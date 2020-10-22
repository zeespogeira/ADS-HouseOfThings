package Lamp;

public class LampPhilips extends Lamp {
    Integer ilumination;

    public LampPhilips(Integer ilumination) {
        this.ilumination = ilumination;
    }

    @Override
    public Integer getIlumination() {
         this.turnOff();
        return ilumination;
    }

    @Override
    public void act() {

    }

    @Override
    public void act(boolean test) {

    }
}
