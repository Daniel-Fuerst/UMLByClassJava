package interpreter;

import types.*;
import types.Class;

public class LineOfCode {
    private final String lineInText;
    int bracketLayer;
    private final AccessModifier accessModifier;
    private final StatementType statementType;
    private final boolean isStatic;
    String umlOutput;

    public LineOfCode(String lineInText, int bracketLayer) {
        this.lineInText = lineInText.trim();
        this.bracketLayer = bracketLayer;
        isStatic = lineInText.contains("static");

        statementType = Typer.typeLine(this);
        accessModifier = AccessModifier.getAccessModifier(this.getLineInText());
        umlOutput = compileUMLNotation();
    }

    private String compileUMLNotation() {
        String string = accessModifier.getUmlNotation() + " ";

        switch (statementType) {
            case DECLARATION:
                string += Variable.valueOf(lineInText);
                break;
            case CONSTRUCTOR:
                string += Constructor.valueOf(lineInText);
                break;
            case CLASS:
                string += Class.valueOf(lineInText);
                break;
            case METHOD:
                string += Method.valueOf(lineInText);
                break;
            case NOTHING:
                return "";
        }

        return isStatic ? String.format("_%s_", string) : string;
    }

    public String getLineInText() {
        return lineInText;
    }

    public StatementType getStatementType() {
        return statementType;
    }

    public String getUmlOutput() {
        return umlOutput;
    }
}
