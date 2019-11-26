package token;

/* example: hoge, piyo */
public class Symbol extends Token {
    private String name;
    public Symbol(String name) {
        super(Tag.SYMBOL);
        this.name = name;
    }
    public String getName() {
        return name;
    }
    @Override
    public String toString() {
        return String.format("Symbol(%s)", name);
    }
}
