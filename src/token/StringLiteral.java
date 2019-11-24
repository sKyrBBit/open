package token;

public class StringLiteral extends Token {
    private String value;
    public StringLiteral(String value) {
        super(Tag.STRING);
        this.value = value;
    }
    @Override
    public String toString() {
        return String.format("String(%s)", value);
    }
}
