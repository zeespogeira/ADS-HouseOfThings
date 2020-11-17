package infrastructure;

public enum Operator {
    LOWER ("Lower"),
    LOWER_OR_EQUAL ("Lower or Equal"),
    EQUAL ("Equal"),
    HIGHER ("Higher"),
    HIGHER_OR_EQUAL ("Higher or Equal");

    private final String name;

    Operator(String s) {
        name = s;
    }

    public boolean equalsName(String otherName) {
        // (otherName == null) check is not needed because name.equals(null) returns false
        return name.equals(otherName);
    }

    public String toString() {
        return this.name;
    }
}

