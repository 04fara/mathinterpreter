class Binary extends Expression {
    final Expression left, right;
    final Token operator;

    Binary(Expression left, Token operator, Expression right) {
        this.left = left;
        this.operator = operator;
        this.right = right;
        this.right.isRight = true;
    }

    @Override
    <R> R accept(Visitor<R> visitor) {
        return visitor.visitBinaryExpression(this);
    }
}
