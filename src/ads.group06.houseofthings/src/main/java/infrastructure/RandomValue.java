package infrastructure;

import java.util.Random;

public class RandomValue {
    Random r;
    private int low;
    private int high;
    private int result;

    public RandomValue(int low, int high) {
        this.r = new Random();
        this.low = low;
        this.high = high;
        this.result =  r.nextInt(high-low) + low;
    }

    public Integer getRandom(){
        return this.result;
    }
}
