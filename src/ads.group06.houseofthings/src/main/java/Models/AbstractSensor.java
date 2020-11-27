package Models;

import Interface.ISensor;

import java.util.concurrent.atomic.AtomicInteger;

public abstract class AbstractSensor implements ISensor {
    static final AtomicInteger idGen = new AtomicInteger(1);
    private Integer id;
    private String name;

    public AbstractSensor(String name){
        this.name = name;
        this.id= idGen.getAndIncrement();
    }

    public AbstractSensor(){
        this.id= idGen.getAndIncrement();
    }


    public abstract AbstractSensor sense();

   public int getId(){
        return this.id;
    }

   //public abstract Integer getId();

    @Override
    public String toString() {
        return "id=" + id + ", " +
                "name='" + name + '\'';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
