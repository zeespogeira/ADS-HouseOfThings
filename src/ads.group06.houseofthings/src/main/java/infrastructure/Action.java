package infrastructure;
import Interface.IAction;
import Models.AbstractActuator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Action implements IAction {
    private List<AbstractActuator> actuators;
    private List<Condition> conditions;

    public Action(List<AbstractActuator> actuators, List<Condition> conditions){
        this.actuators = actuators;
        this.conditions = conditions;
    }

    public Action() {

    }

    public void execute(SensorReading sensorReading) {
        //check if all the conditions are met
        boolean allConditionsMet = conditions.stream().allMatch(c->c.isMet(sensorReading));

        //notify actuators to act
        for(var actuator : actuators){
            actuator.act(allConditionsMet);
        }
    }

    public List<AbstractActuator> getActuators() {
        return actuators;
        //s
    }

    @Override
    public boolean hasConditionWithSensor(int sensorId) {
        return conditions.stream().anyMatch(c->c.getSensorId() == sensorId);
    }

    @Override
    public void addActuator(AbstractActuator actuator) {
        this.actuators.add(actuator);
    }

    @Override
    public void removeActuator(AtomicInteger actuatorId) {
        AbstractActuator actuator = this.actuators.stream()
                .filter(a->a.getId() == actuatorId) //check this
                .findFirst()
                .orElse(null);

        if(actuator != null){
            this.actuators.remove(actuator);
        }
    }
}
