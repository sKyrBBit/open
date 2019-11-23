import java.util.List;
import java.util.Vector;

public class Tokenizer {
    private static final List<Character> whitespaces = List.of(
            ' ', '\t', '\n'
    );
    private static final List<Character> alphabets = List.of(
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
            'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't',
            'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D',
            'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N',
            'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X',
            'Y', 'Z');
    private static final List<Character> numbers = List.of(
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'
    );
    static enum LexicalState implements State {
        WHITESPACE,
        SYMBOL {
            @Override public String toString() {return "SYMBOL"; }
        } ,
        NUMBER {
            @Override public String toString() {return "NUMBER"; }
        },
        STRING{
            @Override public String toString() {return "STRING"; }
        },
        COMMENT
    }
    public List<Token> tokenize() {
        final Vector<Token> tokens = new Vector<>();
        final RuleBook<Character> rules = new RuleBook<>();
        final DFA<Character> DFA = new DFA<>(LexicalState.WHITESPACE, rules);

        /* Rules */
        for (char c: whitespaces) {
            rules.setRule(new Rule<>(LexicalState.SYMBOL, c,LexicalState.WHITESPACE) {
                @Override public State next() { tokens.add(new Token(Tag.SYMBOL)); return super.next(); }
            });
            rules.setRule(new Rule<>(LexicalState.NUMBER, c,LexicalState.WHITESPACE)  {
                @Override public State next() { tokens.add(new Token(Tag.NUMBER)); return super.next(); }
            });
            rules.setRule(new Rule<>(LexicalState.WHITESPACE, c, LexicalState.WHITESPACE));
        }
        for (char c: alphabets) {
            rules.setRule(new Rule<>(LexicalState.WHITESPACE, c, LexicalState.SYMBOL));
            rules.setRule(new Rule<>(LexicalState.SYMBOL, c, LexicalState.SYMBOL));
            rules.setRule(new Rule<>(LexicalState.NUMBER, c, LexicalState.SYMBOL) {
                @Override public State next() { tokens.add(new Token(Tag.NUMBER)); return super.next(); }
            });
            rules.setRule(new Rule<>(LexicalState.STRING, c, LexicalState.STRING));
            rules.setRule(new Rule<>(LexicalState.COMMENT, c, LexicalState.COMMENT));
        }
        for (char c: numbers) {
            rules.setRule(new Rule<>(LexicalState.WHITESPACE, c, LexicalState.NUMBER));
            rules.setRule(new Rule<>(LexicalState.SYMBOL, c, LexicalState.SYMBOL));
            rules.setRule(new Rule<>(LexicalState.NUMBER, c, LexicalState.NUMBER));
            rules.setRule(new Rule<>(LexicalState.STRING, c, LexicalState.STRING));
            rules.setRule(new Rule<>(LexicalState.COMMENT, c, LexicalState.COMMENT));
        }
        rules.setRule(new Rule<>(LexicalState.WHITESPACE, '"', LexicalState.STRING));
        rules.setRule(new Rule<>(LexicalState.STRING, '"', LexicalState.WHITESPACE) {
            @Override public State next() { tokens.add(new Token(Tag.STRING)); return super.next(); }
        });
        rules.setRule(new Rule<>(LexicalState.WHITESPACE, '#', LexicalState.COMMENT));
        rules.setRule(new Rule<>(LexicalState.COMMENT, '#', LexicalState.WHITESPACE));

        /* Test */
        for (char c: "hoge 100 \"piyo\" ".toCharArray()) DFA.input(c);
        return tokens;
    }
}
