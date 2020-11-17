package Actuators.Lamp;

import java.util.concurrent.atomic.AtomicInteger;

public class LampBosch extends Lamp {
    String ilumination;
    //static final AtomicInteger idGen = new AtomicInteger(1);

    public LampBosch(String ilumination) {
        //super();
        this.ilumination = ilumination;
        //super.idGen.getAndIncrement();
    }

    public LampBosch(boolean isOn, String ilumination) {
        super(isOn);
        this.ilumination = ilumination;
        //super.idGen.getAndIncrement();
    }

    public LampBosch() {
        //super(idGen.getAndIncrement());
        //super.idGen.getAndIncrement();
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
