package Actuators.Curtain;

import java.io.Serializable;

//Concrete Command
public class CurtainSonyA58 extends CurtainSony implements Serializable {

    public CurtainSonyA58() {
    }


    @Override
    public String toString() {
        return "name='" + name + "'";
    }
}
