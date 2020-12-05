package Actuators;

public interface Act {
    boolean act(boolean state);
    abstract boolean act(boolean state, String name, Integer value);
    abstract boolean act(boolean state, String name,  String value);
}
