package com.company.Parser.Grammar.Expression;

import com.company.lexer.Token;

public abstract class AdditiveExpr extends Binary {
    public AdditiveExpr(Expression left, Expression right, Token operator) {
        super(left, right, operator);
    }
}
