package com.company.Parser;

import com.company.ErrorHandler.ErrorHandler;
import com.company.Interpreter.Scope;
import com.company.Parser.Grammar.Expression.Expression;
import com.company.Parser.Grammar.Expression.Literal;
import com.company.Parser.Grammar.Expression.Subtraction;
import com.company.Parser.Grammar.Expression.Sum;
import com.company.lexer.Token;
import com.company.lexer.TokenType;

import java.util.Arrays;

public class MatrixVar {
    public int col;
    public int row;
                //row-v  v-col
    public Expression [][] elements;

    public MatrixVar(int col, int row, Expression[][] elements) {
        this.col = col;
        this.row = row;
        this.elements = elements;
    }

    public MatrixVar add(MatrixVar b,Scope scope)
    {
        if(this.col != b.col || this.row != b.row)
        {
            ErrorHandler.stop("Macierze muszą być tych samych rozmiarów");
        }
        for (int i = 0; i < row; i++)
        {
            for (int j = 0; j < col; j++)
            {


               elements[i][j] = new Sum(this.elements[i][j],b.elements[i][j],null);
                elements[i][j] =new Literal(elements[i][j].evaluate(scope),new Token(TokenType.MATRIX_T,null,"")) ;
            }
        }
        return this;

    }
    public MatrixVar setAllElements(Literal o)
    {
        for (int i = 0; i < row; i++)
        {
            for (int j = 0; j < col; j++)
            {

                elements[i][j] = (Literal) o;
            }
        }
        return this;
    }

    public Object evaluate(Scope scope)
    {
        for (int i = 0; i < row; i++)
        {
            for (int j = 0; j < col; j++)
            {

                if (!(elements[i][j] instanceof Literal))elements[i][j] = (Expression) elements[i][j].evaluate(scope);
            }
        }
        return this;
    }


    public String toString(Scope scope) {
        StringBuilder sB = new StringBuilder("{\n");
        for (int i = 0; i < row; i++)
        {
            sB.append("[");
            for (int j = 0; j < col; j++)
            {

                sB.append( elements[i][j].evaluate(scope) +"," );
            }
            sB.deleteCharAt(sB.length() - 1);
            sB.append("],\n");

        }
        sB.deleteCharAt(sB.length() - 2);
        sB.append("}");

        return sB.toString();
    }

}
