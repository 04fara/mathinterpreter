class Primary extends Expression {
    final Object value;

    Primary(Object value) {
        this.value = value;
    }

    @Override
    <R> R accept(Visitor<R> visitor) {
        return visitor.visitPrimaryExpression(this);
    }
}