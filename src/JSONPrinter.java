import java.util.Collections;

class JSONPrinter implements Expression.Visitor<String> {
    private int i = 0;

    @Override
    public String visitParenthesizedExpression(Parenthesized expression) {
        i++;
        return toJSON("()", expression.isRight, expression.expression);
    }

    @Override
    public String visitBinaryExpression(Binary expression) {
        i++;
        return toJSON(expression.operator.representation, expression.isRight, expression.left, expression.right);
    }

    @Override
    public String visitLogicalExpression(Logical expression) {
        i++;
        return toJSON(expression.operator.representation, expression.isRight, expression.left, expression.right);
    }

    @Override
    public String visitPrimaryExpression(Primary expression) {
        i++;
        if (expression.value == null) return "null";
        return expression.value.toString();
    }

    @Override
    public String visitUnaryExpression(Unary expression) {
        i++;
        return toJSON(expression.operator.representation, expression.isRight, expression.right);
    }

    String printJSON(Expression expression) {
        String res = expression.accept(this);
        return "JSON:\n" + res.substring(0, res.length() - 2);
    }

    private String toJSON(String operator, boolean isRight, Expression... expressions) {
        String indent = String.join("", Collections.nCopies(i - 1, "\t"));
        StringBuilder builder = new StringBuilder().append("{\n").append(indent)
                .append("\toperator: ").append(operator).append(",\n");
        for (Expression expression : expressions) {
            builder.append(indent).append("\t")
                    .append(expressions.length < 2 ? "content: " : (expression.isRight ? "right: " : "left: "));
            String st = expression.accept(this);
            builder.append((expressions.length < 2 && st.length() > 2) ? st.substring(0, st.length() - 2) + "\n" : st);
            if (expression instanceof Primary) builder.append(!expression.isRight ? ",\n" : "\n");
            i--;
        }
        return builder.append(indent).append(!isRight ? "},\n" : "}\n").toString();
    }
}
