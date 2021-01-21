package com.company.Parser.Grammar.Expression;

import com.company.lexer.Token;

public abstract class Binary extends Expression {
    public Expression left;
    public Expression right;
    public Token operator;

    public Binary(Expression left, Expression right, Token operator) {
        this.left = left;
        this.right = right;
        this.operator = operator;
    }
}
