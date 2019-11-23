import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    static final List<Character> whitespaces = List.of(
            ' ', '\t', '\n'
    );
    static final List<Character> alphabets = List.of(
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
            'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't',
            'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D',
            'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N',
            'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X',
            'Y', 'Z');
    static final List<Character> numbers = List.of(
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'
    );

    static enum LexicalState implements State {
        WHITESPACE,
        SYMBOL {
            @Override public boolean isEnd() { return true; }
            @Override public String toString() {return "SYMBOL"; }
        } ,
        NUMBER {
            @Override public boolean isEnd() { return true; }
            @Override public String toString() {return "NUMBER"; }
        },
        TMP,
        STRING{
            @Override public boolean isEnd() { return true; }
            @Override public String toString() {return "STRING"; }
        },
        COMMENT
    }
    public static void main(String[] args) {
        final RuleBook<Character> rules = new RuleBook<>();
        final NFA<Character> nfa = new NFA<>(LexicalState.WHITESPACE, rules);
        for (char c: whitespaces) {
            for (LexicalState l: LexicalState.values()) {
                rules.setRule(new Rule<>(l, c, LexicalState.WHITESPACE));
            }
        }
        for (char c: alphabets) {
            rules.setRule(new Rule<>(LexicalState.WHITESPACE, c, LexicalState.SYMBOL));
            rules.setRule(new Rule<>(LexicalState.SYMBOL, c, LexicalState.SYMBOL));
            rules.setRule(new Rule<>(LexicalState.TMP, c, LexicalState.TMP));
            rules.setRule(new Rule<>(LexicalState.COMMENT, c, LexicalState.COMMENT));
        }
        for (char c: numbers) {
            rules.setRule(new Rule<>(LexicalState.WHITESPACE, c, LexicalState.NUMBER));
            rules.setRule(new Rule<>(LexicalState.NUMBER, c, LexicalState.NUMBER));
            rules.setRule(new Rule<>(LexicalState.TMP, c, LexicalState.TMP));
            rules.setRule(new Rule<>(LexicalState.COMMENT, c, LexicalState.COMMENT));
        }
        /* STRING */
        rules.setRule(new Rule<>(LexicalState.WHITESPACE, '"', LexicalState.TMP));
        rules.setRule(new Rule<>(LexicalState.TMP, '"', LexicalState.STRING));
        /* COMMENT */
        rules.setRule(new Rule<>(LexicalState.WHITESPACE, '#', LexicalState.COMMENT));
        rules.setRule(new Rule<>(LexicalState.COMMENT, '#', LexicalState.WHITESPACE));
        List<Character> input;
        input = Stream.of('h', 'o', 'g', 'e').collect(Collectors.toList());
        System.out.println(nfa.isEnd(input));
        input = Stream.of('1', '0', '0').collect(Collectors.toList());
        System.out.println(nfa.isEnd(input));
        input = Stream.of('"', 'p', 'i', 'y', 'o', '"').collect(Collectors.toList());
        System.out.println(nfa.isEnd(input));
    }
}
