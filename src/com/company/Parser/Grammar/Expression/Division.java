package com.company.Parser.Grammar.Expression;

import com.company.Interpreter.Scope;
import com.company.lexer.Token;

public class Division extends MultiplicativeExpr{
    public Division(Expression left, Expression right, Token operator) {
        super(left, right, operator);
    }

    @Override
    public Object evaluate(Scope scope) {
        return null;
    }
}
