package Interface;

public interface IActuators {
    //O que o actuator faz
    void act();
    //abstract void act(boolean test);
    public abstract void act(boolean state);
}
