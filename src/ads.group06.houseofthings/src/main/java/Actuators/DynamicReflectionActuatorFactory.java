package Actuators;

import Actuators.Lamp.LampBosch;
import Actuators.Lamp.LampPhilips;
import Sensors.Thermometer.ThermometerBosch;
import Models.AbstractActuator;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class DynamicReflectionActuatorFactory {
    private static final Map<String, Class<? extends AbstractActuator>> registeredType= new HashMap<>();

    static {
        registeredType.put("LampBosch", LampBosch.class);
        registeredType.put("LampPhilips", LampPhilips.class);
    }

    public static void registerType(String type, Class<? extends AbstractActuator> _class){
        registeredType.put(type, _class);
    }


    public static AbstractActuator getActuator(String type) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Class<?> cClass = registeredType.get(type);
        Constructor actuatorConstructor = cClass.getDeclaredConstructor(new Class[] {});
        return (AbstractActuator)actuatorConstructor.newInstance(new Object[] {});

    }

}
