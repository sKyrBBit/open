package token;

public class Symbol extends Token {
    private String name;
    public Symbol(String name) {
        super(Tag.SYMBOL);
        this.name = name;
    }
    @Override
    public String toString() {
        return String.format("Symbol(%s)", name);
    }
}
