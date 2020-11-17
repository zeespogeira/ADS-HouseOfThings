package Actuators.Lamp;
import Models.AbstractActuator;

import java.util.concurrent.atomic.AtomicInteger;


public abstract class Lamp extends AbstractActuator {
    private boolean isOn;
    static final AtomicInteger idGen = new AtomicInteger(1);
    private Integer id;

    public Lamp() {
        this.id= idGen.getAndIncrement();
    }

    public Lamp(boolean isOn) {
        this.isOn = isOn;
        this.id= idGen.getAndIncrement();
        }


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

    public boolean isOn() {
        return isOn;
    }

    @Override
    public Integer getId() {
        return id;
    }

    /*@Override
    public void setId() {
        this.id=
    }*/

    @Override
    public String toString() {
        return "id=" + id + ", " + super.toString() + ", isOn=" + isOn;
    }
}
