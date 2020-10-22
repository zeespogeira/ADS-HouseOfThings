package infrastructure;

import Interface.IComparer;

public class EqualComparer implements IComparer {
    @Override
    public boolean compare(double reference, double value) {
        return reference == value;
    }
}
