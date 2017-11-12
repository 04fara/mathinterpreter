import java.util.ArrayList;

class Scanner {
    private final String source;
    private final ArrayList<Token> tokens = new ArrayList<>();
    private int start = 0, current = 0;

    Scanner(String source) {
        this.source = source;
    }

    ArrayList<Token> scanTokens() {
        while (!isAtEnd()) {
            start = current;
            scanToken();
        }
        tokens.add(new Token(Parser.TokenType.EOF, "EOF", null));
        return tokens;
    }

    private void scanToken() {
        char c = advance();
        switch (c) {
            case '(':
                addToken(Parser.TokenType.LEFT_PAREN);
                break;
            case ')':
                addToken(Parser.TokenType.RIGHT_PAREN);
                break;
            case '+':
                addToken(Parser.TokenType.PLUS);
                break;
            case '-':
                addToken(Parser.TokenType.MINUS);
                break;
            case '*':
                addToken(Parser.TokenType.STAR);
                break;
            case '/':
                addToken(Parser.TokenType.SLASH);
                break;
            case '=':
                if (match('=')) {
                    addToken(Parser.TokenType.EQUAL_EQUAL);
                    break;
                }
            case '<':
                addToken(match('=') ? Parser.TokenType.LESS_EQUAL : Parser.TokenType.LESS);
                break;
            case '>':
                addToken(match('=') ? Parser.TokenType.GREATER_EQUAL : Parser.TokenType.GREATER);
                break;
            case 'a':
                if (match('n') && match('d')) {
                    addToken(Parser.TokenType.AND);
                    break;
                }
                throw new RuntimeError("Unexpected character.");
            case '&':
                addToken(Parser.TokenType.AND);
                break;
            case 'o':
                if (match('r')) {
                    addToken(Parser.TokenType.OR);
                    break;
                }
                throw new RuntimeError("Unexpected character.");
            case '|':
                addToken(Parser.TokenType.OR);
                break;
            case 'x':
                if (match('o') && match('r')) {
                    addToken(Parser.TokenType.XOR);
                    break;
                }
                throw new RuntimeError("Unexpected character.");
            case '^':
                addToken(Parser.TokenType.XOR);
                break;
            case ' ':
            case '\r':
            case '\t':
            case '\n':
                break;
            default:
                if (isDigit(c)) {
                    number();
                    break;
                }
                throw new RuntimeError("Unexpected character.");
        }
    }

    private void number() {
        while (isDigit(peek())) advance();
        addToken(Parser.TokenType.INTEGER, Integer.parseInt(source.substring(start, current)));
    }

    private boolean match(char expected) {
        if (isAtEnd() || source.charAt(current) != expected) return false;
        current++;
        return true;
    }

    private char peek() {
        if (isAtEnd()) return '\0';
        return source.charAt(current);
    }

    private boolean isDigit(char c) {
        return c >= '0' && c <= '9';
    }

    private boolean isAtEnd() {
        return current >= source.length();
    }

    private char advance() {
        return source.charAt(current++);
    }

    private void addToken(Parser.TokenType type, Object literal) {
        tokens.add(new Token(type, source.substring(start, current), literal));
    }

    private void addToken(Parser.TokenType type) {
        addToken(type, null);
    }
}