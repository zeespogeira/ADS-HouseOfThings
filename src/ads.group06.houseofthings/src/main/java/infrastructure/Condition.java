package infrastructure;

import Models.AbstractSensor;

import java.io.Serializable;

public class Condition implements Serializable {

    private AbstractSensor sensor;
    private double referenceValue;
    private Operator operator;
    private boolean isMet;

    public Condition(AbstractSensor sensor , double referenceValue, Operator operator){
        this.sensor = sensor;
        this.referenceValue = referenceValue;
        this.operator = operator;
    }

    public AbstractSensor getSensor(){
        return sensor;
    }

    /*public boolean isMet(SensorReading sensorReading){
         if(this.sensorId == sensorReading.getSensorId()){
            checkCondition(sensorReading.getValue());
        }

        return  isMet;
    }*/

    public boolean isMet(AbstractSensor sensorReading){
        /*System.out.println("abstractSensor: "+sensorReading.toString());
        System.out.println("sensorId da condition: "+this.sensor);
        System.out.println(this.toString());*/
        if(this.sensor.equals(sensorReading)){
            checkCondition(sensorReading.getReading());
            //System.out.println("sensorReading.getReading(): " +sensorReading.getReading());
        }
        return  isMet;
    }

    //TODO: implement state pattern
    private void checkCondition(double value){
        this.isMet = Comparer.compare(referenceValue, operator, value);
        //System.out.println("isMet: "+ isMet);
    }

    @Override
    public String toString() {
        return "Condition{" +
                "sensor=" + sensor +
                ", referenceValue=" + referenceValue +
                ", operator=" + operator +
                ", isMet=" + isMet +
                '}';
    }

    public double getReferenceValue() {
        return referenceValue;
    }

    public Operator getOperator() {
        return operator;
    }
}
