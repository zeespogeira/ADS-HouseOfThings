package Sensors.Humidity;


import Models.AbstractSensor;
import infrastructure.RandomValue;

public abstract class Humidity extends AbstractSensor {
    Integer humidity;

    public Humidity() {
        RandomValue randomValue=new RandomValue(0, 50);
        this.humidity=Integer.valueOf(randomValue.getRandom());
    }

    /*public Humidity(String humidity) {
        this.humidity = humidity;
    }*/

    @Override
    public AbstractSensor sense() {
        RandomValue randomValue=new RandomValue(0, 50);
        this.humidity=Integer.valueOf(randomValue.getRandom());
        return this;
    }

    @Override
    public String toString() {
        return super.toString() + "humidity=" + humidity;
    }

    public Integer getHumidity() {
        return humidity;
    }

    public void setHumidity(Integer humidity) {
        this.humidity = humidity;
    }
}
