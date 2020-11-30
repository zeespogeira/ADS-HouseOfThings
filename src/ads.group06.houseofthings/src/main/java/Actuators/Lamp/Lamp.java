package Actuators.Lamp;
import Models.AbstractActuator;

import java.util.concurrent.atomic.AtomicInteger;


public abstract class Lamp extends AbstractActuator {
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
    public void act(boolean state) {
        //if need to turn ON and the actuator is OFF then turn ON
        if(state && isOn == false){
            setState(true);
        }

        //if need to turn OFF and the actuator is ON then turn OFF
        if(state == false && isOn){
            setState(false);
        }
    }

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
    }
}
