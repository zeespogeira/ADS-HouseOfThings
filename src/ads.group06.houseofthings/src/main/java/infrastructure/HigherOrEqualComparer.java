package infrastructure;

import Interface.IComparer;

public class HigherOrEqualComparer implements IComparer {

    @Override
    public boolean compare(double reference, double value) {
        return value >= reference;
    }
}
