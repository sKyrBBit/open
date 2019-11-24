import lexical.Tokenizer;
import syntax.Parser;
import syntax.ParsingException;
import token.Token;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        Token[] tokens = new Tokenizer().tokenize(new InputStreamReader(new FileInputStream("test.txt")));
        try {
            new Parser().parse(tokens);
        } catch (ParsingException e) {
            System.out.println(e);
        }
    }
}
