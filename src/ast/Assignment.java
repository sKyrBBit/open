package ast;

public class Assignment {
    String left;
    Expression right;
    public Assignment(String left, Expression right) {
        this.left = left;
        this.right = right;
    }
    @Override
    public String toString() {
        return String.format("%s := %s", left, right.toString());
    }
}
