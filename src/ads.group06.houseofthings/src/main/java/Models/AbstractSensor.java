package Models;

import java.util.concurrent.atomic.AtomicInteger;

public abstract class AbstractSensor {

    static final AtomicInteger idGen = new AtomicInteger(1);
    //final public int id;

    public AbstractSensor(){
        idGen.getAndIncrement();
    }

    public abstract void sense(boolean flag);

   /* public int getId(){
        return this.id;
    }*/

    @Override
    public String toString() {
        return "id=" + idGen;
    }
}
