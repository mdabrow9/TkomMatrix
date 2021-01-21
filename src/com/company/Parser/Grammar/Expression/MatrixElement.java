package com.company.Parser.Grammar.Expression;

import com.company.Interpreter.Scope;
import com.company.lexer.Token;

public class MatrixElement extends Unary{

    public int col;
    public int row;

    public MatrixElement(Expression right, Token operator, int col, int row) {
        super(right, operator);
        this.col = col;
        this.row = row;
    }

    @Override
    public Object evaluate(Scope scope) {
        return null;
    }
}