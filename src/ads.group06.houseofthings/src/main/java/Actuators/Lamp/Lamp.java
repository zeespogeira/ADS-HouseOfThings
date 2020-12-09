package Actuators.Lamp;
import Actuators.ActuatorAction;
import Models.AbstractActuator;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicInteger;


public abstract class Lamp extends AbstractActuator implements Serializable {
    private boolean isOn;

    public Lamp() {
        this.isOn=false;
    }

    public Lamp(String name, boolean isOn) {
        super(name);
        this.isOn = isOn;
    }

    public Lamp(boolean isOn) {
        this.isOn = isOn;
    }

    /*public Lamp(boolean isOn) {
        this.isOn = isOn;
        }*/


    protected boolean turnOn(){
        isOn=true;
        return isOn;
    }

    public boolean turnOff(){
        isOn=false;
        return isOn;
    }

    //public abstract Integer getIlumination();

    @Override
    public void act(boolean state, ActuatorAction actuatorAction) {
        if(("set" + actuatorAction.getName()).equalsIgnoreCase("setState") && state){
            if(actuatorAction.getValue().equalsIgnoreCase("on")){ //if is on
                setState(true);
            } else if(actuatorAction.getValue().equalsIgnoreCase("off")){ //if is off
               setState(false);
            }
        }
        else{
            setState(false);
        }
    }

    @Override
    public void setState(boolean state) {
        isOn = state;
    }

    public boolean getisOn() {
        return isOn;
    }

    @Override
    public String toString() {
        return super.toString() + ", isOn=" + isOn;
    }

    @Override
    public String getState() {
        return ((this.getisOn()) ? "On" : "Off");
        /*if(this.getisOn()==true){
            return "On";
        }
        return "Off";*/
    }
}
