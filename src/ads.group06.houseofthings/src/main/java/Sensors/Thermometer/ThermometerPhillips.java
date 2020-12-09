package Sensors.Thermometer;

import Models.AbstractSensor;
import infrastructure.RandomValue;

public class ThermometerPhillips extends Thermometer {

    public ThermometerPhillips() {
        super.setMeasuringUnit("F");
    }

    @Override
    public String toString() {
        return "" + super.toString();
    }

}
