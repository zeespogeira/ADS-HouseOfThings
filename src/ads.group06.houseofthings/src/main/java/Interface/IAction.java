package Interface;

import Models.AbstractActuator;
import infrastructure.SensorReading;

import java.util.concurrent.atomic.AtomicInteger;

public interface IAction {
    void addActuator(AbstractActuator actuator);
    void removeActuator(AtomicInteger actuatorId);
    void execute(SensorReading sensorReading);
    boolean hasConditionWithSensor(int sensorId);
}
