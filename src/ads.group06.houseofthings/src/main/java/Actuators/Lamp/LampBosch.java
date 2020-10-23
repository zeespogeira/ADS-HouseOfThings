package Actuators.Lamp;

public class LampBosch extends Lamp {
    String ilumination;


    public LampBosch(String ilumination) {
        this.ilumination = ilumination;
    }

    public LampBosch(boolean isOn, String ilumination) {
        super(isOn);
        this.ilumination = ilumination;
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
    public String toString() {
        return super.toString() + "LampBosch{" +
                "ilumination='" + ilumination + '\'' +
                '}';
    }
}
