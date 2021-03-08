package com.company.Parser.Grammar.Expression;

import com.company.ErrorHandler.ErrorHandler;
import com.company.Interpreter.Scope;
import com.company.Parser.MatrixVar;
import com.company.lexer.Token;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class Division extends MultiplicativeExpr{
    public Division(Expression left, Expression right, Token operator) {
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
                return (int)left / (int)right;
                //return new BigDecimal((int)left).divide(new BigDecimal((int)right), MathContext.DECIMAL64);

            }
            if(left instanceof BigDecimal && right instanceof BigDecimal )
            {
                return ((BigDecimal)left).divide((BigDecimal)right, MathContext.DECIMAL64);
            }
            if(left instanceof BigDecimal )
            {
                return  ((BigDecimal)left).divide(new BigDecimal ((int)right ), MathContext.DECIMAL64);
            }
            if(right instanceof  BigDecimal)
            {
                return    new BigDecimal((int)left).divide((BigDecimal)right, MathContext.DECIMAL64);

            }
            ErrorHandler.stop("nie obsługiwana operacja: Division!!!"+ (operator.getPosition()!=null?  operator.getPosition().toString() : "") );
        }
        if(left instanceof MatrixVar || right instanceof MatrixVar) //dodawanie macierzy
        {

            ErrorHandler.stop("Nie można dzielić macierzy!" + (operator.getPosition()!=null?  operator.getPosition().toString() : "") );
        }
        if(left instanceof String || right instanceof String)
        {
            ErrorHandler.stop("Nie można dzielić typu string "+ (operator.getPosition()!=null?  operator.getPosition().toString() : "") );
        }

        ErrorHandler.stop("nie obsługiwana operacja:  Division!!!" + (operator.getPosition()!=null?  operator.getPosition().toString() : "") );
        return null;
    }
}
