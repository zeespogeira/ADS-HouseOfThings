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
    public void act(boolean state, ActuatorAction actuatorAction) {
        //If conditions are all met
        if(state){
            //Check if the value is "setState". If it is enters
            if(("set" + actuatorAction.getName()).equalsIgnoreCase("setState")){
                //Checks if the value is "on"
                if(actuatorAction.getValue().equalsIgnoreCase("on")){ //if is on
                    setState(true);
                    RandomValue value=new RandomValue(0, 50);
                    setVelocity(value.getRandom());
                } else if(actuatorAction.getValue().equalsIgnoreCase("off")){ //if is off
                    setState(false);
                    setVelocity(0);
                }
            }
            //Check if the value is "setVelocity". If it is, enters
            if(("set" + actuatorAction.getName()).equalsIgnoreCase("setVelocity")){
                //Checks if the value is different than 0. If it is, the vaccum is on
                if(Integer.valueOf(actuatorAction.getValue())!=0){
                    setState(true);
                    setVelocity(Integer.valueOf(actuatorAction.getValue()));
                } else{
                    setState(false);
                    setVelocity(0);
                }
            }
        }
        else{
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
