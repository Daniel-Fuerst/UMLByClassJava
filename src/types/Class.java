package types;

public class Class {
    private String name;

    public Class(String name) {
        this.name = name;
    }

    public static Class valueOf(String input) {
        int indexOfClass = input.indexOf("class");
        return new Class((input.substring(indexOfClass + "class".length(), input.indexOf(" ", indexOfClass + "class".length() + 1))).trim());
    }

    @Override
    public String toString() {
        return name;
    }
}
