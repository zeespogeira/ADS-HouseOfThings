package Models;

import java.util.concurrent.atomic.AtomicInteger;

public abstract class AbstractActuator {
    static final AtomicInteger idGen = new AtomicInteger(1);

    public AbstractActuator(){
        idGen.getAndIncrement();
    }

    public abstract void act(boolean flag);
   // protected int id;


    public static AtomicInteger getId() {
        return idGen;
    }

    @Override
    public String toString() {
        return "id=" + idGen;
    }
}
