import Actuators.Lamp.Lamp;
import Actuators.Lamp.LampBosch;
import Actuators.Lamp.LampPhilips;
import org.junit.Test;

public class ActuatorTests {
    @Test
    public void ActionTest_One() {
        int sensorId = 1;


        Lamp lampBosch = new LampBosch();
        lampBosch.setState(true);
        //lampBosch.act(lampBosch.g());
        Lamp lampBosch2 = new LampBosch();
        //lampPhilips.setState(true);

        //ver se as lamapadas sao iguais com o equals()

    }
}
