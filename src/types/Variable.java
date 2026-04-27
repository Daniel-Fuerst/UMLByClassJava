package types;

public class Variable {
    private final String name;
    private final String dataType;
    private final boolean readOnly;

    public Variable(String name, String dataType, boolean readOnly) {
        this.name = name;
        this.dataType = dataType;
        this.readOnly = readOnly;
    }

    public static Variable valueOf(String input) {
        boolean isReadOnly = input.contains("final");
        String name = getVariableNameByDeclaration(input);
        String type = getDataTypeByDeclaration(input);

        return new Variable(name, type, isReadOnly);
    }

    public static String getVariableNameByDeclaration(String declaration) {
        declaration = declaration.replace(";", "");
        String[] data = declaration.split(" ");
        int counter = 0;
        int counterOfNonKeywords = 0;

        for (String s : data) {
            if (s.equals("=")) break;

            if (javax.lang.model.SourceVersion.isKeyword(s)) {
                counter++;
            } else {
                counterOfNonKeywords++;
            }
        }

        if (counterOfNonKeywords >= 2) {
            return data[counter + counterOfNonKeywords - 1];
        } else {
            return data[counter];
        }
    }

    public static String getDataTypeByDeclaration(String declaration) {
        declaration = declaration.replace(";", "");
        String[] data = declaration.split(" ");
        int counter = 0;
        int counterOfNonKeywords = 0;

        for (String s : data) {
            if (s.equals("=")) break;

            if (javax.lang.model.SourceVersion.isKeyword(s)) {
                counter++;
            } else {
                counterOfNonKeywords++;
            }
        }

        if (counterOfNonKeywords >= 2) {
            return data[counter + counterOfNonKeywords - 2];
        } else {
            return data[counter - 1];
        }
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Variable v)) return false;
        return (this.readOnly == v.readOnly) &&
                (this.name.equals(v.name)) &&
                (this.dataType.equals(v.dataType));
    }

    @Override
    public String toString() {
        String output = String.format("%s: %s", name, dataType);
        return readOnly ? (output + " {readOnly}") : output;
    }
}
