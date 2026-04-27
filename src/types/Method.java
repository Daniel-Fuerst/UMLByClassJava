package types;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Method {
    private String name;
    private ArrayList<String> parameters;
    private String returnType;

    public Method(String name, ArrayList<String> parameters, String returnType) {
        this.name = name;
        this.parameters = parameters;
        this.returnType = returnType;
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

        return String.format("%s(%s): %s", name, parametersUML, returnType);
    }

    public static Method valueOf(String input) {
        String name = getMethodNameByDeclaration(input);
        String returnType = getMethodReturnTypeByDeclaration(input);
        ArrayList<String> parameters = getParametersByDeclaration(input);

        return new Method(name, parameters, returnType);
    }

    public static String getMethodNameByDeclaration(String declaration) {
        declaration = declaration.replace(";", "");
        String[] data = declaration.split(" ");
        int counter = 0;

        for (String s : data) {
            if (s.contains("(")) break;
            counter++;
        }

        return data[counter].substring(0, data[counter].indexOf("("));
    }

    public static String getMethodReturnTypeByDeclaration(String declaration) {
        declaration = declaration.replace(";", "");
        String[] data = declaration.split(" ");
        int counter = 0;

        for (String s : data) {
            if (s.contains("(")) break;
            counter++;
        }

        return data[counter - 1];
    }

    private static ArrayList<String> getParametersByDeclaration(String declaration) {
        int firstBracket = declaration.indexOf("(");
        int firstClosingBracket = declaration.indexOf(")");
        String beforeParameters = declaration.substring(firstBracket + 1, firstClosingBracket);
        String[] beforeParametersSplitted = beforeParameters.split(",");

        return new ArrayList<>(List.of(beforeParametersSplitted));
    }
}
