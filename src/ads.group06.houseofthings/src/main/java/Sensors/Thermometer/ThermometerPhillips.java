package Sensors.Thermometer;

import Models.AbstractSensor;
import infrastructure.RandomValue;

public class ThermometerPhillips extends Thermometer {

    //Integer realFeel;
    /*public ThermometerPhillips(String temperature) {
        super(temperature);
    }*/

    public ThermometerPhillips() {
        super.setMeasuringUnit("F");
        /*RandomValue randomValue=new RandomValue(-15, 40);
        this.realFeel=Integer.valueOf(randomValue.getRandom());*/
    }

    /*@Override
    public AbstractSensor sense() {
        RandomValue randomValue=new RandomValue(-15, 40);
        setRealFeel(Integer.valueOf(randomValue.getRandom()));
        RandomValue randomValue2=new RandomValue(-15, 40);
        super.setTemperature(Integer.valueOf(randomValue2.getRandom()));
        return this;
    }*/

    @Override
    public String toString() {
        return "" + super.toString();
    }

    /*public Integer getRealFeel() {
        return realFeel;
    }

    public void setRealFeel(Integer realFeel) {
        this.realFeel = realFeel;
    }*/
}
