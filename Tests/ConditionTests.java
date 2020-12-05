import infrastructure.Condition;
import infrastructure.Operator;
import infrastructure.SensorReading;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
/*
public class ConditionTests {
    @Test
    public void GivenACondition_WithEqualOperator_ShouldBeMet(){
        //given
        int sensorId = 1;
        Condition condition = new Condition(sensorId, 123, Operator.EQUAL);

        //when
        double sensorReading = 123;

        //then
        assertEquals(true, condition.isMet(new SensorReading(sensorId, sensorReading)));

    }

    @Test
    public void GivenACondition_WithEqualOperator_ShouldNotBeMet(){
        //given
        int sensorId = 1;
        Condition condition = new Condition(sensorId, 123, Operator.EQUAL);

        //when
        double sensorReading = 123456;

        //then
        //assertEquals(false, condition.isMet(sensorId, sensorReading));
        assertEquals(true, condition.isMet(new SensorReading(sensorId, sensorReading)));
    }

    @Test
    public void GivenACondition_WithLowerOperator_ShouldBeMet(){
        //given
        int sensorId = 1;
        Condition condition = new Condition(sensorId, 123, Operator.LOWER);

        //when
        double sensorReading = 12;

        //then
        //assertEquals(true, condition.isMet(sensorId, sensorReading));
        assertEquals(true, condition.isMet(new SensorReading(sensorId, sensorReading)));
    }

    @Test
    public void GivenACondition_WithLowerOperator_ShouldNotBeMet(){
        //given
        int sensorId = 1;
        Condition condition = new Condition(sensorId, 123, Operator.LOWER);

        //when
        double sensorReading = 123;

        //then
        //assertEquals(false, condition.isMet(sensorId, sensorReading));
        assertEquals(false, condition.isMet(new SensorReading(sensorId, sensorReading)));
    }

    @Test
    public void GivenACondition_WithLowerOrEqualOperator_ShouldBeMet(){
        //given
        int sensorId = 1;
        Condition condition = new Condition(sensorId, 123, Operator.LOWER_OR_EQUAL);

        //when
        double sensorReading = 123;

        //then
        //assertEquals(true, condition.isMet(sensorId, sensorReading));
        assertEquals(true, condition.isMet(new SensorReading(sensorId, sensorReading)));
    }

    @Test
    public void GivenACondition_WithLowerOrEqualOperator_ShouldNotBeMet(){
        //given
        int sensorId = 1;
        Condition condition = new Condition(sensorId, 123, Operator.LOWER_OR_EQUAL);

        //when
        double sensorReading = 1234;

        //then
        //assertEquals(false, condition.isMet(sensorId, sensorReading));
        assertEquals(false, condition.isMet(new SensorReading(sensorId, sensorReading)));
    }

    @Test
    public void GivenACondition_WithHigherOperator_ShouldBeMet(){
        //given
        int sensorId = 1;
        Condition condition = new Condition(sensorId, 123, Operator.HIGHER);

        //when
        double sensorReading = 1234;

        //then
       // assertEquals(true, condition.isMet(sensorId, sensorReading));
        assertEquals(true, condition.isMet(new SensorReading(sensorId, sensorReading)));
    }

    @Test
    public void GivenACondition_WithHigherOperator_ShouldNotBeMet(){
        //given
        int sensorId = 1;
        Condition condition = new Condition(sensorId, 123, Operator.HIGHER);

        //when
        double sensorReading = 123;

        //then
        //assertEquals(false, condition.isMet(sensorId, sensorReading));
        assertEquals(true, condition.isMet(new SensorReading(sensorId, sensorReading)));
    }

    @Test
    public void GivenACondition_WithHigherOrEqualOperator_ShouldBeMet(){
        //given
        int sensorId = 1;
        Condition condition = new Condition(sensorId, 123, Operator.HIGHER_OR_EQUAL);

        //when
        double sensorReading = 123;

        //then
        //assertEquals(true, condition.isMet(sensorId, sensorReading));
        assertEquals(true, condition.isMet(new SensorReading(sensorId, sensorReading)));
    }

    @Test
    public void GivenACondition_WithHigherOrEqualOperator_ShouldNotBeMet(){
        //given
        int sensorId = 1;
        Condition condition = new Condition(sensorId, 123, Operator.HIGHER_OR_EQUAL);

        //when
        double sensorReading = 123;

        //then
        //assertEquals(true, condition.isMet(sensorId, sensorReading));
        assertEquals(true, condition.isMet(new SensorReading(sensorId, sensorReading)));
    }
}
*/