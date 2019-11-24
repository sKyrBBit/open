package token;

public class NumberLiteral extends Token {
    private int value;
    public NumberLiteral(String value) {
        super(Tag.NUMBER);
        this.value = Integer.parseInt(value);
    }
    @Override
    public String toString() {
        return String.format("Number(%d)", value);
    }
}
