package infrastructure;

public class Condition {
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

    public boolean isMet(int sensorId, double value){
        if(this.sensorId == sensorId){
            //TODO: check condition
            checkCondition(value);
        }

        return  isMet;
    }

    private void checkCondition(double value){
        this.isMet = Comparer.compare(referenceValue, operator, value);
    }
}
