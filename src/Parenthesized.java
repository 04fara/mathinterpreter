class Parenthesized extends Expression {
    final Expression expression;

    Parenthesized(Expression expression) {
        this.expression = expression;
    }

    @Override
    <R> R accept(Visitor<R> visitor) {
        return visitor.visitParenthesizedExpression(this);
    }
}
