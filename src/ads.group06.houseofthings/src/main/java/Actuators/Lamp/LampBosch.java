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
        this.setIlumination(0);
    }



    public Integer getIlumination() {
        return ilumination;
    }

    public void setIlumination(Integer ilumination) {
        this.ilumination = ilumination;
    }


    //Template Method
    @Override
    public void act(boolean state) {
        //if need to turn ON and the actuator is OFF then turn ON
        if(state && super.isOn() == false){
            setState(true);
            RandomValue value=new RandomValue(0, 100);
            setIlumination(value.getRandom());
        }

        //if need to turn OFF and the actuator is ON then turn OFF
        if(state == false && super.isOn()){
            setState(false);
            setIlumination(0);
        }
    }


    @Override
    public String toString() {
        return "LampBosch{" + super.toString() +
                ", ilumination='" + ilumination + '\'' +
                '}';
    }
}
