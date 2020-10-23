import infrastructure.Condition;
import infrastructure.Operator;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class ConditionTests {
    @Test
    public void GivenACondition_WithASetup_ShouldReturnAsExpected(){
        //given
        int sensorId = 1;
        Condition condition = new Condition(sensorId, 123, Operator.EQUAL);

        //when
        double sensorReading = 123456;

        //then
        assertEquals(false, condition.isMet(sensorId, sensorReading));
    }
}
