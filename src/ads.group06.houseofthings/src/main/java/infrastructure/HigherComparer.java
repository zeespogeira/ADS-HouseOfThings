package infrastructure;

import Interface.IComparer;

public class HigherComparer implements IComparer {
    @Override
    public boolean compare(double reference, double value) {
        return value > reference;
    }
}
