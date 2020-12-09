package infrastructure;
import Actuators.ActuatorAction;
import Interface.IAction;
import Models.AbstractActuator;
import Models.AbstractSensor;

import java.io.Serializable;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Action implements IAction, Serializable {
    private String actionName;
    private List<AbstractActuator> actuators;
    private List<Condition> conditions;
    private ActuatorAction actuatorAction;

    public Action(String actionName, List<AbstractActuator> actuators, List<Condition> conditions, ActuatorAction actuatorAction) {
        this.actionName = actionName;
        this.actuators = actuators;
        this.conditions = conditions;
        this.actuatorAction = actuatorAction;
    }

    public Action() {

    }

    public String getName() {
        return actionName;
    }

    public void setName(String name) {
        this.actionName = name;
    }

    public void execute(AbstractSensor sensorReading) {
        //check if all the conditions are met
        boolean allConditionsMet = conditions.stream().allMatch(c->c.isMet(sensorReading));

        System.out.println("allConditionsMet " + allConditionsMet);
        //notify actuators to act
        for(var actuator : actuators){
                //command no abstractAct
                actuator.act(allConditionsMet, actuatorAction);
        }
    }

    public List<AbstractActuator> getActuators() {
        return actuators;
        //s
    }

    public List<Condition> getConditions() {
        return conditions;
        //s
    }

    public ActuatorAction getActuatorAction() {
        return actuatorAction;
    }

    @Override
    public boolean hasConditionWithSensor(int sensorId) {
        return conditions.stream().anyMatch(c->c.getSensor().getId() == sensorId);
    }

    @Override
    public void addActuator(AbstractActuator actuator) {
        this.actuators.add(actuator);
    }

    @Override
    public void removeActuator(Integer actuatorId) {
        AbstractActuator actuator = this.actuators.stream()
                .filter(a->a.getId() == actuatorId) //check this
                .findFirst()
                .orElse(null);

        if(actuator != null){
            this.actuators.remove(actuator);
        }
    }

    @Override
    public String toString() {
        return "Action{" +
                "actionName='" + actionName + '\'' +
                ", actuators=" + actuators +
                ", conditions=" + conditions +
                ", actuatorAction=" + actuatorAction +
                '}';
    }
}
