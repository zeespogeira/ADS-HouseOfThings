package Actuators.VaccumCleaner;
import Actuators.ActuatorAction;
import Models.AbstractActuator;

import java.io.Serializable;


public abstract class VaccumCleaner extends AbstractActuator implements Serializable {
    private boolean isOn;

    public VaccumCleaner() {
        this.isOn=false;
    }

    public VaccumCleaner(String name, boolean isOn) {
        super(name);
        this.isOn = isOn;
    }

    public VaccumCleaner(boolean isOn) {
        this.isOn = isOn;
    }

    @Override
    public void act(ActuatorAction actuatorAction) {
        //Check if the value is "setState". If it is enters
        if(("set" + actuatorAction.getName()).equalsIgnoreCase("setState")){
            //Checks if the value is "on"
            if(actuatorAction.getValue().equalsIgnoreCase("on")){ //if is on
                setState(true);
            } else if(actuatorAction.getValue().equalsIgnoreCase("off")){ //if is off
                setState(false);
            }
        }
    }

    @Override
    public void setState(boolean state) {
        isOn = state;
    }

    public boolean isOn() {
        return isOn;
    }


    @Override
    public String toString() {
        return super.toString() + ", isOn=" + isOn;
    }

    @Override
    public String getState() {
        return ((this.isOn()) ? "On" : "Off");
    }
}
