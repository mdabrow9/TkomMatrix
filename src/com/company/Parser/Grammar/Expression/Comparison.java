package com.company.Parser.Grammar.Expression;

import com.company.ErrorHandler.ErrorHandler;
import com.company.Interpreter.Scope;
import com.company.Parser.MatrixVar;
import com.company.lexer.Token;
import com.company.lexer.TokenType;

import java.math.BigDecimal;

import static com.company.lexer.TokenType.*;

public class Comparison extends Binary {
    public Comparison(Expression left, Expression right, Token operator) {

        super(left, right, operator);
    }

    @Override
    public Object evaluate(Scope scope)
    {
        Object left = this.left.evaluate(scope);
        Object right = this.right.evaluate(scope);
        TokenType op = this.operator.getType();


        if(left instanceof Number && right instanceof Number)
        {
            if(left instanceof Integer && right instanceof Integer)
            {
                boolean equals = (int)left == (int)right;
                boolean leftIsLessThanRight = (int)left <= (int)right;
                return  compare(equals,leftIsLessThanRight,op);

            }
            if(left instanceof BigDecimal && right instanceof BigDecimal )
            {
                boolean equals = ((BigDecimal)left).compareTo((BigDecimal)right) ==0;
                boolean leftIsLessThanRight = ((BigDecimal)left).compareTo((BigDecimal)right) ==-1;
                return  compare(equals,leftIsLessThanRight,op);
            }
            if(left instanceof BigDecimal )
            {
                boolean equals = ((BigDecimal)left).compareTo(new BigDecimal ((int)right ))== 0;
                boolean leftIsLessThanRight = ((BigDecimal)left).compareTo(new BigDecimal ((int)right ))== -1;
                return  compare(equals,leftIsLessThanRight,op);
            }
            if(right instanceof  BigDecimal)
            {
                boolean equals = ((BigDecimal)right).compareTo(new BigDecimal ((int)left ))== 0;
                boolean leftIsLessThanRight = ((BigDecimal)right).compareTo(new BigDecimal ((int)left ))== 1;
                return  compare(equals,leftIsLessThanRight,op);
            }
            ErrorHandler.stop("nie obsługiwana operacja porównainia!!!" + (operator.getPosition()!=null?  operator.getPosition().toString() : ""));
        }

        if(left instanceof MatrixVar && right instanceof MatrixVar)
        {
            ErrorHandler.stop("nie obsługiwana operacja porównania!!!" + (operator.getPosition()!=null?  operator.getPosition().toString() : ""));
        }

        if(left instanceof String && right instanceof String)
        {

            ErrorHandler.stop("nie obsługiwana operacja porównania!!!" + (operator.getPosition()!=null?  operator.getPosition().toString() : ""));

        }

        ErrorHandler.stop("Obie strony porówniania muszą być tego samego typu!!!" + (operator.getPosition()!=null?  operator.getPosition().toString() : ""));
        return null;
    }
    private boolean compare(boolean equals, boolean leftIsLessThanRight ,TokenType op)
    {
        if(op == LESS && leftIsLessThanRight && !equals) return true;
        if(op == GREATER && !leftIsLessThanRight && !equals) return true;
        if(op == LESS_EQUAL && (leftIsLessThanRight || equals)) return true;
        return op == GREATER_EQUAL && (!leftIsLessThanRight || equals);
    }
}
