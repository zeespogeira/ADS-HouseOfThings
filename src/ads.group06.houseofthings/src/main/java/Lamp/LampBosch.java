package Lamp;

public class LampBosch extends Lamp {
    Integer ilumination;

    public LampBosch(int id, boolean isOn, Integer ilumination) {
        super(id);
        this.ilumination = ilumination;
    }

    @Override
    public Integer getIlumination() {
         //this.turnOff();
        return ilumination;
    }
}
