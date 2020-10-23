package Lamp;

public class LampPhilips extends Lamp {
    Integer ilumination;

    public LampPhilips(int id, Integer ilumination) {
        super(id);
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
}
