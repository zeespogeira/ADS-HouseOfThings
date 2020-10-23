package Lamp;


import Models.AbstractActuator;

public abstract class Lamp extends AbstractActuator {
    private boolean isOn;

    public Lamp(int id) {
        super(id);
    }


    protected boolean turnOn(){
        isOn=true;
        return isOn;
    }

    public boolean turnOff(){
        isOn=false;
        return isOn;
    }

    public abstract Integer getIlumination();

    /*
    @Override
    public void act() {
        if(this.isOn==true) this.turnOff();
    }*/

    public boolean isOn() {
        return isOn;
    }

    public void setOn(boolean on) {
        isOn = on;
    }


}
