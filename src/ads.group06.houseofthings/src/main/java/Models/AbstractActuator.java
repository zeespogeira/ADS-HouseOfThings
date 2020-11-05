package Models;

public abstract class AbstractActuator {
    //private static int count = 0;
    final public int id;

    public AbstractActuator(int id){
        this.id = id;
    }

    public abstract void act(boolean flag);
   // protected int id;

    public int getId(){
        return this.id;
    }
}
