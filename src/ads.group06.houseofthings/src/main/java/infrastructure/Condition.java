package infrastructure;

public class Condition {
    private int sensorId;
    private double referenceValue;
    private Operator operator;

    public Condition(int sensorId , double referenceValue, Operator operator){
        this.sensorId = sensorId;
        this.referenceValue = referenceValue;
        this.operator = operator;
    }

    public int getSensorId(){
        return sensorId;
    }

    public boolean isMet(double value){
        return Comparer.compare(referenceValue, operator, value);
    }
}
