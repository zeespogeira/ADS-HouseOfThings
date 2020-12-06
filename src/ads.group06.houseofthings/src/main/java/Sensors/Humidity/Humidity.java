package Sensors.Humidity;


import Models.AbstractSensor;
import infrastructure.RandomValue;

public abstract class Humidity extends AbstractSensor {
    Double humidity;

    public Humidity() {
        RandomValue randomValue=new RandomValue(0, 50);
        //this.humidity=Double.valueOf(randomValue.getRandom());
        this.humidity=10.0;
    }

    /*public Humidity(String humidity) {
        this.humidity = humidity;
    }*/

    @Override
    public AbstractSensor sense() {
        //RandomValue randomValue=new RandomValue(0, 50);
        //this.humidity=Double.valueOf(randomValue.getRandom());
        this.humidity=10.0;
        return this;
    }

    @Override
    public String toString() {
        return super.toString() + ", humidity=" + humidity;
    }

    public Double getHumidity() {
        return humidity;
    }

    public void setHumidity(Double humidity) {
        this.humidity = humidity;
    }

    @Override
    public Double getReading() {
        return this.getHumidity();
    }
}
