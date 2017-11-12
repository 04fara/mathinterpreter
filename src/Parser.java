import java.util.ArrayList;

class Parser {
    enum TokenType {
        LEFT_PAREN, RIGHT_PAREN,
        MINUS, PLUS, SLASH, STAR, AND, OR, XOR,
        EQUAL_EQUAL, GREATER, GREATER_EQUAL, LESS, LESS_EQUAL,
        INTEGER, EOF
    }

    private final ArrayList<Token> tokens;
    private int current = 0;

    Parser(ArrayList<Token> tokens) {
        this.tokens = tokens;
    }

    Expression parse() {
        return or();
    }

    private Expression or() {
        Expression expression = and();
        while (match(TokenType.OR)) {
            Token operator = previous();
            Expression right = and();
            expression = new Logical(expression, operator, right);
        }
        return expression;
    }

    private Expression and() {
        Expression expression = xor();
        while (match(TokenType.AND)) {
            Token operator = previous();
            Expression right = xor();
            expression = new Logical(expression, operator, right);
        }
        return expression;
    }

    private Expression xor() {
        Expression expression = equality();
        while (match(TokenType.XOR)) {
            Token operator = previous();
            Expression right = equality();
            expression = new Logical(expression, operator, right);
        }
        return expression;
    }

    private Expression equality() {
        Expression expression = comparison();
        while (match(TokenType.EQUAL_EQUAL)) {
            Token operator = previous();
            Expression right = comparison();
            expression = new Binary(expression, operator, right);
        }
        return expression;
    }

    private Expression comparison() {
        Expression expression = addition();
        while (match(TokenType.GREATER, TokenType.GREATER_EQUAL, TokenType.LESS, TokenType.LESS_EQUAL)) {
            Token operator = previous();
            Expression right = addition();
            expression = new Binary(expression, operator, right);
        }
        return expression;
    }

    private Expression addition() {
        Expression expression = multiplication();
        while (match(TokenType.MINUS, TokenType.PLUS)) {
            Token operator = previous();
            Expression right = multiplication();
            expression = new Binary(expression, operator, right);
        }
        return expression;
    }

    private Expression multiplication() {
        Expression expression = unary();
        while (match(TokenType.SLASH, TokenType.STAR)) {
            Token operator = previous();
            Expression right = unary();
            expression = new Binary(expression, operator, right);
        }
        return expression;
    }

    private Expression unary() {
        if (match(TokenType.MINUS)) {
            Token operator = previous();
            Expression right = unary();
            return new Unary(operator, right);
        }
        return primary();
    }

    private Expression primary() {
        if (match(TokenType.INTEGER))
            return new Primary(previous().value);
        if (match(TokenType.LEFT_PAREN)) {
            Expression expression = parse();
            consume(TokenType.RIGHT_PAREN);
            return new Parenthesized(expression);
        } else return null;
    }

    private boolean match(TokenType... types) {
        for (TokenType type : types)
            if (check(type)) return advance();
        return false;
    }

    private void consume(TokenType type) {
        if (check(type)) advance();
    }

    private boolean advance() {
        if (!isAtEnd()) current++;
        return true;
    }

    private Token previous() {
        return tokens.get(current - 1);
    }

    private boolean check(TokenType tokenType) {
        return !isAtEnd() && peek().type == tokenType;
    }

    private boolean isAtEnd() {
        return peek().type == TokenType.EOF;
    }

    private Token peek() {
        return tokens.get(current);
    }
}