package Interface;

import Models.AbstractActuator;

public interface IAction {
    void addActuator(AbstractActuator actuator);
    void removeActuator(int actuatorId);
    void notifyExecution(int sensorId, double value);
}
