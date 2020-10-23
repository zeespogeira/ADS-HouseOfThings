package infrastructure;

import Interface.IComparer;

public class LowerComparer implements IComparer {
    @Override
    public boolean compare(double reference, double value) {
        return value < value;
    }
}
