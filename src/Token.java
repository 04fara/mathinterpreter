class Token {
    final Parser.TokenType type;
    final String representation;
    final Object value;

    Token(Parser.TokenType type, String representation, Object value) {
        this.type = type;
        this.representation = representation;
        this.value = value;
    }
}