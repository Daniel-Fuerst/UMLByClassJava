package launch;

import interpreter.Formatter;
import interpreter.LineOfCode;
import types.Variable;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class UMLByClass {
    private static final ArrayList<Variable> knownVariables = new ArrayList<>();

    static void main() {
        ArrayList<LineOfCode> lines = new ArrayList<>();
        ArrayList<String> umlOutput = new ArrayList<>();
        ArrayList<String> fileContent;
        int bracketLayer = 0;

        try {
            fileContent = new ArrayList<>(fetchFileContent());
        } catch (IOException e) {
            fileContent = new ArrayList<>();
            IO.println("IO-Exception: " + e.getMessage());
            System.exit(1);
        }

        for (String line : fileContent) {
            if (line.contains("{")) bracketLayer++;
            if (line.contains("}")) bracketLayer--;

            LineOfCode lineOfCode = new LineOfCode(line, bracketLayer);
            if (!lineOfCode.getUmlOutput().trim().isEmpty()) {
                lines.add(lineOfCode);
            }
        }

        Formatter fmt = new Formatter(lines);
        for (LineOfCode line : lines) {
            umlOutput.add(fmt.formatStatement(line));
        }

        for (String line : umlOutput) {
            IO.println(line);
        }

        fmt.finishUMLOutput();
    }

    private static List<String> fetchFileContent() throws IOException {
        String filePath = IO.readln("Enter File Path: ");
        return Files.readAllLines(Path.of(filePath));
    }

    public static boolean isAKnownVariable(Variable v) {
        return knownVariables.contains(v);
    }

    public static void addToKnownVariables(Variable v) {
        knownVariables.add(v);
    }
}
