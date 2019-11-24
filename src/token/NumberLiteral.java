package token;

import ast.Expression;

public class NumberLiteral extends Token implements Expression {
    private int value;
    public NumberLiteral(String value) {
        super(Tag.NUMBER);
        this.value = Integer.parseInt(value);
    }
    public int getValue() {
        return value;
    }
    @Override
    public String toString() {
        return String.format("Number(%d)", value);
    }
}
