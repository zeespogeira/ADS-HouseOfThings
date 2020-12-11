package Models;

import Interface.ISensor;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class AbstractSensor implements Serializable, ISensor {
    static final AtomicInteger idGen = new AtomicInteger(1);
    private Integer id;
    private String name;
    private String measuringUnit;
    private String whatIsMeasuring;

    public AbstractSensor(String name){
        this.name = name;
        this.id= idGen.getAndIncrement();
    }

    public AbstractSensor(){
        this.id= idGen.getAndIncrement();
    }


    public abstract AbstractSensor sense();

    public abstract Double getReading();

   public int getId(){
        return this.id;
    }

    @Override
    public String toString() {
        return "id=" + id +
                ", name='" + name + '\'' +
                ", measuringUnit='" + measuringUnit + '\'' +
                ", whatIsMeasuring='" + whatIsMeasuring+"'";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractSensor that = (AbstractSensor) o;
        return id.equals(that.id) &&
                name.equals(that.name)&&
                measuringUnit.equals(that.measuringUnit)&&
                whatIsMeasuring.equals(that.whatIsMeasuring);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMeasuringUnit() {
        return measuringUnit;
    }

    public void setMeasuringUnit(String measuringUnit) {
        this.measuringUnit = measuringUnit;
    }

    public String getWhatIsMeasuring() {
        return whatIsMeasuring;
    }

    public void setWhatIsMeasuring(String whatIsMeasuring) {
        this.whatIsMeasuring = whatIsMeasuring;
    }
}
