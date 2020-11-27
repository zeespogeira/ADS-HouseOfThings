package Interface;

import Models.AbstractSensor;

public interface ISensor {

    abstract AbstractSensor sense();
    abstract Object getReading();
}
