package com.company.Parser.Grammar.Expression;

import com.company.ErrorHandler.ErrorHandler;
import com.company.Interpreter.Scope;
import com.company.Parser.MatrixVar;
import com.company.lexer.Token;
import com.company.lexer.TokenType;

import java.math.BigDecimal;

public class Multiplication extends MultiplicativeExpr {
    public Multiplication(Expression left, Expression right, Token operator) {
        super(left, right, operator);
    }

    @Override
    public Object evaluate(Scope scope) {

        Object left = this.left.evaluate(scope);
        Object right = this.right.evaluate(scope);
        if(left instanceof Number && right instanceof Number)
        {
            if(left instanceof Integer && right instanceof Integer)
            {
                //var ret =new Literal((int)left * (int)right , new Token(TokenType.INT,null,0));
                //return ret;
                return (int)left * (int)right;

            }
            if(left instanceof BigDecimal && right instanceof BigDecimal )
            {
                return ((BigDecimal)left).multiply((BigDecimal)right);
            }
            if(left instanceof BigDecimal )
            {
                return  ((BigDecimal)left).multiply(new BigDecimal ((int)right ));
            }
            if(right instanceof  BigDecimal)
            {
                return  new BigDecimal((int)left).multiply((BigDecimal)right);
            }
            ErrorHandler.stop("nie obsługiwana operacja: multi!!!");
        }
        if(left instanceof MatrixVar || right instanceof MatrixVar) //dodawanie macierzy
        {
            if(left instanceof MatrixVar && right instanceof MatrixVar)
            {
                return ((MatrixVar) left).multiply(((MatrixVar) right), scope);
            }
            if(left instanceof MatrixVar && right instanceof Number)
            {
                return ((MatrixVar) left).multiply((Number) right, scope);
            }
            if(right instanceof MatrixVar && left instanceof Number)
            {
                return ((MatrixVar) right).multiply((Number) left, scope);
            }
            ErrorHandler.stop("Obie strony równiania muszą być macierzą lub  jedna ze stron musi być liczbą" + (operator!=null?  operator.getPosition().toString() : ""));
        }
        if(left instanceof String || right instanceof String)
        {
            ErrorHandler.stop("Nie można mnożyć typu string " + (operator.getPosition()!=null?  operator.getPosition().toString() : ""));
        }

        ErrorHandler.stop("nie obsługiwana operacja:  Multiplication!!!" + (operator.getPosition()!=null?  operator.getPosition().toString() : "") );
        return null;
    }
}
