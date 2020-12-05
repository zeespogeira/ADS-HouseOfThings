package Sensors.Thermometer;


import Models.AbstractSensor;
import infrastructure.RandomValue;

public abstract class Thermometer extends AbstractSensor {
    private Double temperature;


    public Thermometer() {
        //RandomValue randomValue=new RandomValue(-15, 40);
        //this.temperature=Double.valueOf(randomValue.getRandom());
        this.temperature=-2.8;
    }

    @Override
    public AbstractSensor sense() {
        RandomValue randomValue=new RandomValue(-15, 40);
        this.temperature=Double.valueOf(randomValue.getRandom());
        return this;
    }

    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public Double getReading() {
        return this.getTemperature();
    }
}
