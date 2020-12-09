package Actuators.Curtain;
import Actuators.ActuatorAction;
import infrastructure.RandomValue;

import java.io.Serializable;

//Concrete Command
public class CurtainSony extends Curtain implements Serializable {

    public CurtainSony() {
    }

    @Override
    public void act(ActuatorAction actuatorAction) {
        //Check if the value is "setState". If it is enters
        if(("set" + actuatorAction.getName()).equalsIgnoreCase("setState")){
            //Checks if the value is "on"
            if(actuatorAction.getValue().equalsIgnoreCase("on")){ //if is on
                setState(true);
                RandomValue value=new RandomValue(1, 100);
                setPercentage(value.getRandom());
            } else if(actuatorAction.getValue().equalsIgnoreCase("off")){ //if is off
                setState(false);
                setPercentage(0);
            }
        }
        //Check if the value is "setVelocity". If it is, enters
        if(("set" + actuatorAction.getName()).equalsIgnoreCase("setPercentage")){
            //Checks if the value is different than 0. If it is, the vaccum is on
            if(Integer.valueOf(actuatorAction.getValue())!=0){
                setState(true);
                setPercentage(Integer.valueOf(actuatorAction.getValue()));
            } else{
                setState(false);
                setPercentage(0);
            }
        }
    }

    @Override
    public String toString() {
        return "name='" + name + "'";
    }
}
