package com.company.Parser.Grammar.Expression;

import com.company.Interpreter.Scope;
import com.company.lexer.Token;

public class Adjugate extends Unary{
    public Adjugate(Expression right, Token operator) {
        super(right, operator);
    }

    @Override
    public Object evaluate(Scope scope) {
        return null;
    }
}