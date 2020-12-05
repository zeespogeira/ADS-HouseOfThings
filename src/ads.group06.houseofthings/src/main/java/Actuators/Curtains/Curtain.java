package Actuators.Curtains;
import Models.AbstractActuator;

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

    //@Override
    public void act(boolean state) {
        //if need to OPEN and the actuator is CLOSED then turn OPEN
        if(state && isOpen == false){
            setState(true);
            this.percentage=100;
        }

        //if need to CLOSE and the actuator is OPEN then CLOSE
        if(state == false && isOpen){
            setState(false);
            this.percentage=0;
        }


    }

/*
    public void act(ActuatorActionInteger actuatorAction) {
        if(actuatorAction.getValue()==0){

        }



        if(percentage!=0 ){
            setState(true);
            this.percentage=percentage;
        }
        else{
            setState(false);
            this.percentage=0;
        }
        if(isOpen == false){
            setState(true);
            this.percentage=100;
        }

        //if need to CLOSE and the actuator is OPEN then CLOSE
        if(isOpen && actuatorAction.getName().equalsIgnoreCase("percentage")){
            setState(false);
            this.percentage=0;
        }
    }
*/

    @Override
    public String toString() {
        return super.toString() + ", isOpen=" + isOpen;
    }


    public void setState(boolean state) {
        isOpen = state;
    }

    @Override
    public String getState() {
        return ((this.isOpen()) ? "Open" : "Closed");
    }

    public boolean isOpen() {
        return isOpen;
    }
}
