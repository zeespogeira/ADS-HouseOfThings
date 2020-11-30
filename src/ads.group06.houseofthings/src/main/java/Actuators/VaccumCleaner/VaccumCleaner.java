package Actuators.VaccumCleaner;
import Models.AbstractActuator;


public abstract class VaccumCleaner extends AbstractActuator {
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
    public String toString() {
        return super.toString() + ", isOn=" + isOn;
    }

    @Override
    public String getState() {
        return ((this.isOn()) ? "On" : "Off");
    }
}
