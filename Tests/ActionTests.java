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
    public void GivenACondition_WithEqualOperator_ShouldBeMet() {
        //given
        int sensorId = 1;
        Condition condition = new Condition(sensorId, 123, Operator.EQUAL);
        Condition condition02 = new Condition(sensorId, 124, Operator.EQUAL);
        Condition condition03 = new Condition(sensorId, 125, Operator.EQUAL);

        Lamp lampBosch = new LampBosch("true");
        Lamp lampPhilips = new LampPhilips(true);
        List<AbstractActuator> actuators = new ArrayList<AbstractActuator>();
        actuators.add(lampBosch);
        actuators.add(lampPhilips);


        List<Condition> conditions = new ArrayList<Condition>();
        conditions.add(condition);
        conditions.add(condition02);
        conditions.add(condition03);

        //when
        SensorReading sensorReading = new SensorReading(sensorId, 100);
        Action action = new Action(actuators, conditions);
        action.execute(sensorReading);


        List<AbstractActuator> actualActuators = action.getActuators();

        for (var actuator : actualActuators) {

//            assertEquals(true, actuator.);
        }
    }
    //then
//        assertEquals(true, condition.isMet(sensorId, sensorReading));
}
