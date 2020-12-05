package infrastructure;

import Models.AbstractActuator;

import java.util.List;

public class UpdateActuatorList {
    private List<Action> actionList;
    private List<AbstractActuator> actuatorList;

    public UpdateActuatorList(List<Action> actionList, List<AbstractActuator> abstractActuators) {
        this.actionList = actionList;
        this.actuatorList = abstractActuators;
    }


}
