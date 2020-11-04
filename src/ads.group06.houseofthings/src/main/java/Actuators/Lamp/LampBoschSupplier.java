package Actuators.Lamp;

import Models.AbstractActuator;

import java.util.function.Supplier;

public class LampBoschSupplier implements Supplier<AbstractActuator> {

    @Override
    public LampBosch get() {
        return new LampBosch();
    }
}
