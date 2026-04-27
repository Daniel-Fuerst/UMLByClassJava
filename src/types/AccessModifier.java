package types;

public enum AccessModifier {
    PUBLIC('+'), PRIVATE('-'), PROTECTED('#'), DEFAULT(' ');

    final char umlNotation;

    AccessModifier(char umlNotation) {
        this.umlNotation = umlNotation;
    }

    public char getUmlNotation() {
        return umlNotation;
    }

    public static AccessModifier getAccessModifier(String input) {
        for (AccessModifier accessModifier : values()) {
            if (input.toUpperCase().contains(accessModifier.name())) return accessModifier;
        }
        return DEFAULT;
    }
}
