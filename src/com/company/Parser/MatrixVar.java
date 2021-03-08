package com.company.Parser;

import com.company.ErrorHandler.ErrorHandler;
import com.company.Interpreter.Scope;
import com.company.Parser.Grammar.Expression.*;
import com.company.lexer.Token;
import com.company.lexer.TokenType;

import java.math.BigDecimal;
import java.math.MathContext;
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

    public Object getElement(int col, int row,Scope scope)
    {
        if(col >=this.col || row >= this.row)
        {
           return null;
        }
        return elements[row][col].evaluate(scope);
    }

    public Boolean equals(MatrixVar b,Scope scope)
    {
        if(this.col != b.col )
        {
            return false;
        }
        for (int i = 0; i < row; i++)
        {
            for (int j = 0; j < col; j++)
            {
                Boolean pom = (Boolean)(new Equality(this.elements[i][j],b.elements[i][j],new Token(TokenType.EQUALS,null,"==")).evaluate(scope));
                if(!pom) return false;
            }
        }
        return true;

    }
    public Boolean notEquals(MatrixVar b,Scope scope)
    {
        return !equals(b,scope);
    }
    public MatrixVar multiply(Number a,Scope scope)
    {

        for (int i = 0; i < row; i++)
        {
            for (int j = 0; j < col; j++)
            {


                var number = new Literal(a,new Token(TokenType.MATRIX_T,null,""));
                elements[i][j] = new Multiplication(this.elements[i][j],number,null);
                elements[i][j] =new Literal(elements[i][j].evaluate(scope),new Token(TokenType.MATRIX_T,null,"")) ;
            }
        }
        return this;
    }
    private MatrixVar minior(int row, int col)
    {
        MatrixVar result = new MatrixVar(this.col-1,this.row-1,new Expression[this.row-1][this.col-1]);

        for (int i = 0 ,i2 =0; i < this.row; i++)
        {
            if(i == row) continue;
            for (int j = 0,j2=0; j < this.col; j++)
            {
                if(j == col) continue;
                result.elements[i2][j2++] = this.elements[i][j];
            }
            i2++;

        }
        return result;
    }
    public Number det(MatrixVar matrixVar,Scope scope)
    {
        if(matrixVar.col != matrixVar.row)
        {
            return null;
        }
        if(matrixVar.col == 1)
        {
            return (Number) matrixVar.elements[0][0].evaluate(scope);
        }
        int a =1;
        var sum = new Literal(0,new Token(TokenType.INT,null,0));
        for(int i =0;i<matrixVar.col ;i++)
        {
            var mulit = new Multiplication(matrixVar.elements[0][i] ,new Literal( det(matrixVar.minior(0,i), scope),new Token(TokenType.INT,null,0)),null).evaluate(scope);
            if(a==1)
            {
                var s = new Sum(sum,new Literal(mulit, new Token(TokenType.INT,null,0)),null).evaluate(scope);
                sum = new  Literal(s, new Token(TokenType.INT,null,0));
            }
            else
            {
                var s = new Subtraction(sum,new Literal(mulit, new Token(TokenType.INT,null,0)),null).evaluate(scope);
                sum = new  Literal(s, new Token(TokenType.INT,null,0));
            }
            a = -a;
        }

        return (Number) sum.evaluate(scope);
    }
    public MatrixVar adjugate(Scope scope)
    {
        if(this.col != this.row)
        {
            return null;
        }
        if(this.col == 1)
        {
           var a= new MatrixVar(1,1,new Expression[1][1]);

            return a.setAllElements(new Literal(0,new Token(TokenType.INT,null,0)));
        }
        MatrixVar result = new MatrixVar(this.col,this.row,new Expression[this.row][this.col]);

        for (int i = 0; i < row; i++)
        {
            for (int j = 0; j < col; j++)
            {

                int a = (int) Math.pow(-1,i+j+2);
                var mulit = new Multiplication(new Literal( a, new Token(TokenType.INT,null,0)),
                        new Literal( det(this.minior(i,j), scope), new Token(TokenType.INT,null,0)),null).evaluate(scope);

                result.elements[i][j] =new Literal(mulit,new Token(TokenType.INT,null,"")) ;
            }
        }
        return result;

    }
    public MatrixVar inverse(Scope scope)
    {
        if(this.col != this.row)
        {
            return null;
        }
        if(this.col == 1)
        {
           return null;
        }
        var det = this.det(this,scope);
        if(det instanceof BigDecimal)
        {
            return this.adjugate(scope).transposition(scope).multiply(new BigDecimal(1).divide((BigDecimal)det , MathContext.DECIMAL32),scope);
        }
        if(det instanceof Integer)
        {
            return this.adjugate(scope).transposition(scope).multiply(new BigDecimal(1).divide(new BigDecimal((int)det) , MathContext.DECIMAL32),scope);
        }
       return null;




    }

    public MatrixVar transposition(Scope scope)
    {

        MatrixVar result = new MatrixVar(this.row,this.col,new Expression[this.col][this.row]);

        for (int i = 0; i < row; i++)
        {
            for (int j = 0; j < col; j++)
            {

                result.elements[j][i] = this.elements[i][j];
            }
        }
        return result;

    }


    public MatrixVar multiply(MatrixVar b,Scope scope)
    {
        if(this.col != b.row )
        {
            ErrorHandler.stop("zły rozmiar macierzy, liczba kolumn pierwszej musi być równa liczbie wierszy drugiej ");
        }
        var newMatrix = new MatrixVar(b.col , this.row, new Expression[this.row][b.col]);

        int m = this.row;
        int n = this.col;
        int p = b.col;

        for (int i = 0; i < newMatrix.row; i++) //= m
        {
            for (int j = 0; j < newMatrix.col; j++) //=p
            {
                var s = new Literal(0,new Token(TokenType.INT,null,0));
                for( int k = 0; k < n; k++ )
                {
                    var leftOfMulti =this.elements[i][k];
                    var rightOfMulti =b.elements[k][j];
                    var rightOfSum =  new Multiplication(leftOfMulti,rightOfMulti,null).evaluate(scope);
                    var sum = new Sum(s,new Literal(rightOfSum, new Token(TokenType.INT,null,0)),null).evaluate(scope);
                    s = new Literal(sum, new Token(TokenType.INT,null,0));
                }

                newMatrix.elements[i][j] = s ;
            }
        }
        return newMatrix;
    }
    public MatrixVar dotMultiplication(MatrixVar b,Scope scope)
    {
        if(this.col != b.col || this.row != b.row)
        {
            ErrorHandler.stop("Macierze muszą być tych samych rozmiarów");
        }
        for (int i = 0; i < row; i++)
        {
            for (int j = 0; j < col; j++)
            {


                elements[i][j] = new Multiplication(this.elements[i][j],b.elements[i][j],null);
                elements[i][j] =new Literal(elements[i][j].evaluate(scope),new Token(TokenType.MATRIX_T,null,"")) ;
            }
        }
        return this;

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

    public MatrixVar subtract(MatrixVar b,Scope scope)
    {
        if(this.col != b.col || this.row != b.row)
        {
            ErrorHandler.stop("Macierze muszą być tych samych rozmiarów");
        }
        for (int i = 0; i < row; i++)
        {
            for (int j = 0; j < col; j++)
            {


               elements[i][j] = new Subtraction(this.elements[i][j],b.elements[i][j],null);
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

                if (!(elements[i][j] instanceof Literal))elements[i][j] =  new Literal(elements[i][j].evaluate(scope),new Token(TokenType.INT,null,0)) ;
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
