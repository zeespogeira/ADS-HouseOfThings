package Actuators.VaccumCleaner;

import Actuators.ActuatorAction;
import infrastructure.RandomValue;

import java.io.Serializable;

public class VaccumCleanerSamsung extends VaccumCleaner implements Serializable {
    Integer velocity;

    public VaccumCleanerSamsung() {
        this.setVelocity(0);
    }

    public VaccumCleanerSamsung(Integer velocity) {
        this.velocity = velocity;
    }

    public Integer getVelocity() {
        return velocity;
    }

    public void setVelocity(Integer velocity) {
        this.velocity = velocity;
    }

    @Override
    public void act(boolean state,  ActuatorAction actuatorAction) {
        //if need to turn ON and the actuator is OFF then turn ON
        if(state && super.isOn() == false){
            setState(true);
            RandomValue value=new RandomValue(0, 50);
            setVelocity(value.getRandom());
        }

        //if need to turn OFF and the actuator is ON then turn OFF
        if(state == false && super.isOn()){
            setState(false);
            setVelocity(0);
        }
    }


    @Override
    public String toString() {
        return "VaccumCleanerSamsung{" + super.toString() +
                ", velocity='" + velocity + '\'' +
                '}';
    }

    @Override
    public String getState() {
        String state;
        if (this.isOn()) {
            state = "On, " + this.getVelocity() + "%";
        } else {
            state = "Off";
        }
        return state;
    }

}
