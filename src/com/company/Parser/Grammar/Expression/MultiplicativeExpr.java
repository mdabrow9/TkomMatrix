package com.company.Parser.Grammar.Expression;

import com.company.lexer.Token;

public abstract class MultiplicativeExpr extends Binary {
    public MultiplicativeExpr(Expression left, Expression right, Token operator) {
        super(left, right, operator);
    }
}
