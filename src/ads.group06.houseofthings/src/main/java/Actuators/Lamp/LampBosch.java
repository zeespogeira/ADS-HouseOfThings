package Actuators.Lamp;

import java.util.concurrent.atomic.AtomicInteger;

public class LampBosch extends Lamp {
    String ilumination;
    //static final AtomicInteger idGen = new AtomicInteger(1);

    public LampBosch(String ilumination) {
        this.ilumination = ilumination;
    }

    public LampBosch(boolean isOn, String ilumination) {
        super(isOn);
        this.ilumination = ilumination;
    }

    public LampBosch() {
        super();
    }

    public String getIlumination() {
         //this.turnOff();
        return ilumination;
    }

    public void setIlumination(String ilumination) {
        this.ilumination = ilumination;
    }



    @Override
    public String toString() {
        return "LampBosch{" + super.toString() +
                ", ilumination='" + ilumination + '\'' +
                '}';
    }
}
