package infrastructure;

import Models.AbstractSensor;

import java.io.Serializable;

public class Condition implements Serializable {
    //private static final long serialVersionUID = 6529685098267757690L;

    private int sensorId;
    private double referenceValue;
    private Operator operator;
    private boolean isMet;

    public Condition(int sensorId , double referenceValue, Operator operator){
        this.sensorId = sensorId;
        this.referenceValue = referenceValue;
        this.operator = operator;
    }

    public int getSensorId(){
        return sensorId;
    }

    /*public boolean isMet(SensorReading sensorReading){
         if(this.sensorId == sensorReading.getSensorId()){
            checkCondition(sensorReading.getValue());
        }

        return  isMet;
    }*/

    public boolean isMet(AbstractSensor sensorReading){
        if(this.sensorId == sensorReading.getId()){
            checkCondition(sensorReading.getReading());
        }

        return  isMet;
    }

    //TODO: implement state pattern
    private void checkCondition(double value){
        this.isMet = Comparer.compare(referenceValue, operator, value);
    }

    @Override
    public String toString() {
        return "Condition{" +
                "sensorId=" + sensorId +
                ", referenceValue=" + referenceValue +
                ", operator=" + operator +
                ", isMet=" + isMet +
                '}';
    }
}
