package com.company.Parser.Grammar.Expression;

import com.company.ErrorHandler.ErrorHandler;
import com.company.Interpreter.Scope;
import com.company.Parser.MatrixVar;
import com.company.lexer.Token;
import com.company.lexer.TokenType;

import java.math.BigDecimal;

public class Equality extends Binary {

    public Equality(Expression left, Expression right, Token operator) {
        super(left, right, operator);
    }

    @Override
    public Object evaluate(Scope scope) {

        Object left = this.left.evaluate(scope);
        Object right = this.right.evaluate(scope);
        Boolean equals = this.operator.getType() == TokenType.EQUALS;

        if(left instanceof Boolean && right instanceof Boolean)
        {
            return  equals == ((Boolean) left == (Boolean) right);
        }
        if(left instanceof Number && right instanceof Number)
        {
            if(left instanceof Integer && right instanceof Integer)
            {
                    return equals == ((int)left == (int)right) ;
            }
            if(left instanceof BigDecimal && right instanceof BigDecimal )
            {
                    return equals == ( ((BigDecimal)left).compareTo((BigDecimal)right) ==0 );
            }
            if(left instanceof BigDecimal )
            {
                return equals == (  ((BigDecimal)left).compareTo(new BigDecimal ((int)right ))== 0) ;
            }
            if(right instanceof  BigDecimal)
            {
                return equals == (  ((BigDecimal)right).compareTo(new BigDecimal ((int)left ))== 0) ;
            }
            ErrorHandler.stop("nie obsługiwana operacja porównaine!!!" + (operator.getPosition()!=null?  operator.getPosition().toString() : ""));
        }

        if(left instanceof MatrixVar && right instanceof MatrixVar)
        {
            if(equals)
            {
                return ((MatrixVar) left).equals(((MatrixVar) right), scope);
            }
            return ((MatrixVar) left).notEquals(((MatrixVar) right), scope);


        }

        if(left instanceof String && right instanceof String)
        {

            return equals == (((String) left).equals(((String) right)));

        }

        ErrorHandler.stop("Obie strony porówniania muszą być tego samego typu!!!" + (operator.getPosition()!=null?  operator.getPosition().toString() : "") );
        return null;
    }
}
