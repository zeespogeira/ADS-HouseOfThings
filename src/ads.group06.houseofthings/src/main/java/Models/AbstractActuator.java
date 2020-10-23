package Models;

public abstract class AbstractActuator {
    public AbstractActuator(int id){
        this.id = id;
    }

    public abstract void act();
    protected int id;

    public int getId(){
        return this.id;
    }
}
