package syntax;

import ast.*;
import token.*;
import token.Symbol;

import java.util.Vector;

/* RDP: Recursive Descent Parsing */
public class Parser {
    private Token[] tokens;
    private int t; // tokens[t]: current token
    private Vector<Variable> variables;
    public Node parse(Token[] tokens) throws ParsingException {
        this.tokens = tokens;
        this.t = 0;
        this.variables = new Vector<>();
        declaration();
        definition();
        for (Variable v: variables) System.out.println(String.format("(%s: %s)", v.type, v.name)); // TODO remove
        return null;
    }
    /* ( SYMBOL SYMBOL ( ',' SYMBOL SYMBOL )* )? ';' */
    private void declaration() throws ParsingException {
        if (match(Tag.SYMBOL)) {
            String type = consume_symbol();
            String name = consume_symbol();
            variables.add(new Variable(type, name));
            while (match(',')) {
                consume(',');
                type = consume_symbol();
                name = consume_symbol();
                variables.add(new Variable(type, name));
            }
            consume(';');
        }
    }
    /* ( SYMBOL ':=' expression ( ',' SYMBOL ':=' expression ) ';' )? */
    private void definition() throws ParsingException{
        Vector<Assignment> program = new Vector<>();
        if (match(Tag.SYMBOL)) {
            String left = consume_symbol();
            consume(':');
            consume('=');
            Expression right = expression();
            program.add(new Assignment(left, right));
            while (match(',')) {
                consume(',');
                left = consume_symbol();
                consume(':');
                consume('=');
                right = expression();
                program.add(new Assignment(left, right));
            }
            consume(';');
        }
        for (Assignment a: program) System.out.println(a); // TODO remove
    }
    /* 'data' SYMBOL '(' ( SYMBOL SYMBOL ( ',' SYMBOL SYMBOL )* )? ')' '{' ( function )* '}' */
    private void arithmetic_data() throws ParsingException {
        consume('d'); consume('a'); consume('t'); consume('a');
        String name = consume_symbol();
        consume('(');
        if (match(Tag.SYMBOL)) {
            String type = consume_symbol();
            name = consume_symbol();
            while (match(',')) {
                consume(',');
                type = consume_symbol();
                name = consume_symbol();
            }
        }
        consume('{');
        function(); // TODO introduce loop
        consume('}');
    }
    /* SYMBOL SYMBOL '(' ( SYMBOL SYMBOL ( ',' SYMBOL SYMBOL )* )? ')' '{' '}' */
    private void procedure() throws ParsingException {
        String type = consume_symbol();
        String name = consume_symbol();
        consume('(');
        if (match(Tag.SYMBOL)) {
            type = consume_symbol();
            name = consume_symbol();
            while (match(',')) {
                consume(',');
                type = consume_symbol();
                name = consume_symbol();
            }
        }
        consume(')');
        consume('{');
        consume('}');
    }
    /* SYMBOL ':' SYMBOL ( ',' SYMBOL )* '=>' SYMBOL ';' expression */
    private void function() throws ParsingException {
        String name = consume_symbol();
        consume(':');
        String left_type = consume_symbol(); // TODO replace with loop
        consume('='); consume('>');
        String right_type = consume_symbol();
        consume(';');
        String left = consume_symbol();
        consume('-'); consume('>');
        Expression right = expression();
    }
    private Expression expression() throws ParsingException {
        if (match(Tag.NUMBER)) return (NumberLiteral) consume(Tag.NUMBER);
        else return (StringLiteral) consume(Tag.STRING);
    }
    private boolean match(int tag) {
        return tokens[t].tag == tag;
    }
    private Token consume(int tag) throws ParsingException {
        if (match(tag)) {
            return tokens[t++];
        } else {
            throw new ParsingException();
        }
    }
    private String consume_symbol() throws ParsingException {
        Symbol tmp = (Symbol) consume(Tag.SYMBOL);
        return tmp.getName();
    }
}
