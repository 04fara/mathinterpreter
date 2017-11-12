class Interpreter implements Expression.Visitor<Object> {
    @Override
    public Object visitParenthesizedExpression(Parenthesized expression) {
        return expression.expression.accept(this);
    }

    @Override
    public Object visitBinaryExpression(Binary expression) {
        Object left = expression.left.accept(this),
                right = expression.right.accept(this);
        checkOperands(left, right);
        switch (expression.operator.type) {
            case PLUS:
                return (int) left + (int) right;
            case MINUS:
                return (int) left - (int) right;
            case STAR:
                return (int) left * (int) right;
            case SLASH:
                return (int) left / (int) right;
            case EQUAL_EQUAL:
                return left.equals(right) ? 1 : 0;
            case GREATER:
                return ((int) left > (int) right) ? 1 : 0;
            case GREATER_EQUAL:
                return ((int) left >= (int) right) ? 1 : 0;
            case LESS:
                return ((int) left < (int) right) ? 1 : 0;
            case LESS_EQUAL:
                return ((int) left <= (int) right) ? 1 : 0;
        }
        return null;
    }

    @Override
    public Object visitLogicalExpression(Logical expression) {
        Object left = expression.left.accept(this),
                right = expression.right.accept(this);
        switch (expression.operator.type) {
            case AND:
                return ((int) left & (int) right);
            case OR:
                return ((int) left | (int) right);
            case XOR:
                return ((int) left ^ (int) right);
            default:
                return null;
        }
    }

    @Override
    public Object visitPrimaryExpression(Primary expression) {
        return expression.value;
    }

    @Override
    public Object visitUnaryExpression(Unary expression) {
        Object right = expression.right.accept(this);
        checkOperands(right);
        return expression.operator.type.equals(Parser.TokenType.MINUS) ? -(int) right : null;
    }

    String interpret(Expression expression) {
        Object value = expression.accept(this);
        return value.toString();
    }

    private void checkOperands(Object... operands) {
        for(Object operand : operands)
            if (!(operand instanceof Integer)) throw new RuntimeError(operand.getClass() + " Operands must be numbers.");
    }
}