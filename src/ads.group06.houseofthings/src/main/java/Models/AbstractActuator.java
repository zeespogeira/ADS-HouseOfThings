package Models;

public abstract class AbstractActuator {
    private static int count = 0;

    public AbstractActuator(){
        this.id = ++count;
    }

    public abstract void act(boolean flag);
    protected int id;

    public int getId(){
        return this.id;
    }
}
