abstract class Expression {
    boolean isRight = false;

    protected interface Visitor<R> {
        R visitBinaryExpression(Binary expression);

        R visitParenthesizedExpression(Parenthesized expression);

        R visitPrimaryExpression(Primary expression);

        R visitLogicalExpression(Logical expression);

        R visitUnaryExpression(Unary expression);
    }

    abstract <R> R accept(Visitor<R> visitor);
}