package Actuators;

import java.io.Serializable;

public class ActuatorAction implements Serializable {
    private String name;
    private String value;

    public ActuatorAction(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "ActuatorAction{" +
                "name='" + name + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
