class Unary extends Expression {
    final Token operator;
    final Expression right;

    Unary(Token operator, Expression right) {
        this.operator = operator;
        this.right = right;
    }

    <R> R accept(Visitor<R> visitor) {
        return visitor.visitUnaryExpression(this);
    }
}