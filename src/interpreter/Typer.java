package interpreter;

import launch.UMLByClass;
import types.AccessModifier;
import types.StatementType;
import types.Variable;

public class Typer {
    public static StatementType typeLine(LineOfCode line) {
        // Check for instance variables
        if (isInstanceVariableDeclaration(line)) {
            return (StatementType.DECLARATION);
        } else if (isConstructor(line)) {
            return (StatementType.CONSTRUCTOR);
        } else if (isClassDeclaration(line)) {
            return (StatementType.CLASS);
        } else if (isMethodDeclaration(line)) {
            return (StatementType.METHOD);
        } else {
            return (StatementType.NOTHING);
        }
    }

    private static boolean isInstanceVariableDeclaration(LineOfCode line) {
        if (line.bracketLayer == 1 &&
            line.getLineInText().contains(";")) {

            if (!line.getLineInText().contains("=") && line.getLineInText().contains("(") || line.getLineInText().contains(")")) {
                return false;
            }

            Variable variable = Variable.valueOf(line.getLineInText());
            if (UMLByClass.isAKnownVariable(variable)) return false;
            UMLByClass.addToKnownVariables(variable);
            return true;
        }
        return false;
    }

    private static boolean isConstructor(LineOfCode line) {
        if (line.getLineInText().contains(")") && line.getLineInText().contains("(") && line.getLineInText().contains("{")) {
            int firstBracket = line.getLineInText().indexOf("(");
            String beforeParameters = line.getLineInText().substring(0, firstBracket);
            String[] beforeParametersSplit = beforeParameters.split(" ");

            return (beforeParametersSplit.length == 2) || (beforeParametersSplit.length <= 1 && !(javax.lang.model.SourceVersion.isKeyword(beforeParametersSplit[0])));
        }
        return false;
    }

    private static boolean isClassDeclaration(LineOfCode line) {
        return line.getLineInText().contains("class");
    }

    private static boolean isMethodDeclaration(LineOfCode line) {
        if (line.getLineInText().contains(")") && line.getLineInText().contains("(") && line.getLineInText().contains("{")) {
            int firstBracket = line.getLineInText().indexOf("(");
            String beforeParameters = line.getLineInText().substring(0, firstBracket);
            String[] beforeParametersSplit = beforeParameters.split(" ");

            if (AccessModifier.getAccessModifier(line.getLineInText()) == AccessModifier.DEFAULT) {
                return beforeParametersSplit.length == 2;
            } else {
                return beforeParametersSplit.length > 2;
            }
        }
        return false;
    }
}
