package Interface;

import Models.AbstractActuator;

public interface IAction {
    void addActuator(AbstractActuator actuator);
    void removeActuator(int actuatorId);
    void execute(int sensorId, double value);
}
