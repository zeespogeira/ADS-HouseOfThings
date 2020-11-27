package Sensors.Thermometer;

import Models.AbstractSensor;
import infrastructure.RandomValue;

import java.util.Random;

public class ThermometerPhillips extends Thermometer {

    Integer realFeel;
    /*public ThermometerPhillips(String temperature) {
        super(temperature);
    }*/

    public ThermometerPhillips() {
        RandomValue randomValue=new RandomValue(-15, 40);
        this.realFeel=Integer.valueOf(randomValue.getRandom());
    }

    @Override
    public AbstractSensor sense() {
        RandomValue randomValue=new RandomValue(-15, 40);
        setRealFeel(Integer.valueOf(randomValue.getRandom()));
        return this;
    }

    @Override
    public String toString() {
        return "" + super.toString() +
                ", realFeel=" + realFeel;
    }

    public Integer getRealFeel() {
        return realFeel;
    }

    public void setRealFeel(Integer realFeel) {
        this.realFeel = realFeel;
    }
}
