public class Main {
    public static void main(String[] args) {
        for (Token t: new Tokenizer().tokenize()) {
            System.out.println(t.tag);
        }
    }
}
