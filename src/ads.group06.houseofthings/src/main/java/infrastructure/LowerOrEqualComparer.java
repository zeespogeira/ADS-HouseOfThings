package infrastructure;

import Interface.IComparer;

public class LowerOrEqualComparer implements IComparer {
    @Override
    public boolean compare(double reference, double value) {
        return value <= reference;
    }
}
