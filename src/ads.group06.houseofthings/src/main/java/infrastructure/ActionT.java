package infrastructure;

import Models.AbstractActuator;

public class ActionT {

    private String name;
    private Operator operator;
    private int ControlValue;
    private AbstractActuator actuator;

    public ActionT(String name, Operator operator, int controlValue, AbstractActuator actuator) {
        this.name = name;
        this.operator = operator;
        ControlValue = controlValue;
        this.actuator = actuator;
    }

    public int getControlValue() {
        return ControlValue;
    }

    public void setControlValue(int controlValue) {
        ControlValue = controlValue;
    }

    public Operator getOperator() {
        return operator;
    }

    public void setOperator(Operator operator) {
        this.operator = operator;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
