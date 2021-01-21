package com.company.Parser.Grammar.Statement;

import com.company.ErrorHandler.ErrorHandler;
import com.company.Interpreter.Scope;
import com.company.Parser.Grammar.Expression.Expression;
import com.company.Parser.Grammar.Expression.Literal;
import com.company.Parser.MatrixVar;
import com.company.lexer.Token;
import com.company.lexer.TokenType;

import java.math.BigDecimal;

public class AssignStatement extends Statement {

    public Expression expression;
    public String var;

    public AssignStatement(Expression expression, String var) {
        this.expression = expression;
        this.var = var;
    }

    @Override
    public Object execute(Scope scope) {
        var variable = scope.getVar(this.var);
        var variableType = variable.type;
        var val = this.expression.evaluate(scope);

        if (val instanceof String && variableType != TokenType.STRING_T
                || val instanceof Integer && variableType != TokenType.INT_T
                || val instanceof MatrixVar && variableType != TokenType.MATRIX_T
                || val instanceof BigDecimal && variableType != TokenType.FLOAT_T
        ) {
            ErrorHandler.stop("Błąd typ wartości inna niż deklarowany");
        }

        if (variableType == TokenType.MATRIX_T) {

            if (((MatrixVar) variable.value).row != ((MatrixVar) val).row || ((MatrixVar) variable.value).col != ((MatrixVar) val).col) {
                ErrorHandler.stop("Próba przypisania macirzy: "+((MatrixVar) val).col+"x"+((MatrixVar) val).row +" do macierzy o rozmiarze: "+((MatrixVar) variable.value).col+"x"+((MatrixVar) variable.value).row);
            }


        }


        scope.setVarValue(this.var, val);

        return null;
    }

}