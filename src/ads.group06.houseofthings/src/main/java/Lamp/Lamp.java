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


}
