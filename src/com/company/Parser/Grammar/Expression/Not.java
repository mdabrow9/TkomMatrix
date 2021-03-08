package com.company.Parser.Grammar.Expression;

import com.company.ErrorHandler.ErrorHandler;
import com.company.Interpreter.Scope;
import com.company.lexer.Token;

public class Not extends Unary{
    public Not(Expression right, Token operator) {
        super(right, operator);
    }

    @Override
    public Object evaluate(Scope scope) {


        Object right = this.right.evaluate(scope);


        if( right instanceof Boolean)
        {
            return  !((Boolean) right) ;
        }
        ErrorHandler.stop("Operacja dostępna tylko na operacji boolowskich, dla inncyh typów użyj:'-' " + (operator.getPosition()!=null?  operator.getPosition().toString() : "") );
        return null;
    }
}
