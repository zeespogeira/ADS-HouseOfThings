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

    public void notifyActionExecution(int sensorId, double value) {
        boolean controlFlag = false;

        //get list of conditions associated with the sensorId
        List<Condition> conditionsToCheck = conditions.stream()
                .filter(condition -> condition.getSensorId() == sensorId)
                .collect(Collectors.toList());

        for(Condition condition : conditionsToCheck){
            if(!condition.isMet(value)){
                continue;
            }
            controlFlag = true;
        }

        if(controlFlag){
            for(var actuator : actuators){
                actuator.act();
            }
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
    public void execute() {
        for(AbstractActuator actuator : actuators){
            actuator.act();
        }
    }
}
