package token;

import ast.Expression;

/* example: "hoge" "piyo" */
public class StringLiteral extends Token implements Expression {
    private String value;
    public StringLiteral(String value) {
        super(Tag.STRING);
        this.value = value;
    }
    public String getValue() {
        return value;
    }
    @Override
    public String toString() {
        return String.format("String(%s)", value);
    }
}
