package infrastructure;

import Interface.IComparer;

public class ComparerFactory {
    public static IComparer getComparer(Operator operator){
        IComparer comparer;
        switch (operator){
            case EQUAL:
                comparer = new EqualComparer();
                break;
            case LOWER:
                comparer = new EqualComparer();
                break;
            case HIGHER:
                comparer = new EqualComparer();
                break;
            case LOWER_OR_EQUAL:
                comparer = new EqualComparer();
                break;
            case HIGHER_OR_EQUAL:
                comparer = new EqualComparer();
                break;
            default:
                throw new IllegalArgumentException("Unkown operator");
        }
        return comparer;
    }
}
