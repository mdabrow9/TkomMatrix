package com.company.Parser.Grammar.Expression;

import com.company.Interpreter.Scope;
import com.company.lexer.Token;

public class MatrixDotMultiplication extends MultiplicativeExpr{
    public MatrixDotMultiplication(Expression left, Expression right, Token operator) {
        super(left, right, operator);
    }

    @Override
    public Object evaluate(Scope scope) {
        return null;
    }
}
