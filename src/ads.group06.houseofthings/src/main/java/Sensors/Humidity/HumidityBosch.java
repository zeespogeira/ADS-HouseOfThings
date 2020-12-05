package Sensors.Humidity;


import Models.AbstractSensor;

public class HumidityBosch extends Humidity {

    /*public HumidityBosch(String humidity) {
        super(humidity);
    }*/

    public HumidityBosch() {
        super.setMeasuringUnit("%");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractSensor that = (AbstractSensor) o;
        return super.getId()==(that.getId()) &&
                super.getName().equals(that.getName())&&
                super.getMeasuringUnit().equals(that.getMeasuringUnit())&&
                getWhatIsMeasuring().equals(that.getWhatIsMeasuring());
    }
}
