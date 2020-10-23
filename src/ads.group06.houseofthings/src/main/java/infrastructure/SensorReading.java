package infrastructure;

public class SensorReading {
    private int sensorId;
    private double value;

    public SensorReading(int sensorId, double value){

        this.sensorId = sensorId;
        this.value = value;
    }

    public int getSensorId(){
        return this.sensorId;
    }

    public double getValue(){
        return this.value;
    }
}
