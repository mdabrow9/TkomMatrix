package com.company.Parser.Grammar.Expression;

import com.company.ErrorHandler.ErrorHandler;
import com.company.Interpreter.Scope;
import com.company.Parser.MatrixVar;
import com.company.lexer.Token;
import com.company.lexer.TokenType;

import java.math.BigDecimal;

public class Inverse extends Unary{
    public Inverse(Expression right, Token operator) {
        super(right, operator);
    }

    @Override
    public Object evaluate(Scope scope) {


        Object right = this.right.evaluate(scope);
        if( right instanceof Number)
        {
            if( right instanceof Integer)
            {
                var ret =(int)right * -1;
                return ret;

            }
            if( right instanceof BigDecimal )
            {
                return ((BigDecimal)right).multiply(new BigDecimal(-1));
            }

            ErrorHandler.stop("Nie obsługiwana operacja inverse !!!"+ (operator.getPosition()!=null?  operator.getPosition().toString() : ""));
        }
        if( right instanceof MatrixVar)
        {
            if(((MatrixVar) right).col != ((MatrixVar) right).row &&((MatrixVar) right).row!=1 )
            {
                ErrorHandler.stop("Macierz odwrotną można wyznaczyć tylko dla macierzy kwadratowej "+ (operator.getPosition()!=null?  operator.getPosition().toString() : ""));
            }
            var det = ((MatrixVar) right).det( (MatrixVar) right,scope);
            if(det instanceof BigDecimal)
            {
                if(((BigDecimal)det).compareTo(new BigDecimal(0)) ==0 )
                {
                    ErrorHandler.stop("Nie można obliczyć macierzy odwrotnej z macierzy osobliwej "+ (operator.getPosition()!=null?  operator.getPosition().toString() : ""));
                }
            }
            else
            {
                if( (int) ((MatrixVar) right).det( (MatrixVar) right,scope) ==0 )
                {
                    ErrorHandler.stop("Nie można obliczyć macierzy odwrotnej z macierzy osobliwej "+ (operator.getPosition()!=null?  operator.getPosition().toString() : ""));
                }
            }

            return  ((MatrixVar) right).inverse( scope);

        }
        if( right instanceof String)
        {
            ErrorHandler.stop("nie dozwolona operacja na typie string !!!"+ (operator.getPosition()!=null?  operator.getPosition().toString() : ""));
        }

        ErrorHandler.stop("nie obsługiwana operacja Inverse !!!"+ (operator.getPosition()!=null?  operator.getPosition().toString() : ""));
        return null;
    }

}
