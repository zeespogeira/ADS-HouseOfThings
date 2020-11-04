package Sensors;

import Sensors.Thermometer.ThermometerBosch;
import Models.AbstractActuator;
import Models.AbstractSensor;
import Sensors.Humidity.HumidityBosch;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class DynamicReflectionSensorFactory {
    private static final Map<String, Class<? extends AbstractSensor>> registeredType= new HashMap<>();

    static {
        registeredType.put("HumidityBosch", HumidityBosch.class);
        registeredType.put("ThermometerBosch", ThermometerBosch.class);
    }

    public static void registerType(String type, Class<? extends AbstractSensor> _class){
        registeredType.put(type, _class);
    }


    public static AbstractSensor getSensor(String type) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Class<?> cClass = registeredType.get(type);
        Constructor actuatorConstructor = cClass.getDeclaredConstructor(new Class[] {});
        return (AbstractSensor)actuatorConstructor.newInstance(new Object[] {});

    }

}
