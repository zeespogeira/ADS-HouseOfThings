package infrastructure;

import Interface.IAction;

import java.util.List;
import java.util.stream.Collectors;

public class Action implements IAction {
    private List<Condition> conditions;

    //TODO: add List<IActutor> actuators
    public Action( List<Condition> conditions){

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
            /* TODO: act actuators
            for(var actuator in Actuators){
                actuator.act();
            }
            */
        }
    }

    @Override
    public void addActuator() {
        
    }

    @Override
    public void removeActuator() {

    }

    @Override
    public void execute() {

    }
}
