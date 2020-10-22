package infrastructure;

public class Comparer {
    public static boolean compare(double reference, Operator operator, double value){
            return ComparerFactory.getComparer(operator).compare(reference, value);
    }

}
