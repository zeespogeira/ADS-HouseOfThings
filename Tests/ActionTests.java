import Actuators.Lamp.Lamp;
import Actuators.Lamp.LampBosch;
import Actuators.Lamp.LampPhilips;
import Models.AbstractActuator;
import infrastructure.Action;
import infrastructure.Condition;
import infrastructure.Operator;
import infrastructure.SensorReading;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;

public class ActionTests {
    @Test
    public void ActionTest_One() {
        int sensorId = 1;
        Condition condition01 = new Condition(sensorId, 123, Operator.EQUAL);
        Condition condition02 = new Condition(sensorId, 124, Operator.HIGHER);
        Condition condition03 = new Condition(sensorId, 125, Operator.LOWER);

        /*
        Lamp lampBosch = new LampBosch();
        lampBosch.setState(true);
        lampBosch.act(lampBosch.getisOn());
        Lamp lampPhilips = new LampPhilips();
        lampPhilips.setState(true);
        lampBosch.act(lampPhilips.getisOn());

        //Command Pattern
        List<AbstractActuator> actuators = new ArrayList<AbstractActuator>();
        actuators.add(lampBosch);
        actuators.add(lampPhilips);

        //Command Pattern (Verificar porque é só 1 tipo)
        List<Condition> conditions = new ArrayList<Condition>();
        conditions.add(condition01);
        conditions.add(condition02);
        conditions.add(condition03);
*/
        SensorReading sensorReading = new SensorReading(sensorId, 100);
        /*Action action = new Action("test", actuators, conditions);
        action.execute(sensorReading);*/


        /*List<AbstractActuator> actualActuators = action.getActuators();

        for (var actuator : actualActuators) {

//            assertEquals(true, actuator.);
        }*/
    }
}
