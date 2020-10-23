package infrastructure;
import Interface.IAction;
import Models.AbstractActuator;

import java.util.List;
import java.util.stream.Collectors;

public class Action implements IAction {
    private List<AbstractActuator> actuators;
    private List<Condition> conditions;

    public Action(List<AbstractActuator> actuators, List<Condition> conditions){
        this.actuators = actuators;
        this.conditions = conditions;
    }

    private void notifyActionExecution(int sensorId, double value) {
        boolean controlFlag = false;

        //get list of conditions associated with the sensorId
        List<Condition> conditionsToCheck = conditions.stream()
                .filter(condition -> condition.getSensorId() == sensorId)
                .collect(Collectors.toList());

        //check if all the conditions are met
        boolean allConditionsMet = conditions.stream().allMatch(c->c.isMet(sensorId, value));

        for(var actuator : actuators){
            actuator.act(allConditionsMet);
        }
    }

    @Override
    public void addActuator(AbstractActuator actuator) {
        this.actuators.add(actuator);
    }

    @Override
    public void removeActuator(int actuatorId) {
        AbstractActuator actuator = this.actuators.stream()
                .filter(a->a.getId() == actuatorId)
                .findFirst()
                .orElse(null);

        if(actuator != null){
            this.actuators.remove(actuator);
        }
    }

    @Override
    public void execute(int sensorId, double value) {
        notifyActionExecution(sensorId, value);
    }
}
