package Sensors.Thermometer;


import Models.AbstractSensor;
import infrastructure.RandomValue;

public abstract class Thermometer extends AbstractSensor {
    private Integer temperature;


    public Thermometer() {
        RandomValue randomValue=new RandomValue(-15, 40);
        this.temperature=Integer.valueOf(randomValue.getRandom());
    }

    @Override
    public AbstractSensor sense() {
        RandomValue randomValue=new RandomValue(-15, 40);
        this.temperature=Integer.valueOf(randomValue.getRandom());
        return this;
    }

    public Integer getTemperature() {
        return temperature;
    }

    public void setTemperature(Integer temperature) {
        this.temperature = temperature;
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public Object getReading() {
        return this.getTemperature();
    }
}
