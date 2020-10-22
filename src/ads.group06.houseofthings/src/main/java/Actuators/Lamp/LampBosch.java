package Actuators.Lamp;

public class LampBosch extends Lamp {
    String ilumination;

    public LampBosch(boolean isOn, String ilumination) {
        this.ilumination = ilumination;
        this.setOn(isOn);
    }

    public LampBosch() {
    }

    public String getIlumination() {
         //this.turnOff();
        return ilumination;
    }

    public void setIlumination(String ilumination) {
        this.ilumination = ilumination;
    }

    @Override
    public void act() {
        //if(this.getOn()==true) this.turnOff();
    }

    @Override
    public String toString() {
        return super.toString() + "LampBosch{" +
                "ilumination='" + ilumination + '\'' +
                '}';
    }
}
