package Lamp;

public class LampBosch extends Lamp {
    Integer ilumination;

    public LampBosch(boolean isOn, Integer ilumination) {
        this.ilumination = ilumination;
        this.setOn(isOn);
    }

    @Override
    public Integer getIlumination() {
         //this.turnOff();
        return ilumination;
    }


    @Override
    public void act() {
        //if(this.getOn()==true) this.turnOff();
    }

    @Override
    public void act(boolean test) {

    }
}
