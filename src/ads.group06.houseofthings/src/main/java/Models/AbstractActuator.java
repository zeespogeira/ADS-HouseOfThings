package Models;

import Actuators.ActuatorAction;
import Interface.IActuators;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicInteger;


//Abstract Command
public abstract class AbstractActuator implements Serializable {

    static final AtomicInteger idGen = new AtomicInteger(1);

    private Integer id;
    protected String name;

    public AbstractActuator(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public AbstractActuator(String name){
        this.name = name;
        this.id= idGen.getAndIncrement();
    }
    public AbstractActuator(){
        this.id= idGen.getAndIncrement();
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getId(){
        return this.id;
    }

    public abstract String getState();

    public abstract void setState(boolean state);

    public abstract void act(boolean state, ActuatorAction actuatorAction);

    @Override
    public String toString() {
        return "id=" + id + ", name=" + name;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        AbstractActuator that = (AbstractActuator) obj;
        return id.equals(that.getId()) &&
                name.equals(that.getName());
    }
}
