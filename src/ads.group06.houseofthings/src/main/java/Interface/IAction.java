package Interface;

import Models.AbstractActuator;
import infrastructure.SensorReading;

public interface IAction {
    void addActuator(AbstractActuator actuator);
    void removeActuator(int actuatorId);
    void execute(SensorReading sensorReading);
    boolean hasConditionWithSensor(int sensorId);
}
