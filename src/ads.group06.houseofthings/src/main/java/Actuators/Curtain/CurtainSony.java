package Actuators.Curtain;
import Actuators.ActuatorAction;
import infrastructure.RandomValue;

import java.io.Serializable;

//Concrete Command
public class CurtainSony extends Curtain implements Serializable {

    public CurtainSony() {
    }


    @Override
    public String toString() {
        return "name='" + name + "'";
    }
}
