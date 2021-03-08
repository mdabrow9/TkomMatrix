package com.company.Parser.Grammar.Expression;

import com.company.ErrorHandler.ErrorHandler;
import com.company.Interpreter.Scope;
import com.company.Parser.MatrixVar;
import com.company.lexer.Token;
import com.company.lexer.TokenType;

import java.math.BigDecimal;

public class Subtraction extends AdditiveExpr{
    public Subtraction(Expression left, Expression right, Token operator) {
        super(left, right, operator);
    }

    @Override
    public Object evaluate(Scope scope)
    {

        Object left = this.left.evaluate(scope);
        Object right = this.right.evaluate(scope);
        if(left instanceof Number && right instanceof Number)
        {
            if(left instanceof Integer && right instanceof Integer)
            {
                //var ret =new Literal((int)left - (int)right , new Token(TokenType.INT,null,0));
                var ret =(int)left - (int)right;
                return ret;

            }
            if(left instanceof BigDecimal && right instanceof BigDecimal )
            {
                return ((BigDecimal)left).subtract((BigDecimal)right);
            }
            if(left instanceof BigDecimal )
            {
                return  ((BigDecimal)left).subtract(new BigDecimal ((int)right ));
            }
            if(right instanceof  BigDecimal)
            {
                return  new BigDecimal((int)left).subtract((BigDecimal) right );
            }
            ErrorHandler.stop("nie obsługiwana operacja: Subtraction!!!");
        }
        if(left instanceof MatrixVar || right instanceof MatrixVar) //dodawanie macierzy
        {
            if(left instanceof MatrixVar && right instanceof MatrixVar)
            {
                return ((MatrixVar) left).subtract(((MatrixVar) right), scope);
            }
            ErrorHandler.stop("Obie strony równiania muszą być macierzą " + (operator.getPosition()!=null?  operator.getPosition().toString() : ""));
        }
        if(left instanceof String || right instanceof String)
        {
            ErrorHandler.stop("Nie można odejmować od typu string "+ (operator.getPosition()!=null?  operator.getPosition().toString() : ""));
        }

        ErrorHandler.stop("nie obsługiwana operacja Subtraction!!!");
        return null;
    }

}
