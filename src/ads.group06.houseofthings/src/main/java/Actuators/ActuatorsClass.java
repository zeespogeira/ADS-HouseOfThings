package Actuators;

public abstract class ActuatorsClass {
    //public abstract void act();
    public ActuatorsClass(int id){
        this.id = id;
    }

    public abstract void act(boolean flag);
    protected int id;

    public int getId(){
        return this.id;
    }
}
