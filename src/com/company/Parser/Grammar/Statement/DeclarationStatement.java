package com.company.Parser.Grammar.Statement;

import com.company.ErrorHandler.ErrorHandler;
import com.company.Interpreter.Scope;
import com.company.Parser.Grammar.Expression.Expression;
import com.company.Parser.Grammar.Expression.Literal;
import com.company.Parser.Grammar.Variable;
import com.company.Parser.MatrixVar;
import com.company.lexer.Token;
import com.company.lexer.TokenType;

import java.math.BigDecimal;


public class DeclarationStatement extends Statement {
    public TokenType variableType;
    public Expression value;
    public String name;
    public Expression row=null;
    public Expression col=null;
    public Boolean isParameter ;

    public DeclarationStatement(TokenType variableType, Expression value, String name, Expression row, Expression col , Boolean isParameter) {
        this.variableType = variableType;
        this.value = value;
        this.name = name;
        this.row = row;
        this.col = col;
        this.isParameter = isParameter;
    }

    public DeclarationStatement(TokenType variableType, Expression value, String name, Boolean isParameter) {
        this.variableType = variableType;
        this.value = value;
        this.name = name;
        this.isParameter = isParameter;

    }

    @Override
    public Object execute(Scope scope) {

        scope.putVar(new Variable(this));

        if( variableType == TokenType.MATRIX_T)
        {
            if (this.col != null && this.row != null)
            {
                var c = this.col.evaluate(scope);
                var r = this.row.evaluate(scope);

                if (c instanceof Integer && r instanceof Integer) {
                } else ErrorHandler.stop("deklaracja ilości wierszy i kolumn musi być liczbą całkowitą");

                if (value != null)
                {
                    var val = value.evaluate(scope);

                    if (val instanceof MatrixVar) {
                        if (((MatrixVar) val).col != (int) this.col.evaluate(scope) || ((MatrixVar) val).row != (int) this.row.evaluate(scope)) {
                            ErrorHandler.stop("Zadeklarowany rozmiar nie jest zgodny z faktycznym rozmiarem macierzy");
                        }
                        scope.setVarValue(name,val);
                    }
                    else
                    {
                        var matr = new MatrixVar((int) c, (int) r, new Expression[(int) r][(int) c]);
                        matr.setAllElements(new Literal(val, new Token(TokenType.INT, null, 0)));
                        scope.setVarValue(name, matr);
                    }

                }
                else
                {
                    var matr = new MatrixVar((int) c, (int) r, new Expression[(int) r][(int) c]);
                    matr.setAllElements(new Literal(0, new Token(TokenType.INT, null, 0)));
                    scope.setVarValue(name, matr);

                }

            }
            else
            {
                if(!isParameter)
                {
                    if(value == null)
                    {
                        ErrorHandler.stop("brak deklaracji rozmiarów macierzy "+"Błąd napotkany podczas deklaracji zmiennej: "+this.name );
                    }
                    var val = value.evaluate(scope);
                    if(val instanceof MatrixVar)
                    {
                        scope.setVarValue(name,val);
                    }
                    else ErrorHandler.stop("typ wartości inny niż deklarowany "+"Błąd napotkany podczas deklaracji zmiennej: "+this.name);
                }

            }
            return null;
        }

        if(value != null)
        {
            var val = value.evaluate(scope);
            if(val instanceof Literal)
            {
                //var val = value.evaluate(scope);
                var val2 = ((Literal) val).value;
                if(val2 instanceof String && variableType != TokenType.STRING_T
                        || val2 instanceof Integer && variableType != TokenType.INT_T
                        || val2 instanceof MatrixVar && variableType != TokenType.MATRIX_T
                        || val2 instanceof BigDecimal && variableType != TokenType.FLOAT_T
                )
                {
                    ErrorHandler.stop("Błąd typ wartości inna niż deklarowany "+"Błąd napotkany podczas deklaracji zmiennej: "+this.name);
                }

                scope.setVarValue(name,val2);
            }
            if(val instanceof String && variableType != TokenType.STRING_T
                    || val instanceof Integer && variableType != TokenType.INT_T
                    || val instanceof MatrixVar && variableType != TokenType.MATRIX_T
                    || val instanceof BigDecimal && variableType != TokenType.FLOAT_T
            )
            {
                ErrorHandler.stop("Błąd typ wartości inna niż deklarowany " +"Błąd napotkany podczas deklaracji zmiennej: "+this.name);
            }

            scope.setVarValue(name,val);
        }


        return null;
    }
}
