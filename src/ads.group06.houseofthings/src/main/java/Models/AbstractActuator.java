package Models;

import Interface.IActuators;

import java.util.concurrent.atomic.AtomicInteger;

//Abstract Factory
public abstract class AbstractActuator {

    static final AtomicInteger idGen = new AtomicInteger(1);
    private Integer id;
    protected String name;

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

    @Override
    public String toString() {
        return "id=" + id + ", name=" + name + '\'';
    }
}
