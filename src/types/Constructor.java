package types;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Constructor {
    private String name;
    private ArrayList<String> parameters;

    public Constructor(String name, ArrayList<String> parameters) {
        this.name = name;
        this.parameters = parameters;
    }

    @Override
    public String toString() {
        String[] parametersUMLArr = new String[parameters.size()];

        for (int i = 0; i < parametersUMLArr.length; i++) {
            String[] parameterComponents = parameters.get(i).trim().split(" ");
            if (parameterComponents.length < 2) break;
            parametersUMLArr[i] = String.format("%s: %s, ", parameterComponents[1], parameterComponents[0]);
            if (i == parametersUMLArr.length - 1) parametersUMLArr[i] = parametersUMLArr[i].replace(",", "").trim();
        }

        String parametersUML = "";

        for (String s : parametersUMLArr) {
            parametersUML += s;
        }

        return String.format("%s(%s)", name, parametersUML);
    }

    public static Constructor valueOf(String input) {
        String name = getNameByDeclaration(input);
        ArrayList<String> parameters = getParametersByDeclaration(input);
        return new Constructor(name, parameters);
    }

    private static String getNameByDeclaration(String declaration) {
        int firstBracket = declaration.indexOf("(");
        String beforeParameters = declaration.substring(0, firstBracket);
        String[] beforeParametersSplit = beforeParameters.split(" ");

        if (beforeParametersSplit.length == 2) {
            return beforeParametersSplit[1];
        } else {
            return beforeParametersSplit[0];
        }
    }

    private static ArrayList<String> getParametersByDeclaration(String declaration) {
        int firstBracket = declaration.indexOf("(");
        int firstClosingBracket = declaration.indexOf(")");
        String beforeParameters = declaration.substring(firstBracket + 1, firstClosingBracket);
        String[] beforeParametersSplitted = beforeParameters.split(",");

        return new ArrayList<>(List.of(beforeParametersSplitted));
    }
}
