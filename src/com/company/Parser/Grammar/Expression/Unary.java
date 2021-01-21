package com.company.Parser.Grammar.Expression;

import com.company.lexer.Token;

public abstract class Unary extends Expression{
    public Expression right;
    public Token operator;

    public Unary(Expression right, Token operator) {
        this.right = right;
        this.operator = operator;
    }
}
