package Actuators;

import Actuators.Lamp.LampBosch;
import Actuators.Lamp.LampPhilips;
import Sensors.Thermometer.ThermometerBosch;
import Models.AbstractActuator;
//import Interface.Actuators;


public class ActuatorsFactory {

    //use getShape method to get object of type shape
    public AbstractActuator getActuator(String actType){ //<T extends Actuators>
        if(actType == null){
            return null;
        }
        if(actType.equalsIgnoreCase("LAMPBOSCH")){
            return new LampBosch();

        } else if(actType.equalsIgnoreCase("LAMPPHILIPS")){
            return new LampPhilips();

        }

        return null;
    }
}
