package com.company.Parser.Grammar.Expression;

import com.company.ErrorHandler.ErrorHandler;
import com.company.Interpreter.Scope;
import com.company.Parser.MatrixVar;
import com.company.lexer.Token;

public class MatrixElement extends Unary{

    public Expression col;
    public Expression row;

    public MatrixElement(Expression right, Token operator, Expression col, Expression row) {
        super(right, operator);
        this.col = col;
        this.row = row;
    }

    @Override
    public Object evaluate(Scope scope) {

        Object col = this.col.evaluate(scope);
        Object row = this.row.evaluate(scope);
        Object right = this.right.evaluate(scope);
        if( right instanceof MatrixVar && col instanceof Integer && row instanceof Integer)
        {
            if((int)col >= ((MatrixVar) right).col || (int)row >=  ((MatrixVar) right).row)
            {
                ErrorHandler.stop("Wartość spoza zakresu macierzy "+(operator.getPosition()!=null?  operator.getPosition().toString() : "") );
            }

            return  ((MatrixVar) right).getElement( (int)col,(int)row,scope);
        }
        ErrorHandler.stop("Operacja dostępna tylko na macierzach" + (operator.getPosition()!=null?  operator.getPosition().toString() : ""));
        return null;
    }
}