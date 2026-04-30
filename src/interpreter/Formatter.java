package interpreter;

import types.StatementType;

import java.util.ArrayList;

public class Formatter {
    private String SEPARATOR_LINE = "";
    private int longestLine = Integer.MIN_VALUE;
    private StatementType lastHandledStatementType = StatementType.NOTHING;

    public Formatter(ArrayList<LineOfCode> lines) {
        for (LineOfCode line : lines) {
            if (line.getUmlOutput().length() > longestLine)
                longestLine = line.getUmlOutput().length();
        }

        for (int i = 1; i <= longestLine + 4; i++) {
            SEPARATOR_LINE += "#";
        }
        SEPARATOR_LINE += "\n";
    }

    public String formatStatement(LineOfCode statement) {
        String output = "";

        if (lastHandledStatementType != statement.getStatementType() && statement.getStatementType() != StatementType.NOTHING)
            output += SEPARATOR_LINE;

        lastHandledStatementType = statement.getStatementType();

        output += String.format("# %-" + longestLine + "s #", statement.umlOutput);

        return output;
    }

    public void finishUMLOutput() {
        IO.println(SEPARATOR_LINE);
    }
}
