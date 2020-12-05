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
    //private String value (from the "method" variable in MainTest)
    //private String? Object? value (from the value from the "method" variable)
    // TALVEZ uma class ActuatorAction que tem os 2 valores anteriores

    //FALTA valor para o act (quando a condição se concretizar) [valores do action do form da parte direita] ?


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

    //TEMOS DE VER. ASSim Nao da com a user interface
    //No MainTest-> se a action.getValueToAct() for boolean, inicializa ActionBoolean
                //se a action.getValueToAct() for Inteiro, inicializa Action...

    /*public void execute(SensorReading sensorReading) {
        //check if all the conditions are met
        boolean allConditionsMet = conditions.stream().allMatch(c->c.isMet(sensorReading));

        //Ou talvez verifica Aqui qual acao vai tomar.
        //Ex: IF

        //notify actuators to act
        for(var actuator : actuators){
            //Adiconei if. Assim posso tirar o state dos act()
            if(allConditionsMet==true){
                //actuator.act(ActuatorAction.getName(), ActuatorAction.getValue());
                actuator.act(actuatorAction);
            }
        }
    }*/

    public void execute(AbstractSensor sensorReading) {
        //check if all the conditions are met
        boolean allConditionsMet = conditions.stream().allMatch(c->c.isMet(sensorReading));

        //Ou talvez verifica Aqui qual acao vai tomar.
        //Ex: IF

        //notify actuators to act
        for(var actuator : actuators){
            //Adiconei if. Assim posso tirar o state dos act()
            if(allConditionsMet==true){
                //actuator.act(ActuatorAction.getName(), ActuatorAction.getValue());
                actuator.act(allConditionsMet, actuatorAction);
            }
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
        return conditions.stream().anyMatch(c->c.getSensorId() == sensorId);
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
                "name='" + actionName + '\'' +
                ", actuators=" + actuators.toString() +
                ", conditions=" + conditions +
                '}';
    }
}
