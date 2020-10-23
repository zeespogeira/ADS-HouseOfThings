package Models;

public abstract class AbstractSensor {

    private static int count = 0;

    public AbstractSensor(){
        this.id = ++count;
    }

    public abstract void sense(boolean flag);
    protected int id;

    public int getId(){
        return this.id;
    }
}
