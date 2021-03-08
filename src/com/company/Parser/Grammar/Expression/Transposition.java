package com.company.Parser.Grammar.Expression;

import com.company.ErrorHandler.ErrorHandler;
import com.company.Interpreter.Scope;
import com.company.Parser.MatrixVar;
import com.company.lexer.Token;

public class Transposition extends Unary{


    public Transposition(Expression right, Token operator) {
        super(right, operator);
    }

    @Override
    public Object evaluate(Scope scope) {

        Object right = this.right.evaluate(scope);
        if( right instanceof MatrixVar)
        {
            return  ((MatrixVar) right).transposition( scope);
        }
        ErrorHandler.stop("Operacja dostępna tylko na macierzach " + (operator.getPosition()!=null?  operator.getPosition().toString() : ""));
        return null;
    }
}