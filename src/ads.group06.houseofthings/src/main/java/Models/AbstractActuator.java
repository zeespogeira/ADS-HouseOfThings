package Models;

import java.util.concurrent.atomic.AtomicInteger;

public abstract class AbstractActuator {
    //static final AtomicInteger idGen = new AtomicInteger(1);
    protected String name;

    public AbstractActuator(String name){
        this.name = name;
        //idGen.getAndIncrement();
    }

    public void setName(String name) {
        this.name = name;
    }

    public AbstractActuator(){
        //idGen.getAndIncrement();
    }

    public abstract void act(boolean flag);
   // protected int id;


    public String getName() {
        return name;
    }

    public abstract Integer getId();

    //public abstract void setId();
    /*public AtomicInteger getId(){
        return idGen;
    }*/

    @Override
    public String toString() {
        return "name='" + name + '\'';
    }
}
