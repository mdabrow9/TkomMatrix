package com.company.Parser.Grammar.Expression;

import com.company.Interpreter.Scope;
import com.company.lexer.Token;

public class Comparison extends Binary {
    public Comparison(Expression left, Expression right, Token operator) {
        super(left, right, operator);
    }

    @Override
    public Object evaluate(Scope scope) {
        return null;
    }
}
