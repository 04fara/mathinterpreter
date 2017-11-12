# Math Interpreter

Math Interpreter parses and evaluates mathematical expressions with the help of Visitor pattern.

As Wikipedia tells:

> The visitor design pattern is a way of separating an algorithm from an object structure on which it operates. A practical result of this separation is the ability to add new operations to existent object structures without modifying the structures. It is one way to follow the open/closed principle.

It also serializes AST (Abstract Syntax Tree) in JSON object.

Supported operators: +, -, \*, /, unary +, unary -, <, <=, >, >=, ==, &, |, ^ and ()

Operators precedence:
![**Operators precedence**](https://i.imgur.com/SMouYEO.png)

Example:
![**Example**](https://i.imgur.com/a3cjTeC.png)

## License

Math Interpreter is licensed under the terms of the MIT license.
