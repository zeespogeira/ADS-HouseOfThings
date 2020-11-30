package Actuators.Lamp;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicInteger;

public class LampPhilips extends Lamp implements Serializable {
   // private static final long serialVersionUID = 6529685098267757690L;
    public LampPhilips() {
    }


    /*public LampPhilips(boolean isOn) {
        super( isOn);
    }*/

    @Override
    public String toString() {
        return "LampPhilips{" +super.toString() +"}";
    }
}
