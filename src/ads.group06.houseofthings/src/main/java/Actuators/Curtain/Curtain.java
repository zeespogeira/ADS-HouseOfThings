package Actuators.Curtain;
import Actuators.ActuatorAction;
import Models.AbstractActuator;
import infrastructure.RandomValue;

import java.io.Serializable;


public abstract class Curtain extends AbstractActuator implements Serializable {
    private boolean isOpen;
    private Integer percentage;

    public Curtain() {
        this.isOpen=false;
        this.percentage=0;
    }


    public Curtain(Integer id, String name, boolean isOpen, Integer percentage) {
        super(id, name);
        this.isOpen = isOpen;
        this.percentage = percentage;
    }

    public Curtain(String name, boolean isOpen, Integer percentage) {
        super(name);
        this.isOpen = isOpen;
        this.percentage = percentage;
    }

    public Curtain(boolean isOpen, Integer percentage) {
        this.isOpen = isOpen;
        this.percentage = percentage;
    }

    public Curtain(boolean isOpen) {
        this.isOpen = isOpen;
    }

    //ACT para se for mais valores

    @Override
    public void act(boolean state,ActuatorAction actuatorAction) {
        if(state){
            //Check if the value is "setState". If it is enters
            if(("set" + actuatorAction.getName()).equalsIgnoreCase("setState")){
                //Checks if the value is "on"
                if(actuatorAction.getValue().equalsIgnoreCase("on")){ //if is on
                    setState(true);
                    RandomValue value=new RandomValue(1, 100);
                    setPercentage(value.getRandom());
                } else if(actuatorAction.getValue().equalsIgnoreCase("off")){ //if is off
                    setState(false);
                    setPercentage(0);
                }
            }
            //Check if the value is "setVelocity". If it is, enters
            if(("set" + actuatorAction.getName()).equalsIgnoreCase("setPercentage")){
                //Checks if the value is different than 0. If it is, the vaccum is on
                if(Integer.valueOf(actuatorAction.getValue())!=0){
                    setState(true);
                    setPercentage(Integer.valueOf(actuatorAction.getValue()));
                } else{
                    setState(false);
                    setPercentage(0);
                }
            }
        }else{
            setState(false);
            setPercentage(0);
        }
    }

    @Override
    public String toString() {
        return super.toString() +
                "isOpen=" + isOpen +
                ", percentage=" + percentage;
    }

    @Override
    public void setState(boolean state) {
        isOpen = state;
    }

    public Integer getPercentage() {
        return percentage;
    }

    public void setPercentage(Integer percentage) {
        this.percentage = percentage;
    }

    @Override
    public String getState() {
        String state;
        if (this.isOpen()) {
            state = "On, " + this.getPercentage() + "%";
        } else {
            state = "Off";
        }
        return state;
    }

    public boolean isOpen() {
        return isOpen;
    }
}
