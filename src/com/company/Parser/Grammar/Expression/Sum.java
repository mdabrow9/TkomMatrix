package com.company.Parser.Grammar.Expression;

import com.company.ErrorHandler.ErrorHandler;
import com.company.Interpreter.Scope;
import com.company.Parser.MatrixVar;
import com.company.lexer.Token;
import com.company.lexer.TokenType;

import java.math.BigDecimal;

public class Sum extends AdditiveExpr{
    public Sum(Expression left, Expression right, Token operator) {
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
               var ret =(int)left + (int)right ;

               return ret;

           }
           if(left instanceof BigDecimal && right instanceof BigDecimal )
           {
               return ((BigDecimal)left).add((BigDecimal)right);
           }
           if(left instanceof BigDecimal )
           {
               return  ((BigDecimal)left).add(new BigDecimal ((int)right ));
           }
           if(right instanceof  BigDecimal)
           {
               return  ((BigDecimal)right).add(new BigDecimal ((int)left ));
           }
           ErrorHandler.stop("nie obsługiwana operacja sum!!!" + (operator.getPosition()!=null?  operator.getPosition().toString() : ""));
       }
        if(left instanceof MatrixVar || right instanceof MatrixVar) //dodawanie macierzy
        {
            if(left instanceof MatrixVar && right instanceof MatrixVar)
            {
                return ((MatrixVar) left).add(((MatrixVar) right), scope);
            }
            ErrorHandler.stop("Obie strony równiania muszą być macierzą" + (operator.getPosition()!=null?  operator.getPosition().toString() : ""));
        }
        if(left instanceof String || right instanceof String)
        {
            return left.toString() + right.toString();
        }

        ErrorHandler.stop("nie obsługiwana operacja suma!!!" + (operator.getPosition()!=null?  operator.getPosition().toString() : ""));
        return null;
    }


}
