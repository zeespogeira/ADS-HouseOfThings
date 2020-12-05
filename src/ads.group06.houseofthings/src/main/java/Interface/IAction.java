package Interface;

import Models.AbstractActuator;
import Models.AbstractSensor;
import infrastructure.SensorReading;

import java.util.concurrent.atomic.AtomicInteger;

public interface IAction {
    void addActuator(AbstractActuator actuator);
    void removeActuator(Integer actuatorId);
    void execute(AbstractSensor sensorReading);
    boolean hasConditionWithSensor(int sensorId);
}
