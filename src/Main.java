import token.Token;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        List<Token> tokens = new Tokenizer().tokenize(new InputStreamReader(new FileInputStream("test.txt")));
        for (Token t: tokens) {
            System.out.println(t);
        }
    }
}
