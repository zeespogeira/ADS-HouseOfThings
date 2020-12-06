package Actuators.Lamp;

import Actuators.ActuatorAction;
import infrastructure.RandomValue;

import java.io.Serializable;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class LampBosch extends Lamp implements Serializable {
    Integer ilumination;

    public LampBosch() {
        this.setIlumination(0);
    }

    public LampBosch(Integer ilumination) {
        this.ilumination = ilumination;
    }

    public Integer getIlumination() {
        return ilumination;
    }

    public void setIlumination(Integer ilumination) {
        this.ilumination = ilumination;
    }


    //Template Method
    @Override
    public void act(boolean state,  ActuatorAction actuatorAction) {

        if(("set" + actuatorAction.getName()).equalsIgnoreCase("setState")){
            if(actuatorAction.getValue().equalsIgnoreCase("on")){ //if is on
                super.setState(true);
                System.out.println("On");
                RandomValue value=new RandomValue(0, 100);
                setIlumination(value.getRandom());
            } else if(actuatorAction.getValue().equalsIgnoreCase("off")){ //if is off
                setState(false);
                setIlumination(0);
            }
        }

        if(("set" + actuatorAction.getName()).equalsIgnoreCase("setIlumination")){
            if(Integer.valueOf(actuatorAction.getValue())!=0){
                super.setState(true);
                setIlumination(Integer.valueOf(actuatorAction.getValue()));
            } else {
                setState(false);
                setIlumination(0);
            }
        }
    }


    @Override
    public String toString() {
        return "LampBosch{" + super.toString() +
                ", ilumination='" + ilumination + '\'' +
                '}';
    }

    @Override
    public String getState() {
        String state;
        if (super.getisOn()==true) {
            state = "On, " + this.getIlumination();
        } else {
            state = "Off";
        }
        return state;
    }
}
