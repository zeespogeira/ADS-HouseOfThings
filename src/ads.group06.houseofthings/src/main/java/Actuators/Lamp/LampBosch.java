package Actuators.Lamp;

import infrastructure.RandomValue;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class LampBosch extends Lamp {
    Integer ilumination;

    //Construtores para teste
    /*public LampBosch(String ilumination) {
        this.ilumination = ilumination;
    }

    public LampBosch(boolean isOn, String ilumination) {
        super(isOn);
        this.ilumination = ilumination;
    }*/

    public LampBosch() {
        RandomValue value=new RandomValue(0, 100);
        setIlumination(value.getRandom());
    }



    public Integer getIlumination() {
         //this.turnOff();
        return ilumination;
    }

    public void setIlumination(Integer ilumination) {
        this.ilumination = ilumination;
    }



    @Override
    public String toString() {
        return "LampBosch{" + super.toString() +
                ", ilumination='" + ilumination + '\'' +
                '}';
    }
}
