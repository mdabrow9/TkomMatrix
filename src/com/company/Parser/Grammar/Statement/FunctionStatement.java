package com.company.Parser.Grammar.Statement;

import com.company.ErrorHandler.ErrorHandler;
import com.company.Interpreter.Scope;
import com.company.Parser.Grammar.Expression.Expression;
import com.company.Parser.Grammar.Expression.Literal;
import com.company.Parser.MatrixVar;
import com.company.lexer.Token;
import com.company.lexer.TokenType;

import java.math.BigDecimal;
import java.util.List;

public  class FunctionStatement extends Statement
{
    public Token returnType;
    public Token identifier;
    public List<DeclarationStatement> parameters;
    public List<Statement> body;

    public FunctionStatement(Token returnType, Token identifier, List<DeclarationStatement> parameters, List<Statement> body) {
        this.returnType = returnType;
        this.identifier = identifier;
        this.parameters = parameters;
        this.body = body;
    }

    @Override
    public Object execute(Scope scope) {
         return execute(scope,null);
    }
    public Object execute(Scope callingScope, List<Expression> arguments) {
        //dodawanie parametrów
        int i=0;
        Scope scope1 = new Scope();
        if(identifier.getTextValue() != "main" )
        {
            for (DeclarationStatement item: parameters )
            {


                item.execute(scope1);
                //scope1.setVarValue(item.name,arguments.get(i++).evaluate(callingScope));
                setVariablesValueInterScope(scope1,callingScope,arguments.get(i++),item.name);
            }
        }

        //wykonywanie statmentów
        for (Statement statement : body)
        {
            if(statement instanceof ReturnStatement)
            {

                Object val = statement.execute(scope1);
                if (val instanceof String && returnType.getType() != TokenType.STRING_T
                        || val instanceof Integer && returnType.getType() != TokenType.INT_T
                        || val instanceof MatrixVar && returnType.getType() != TokenType.MATRIX_T
                        || val instanceof BigDecimal && returnType.getType() != TokenType.FLOAT_T
                )
                {
                    ErrorHandler.stop("Podczas wywołania funkcji "+identifier.getTextValue() +": typ zwracany inny niż deklarowany ");
                }
                return val;
            }
            Object returned = statement.execute(scope1);

            if(returned instanceof ReturnStatement)
            {
                 Object val= ((ReturnStatement) returned).execute(scope1);
                if (val instanceof String && returnType.getType() != TokenType.STRING_T
                        || val instanceof Integer && returnType.getType() != TokenType.INT_T
                        || val instanceof MatrixVar && returnType.getType() != TokenType.MATRIX_T
                        || val instanceof BigDecimal && returnType.getType() != TokenType.FLOAT_T
                )
                {
                    ErrorHandler.stop("Podczas wywołania funkcji "+identifier.getTextValue() +": typ zwracany inny niż deklarowany ");
                }
                return val;
            }
        }
        ErrorHandler.stop("Brak wartości zwracanej w funkcji: "+ identifier.getTextValue() );


        return null;
    }

    private void setVariablesValueInterScope(Scope innerScope, Scope parentScope ,Expression value,String name)
    {
        var variable = innerScope.getVar(name);
        var variableType = variable.type;
        var val = value.evaluate(parentScope);

        if (val instanceof String && variableType != TokenType.STRING_T
                || val instanceof Integer && variableType != TokenType.INT_T
                || val instanceof MatrixVar && variableType != TokenType.MATRIX_T
                || val instanceof BigDecimal && variableType != TokenType.FLOAT_T
        ) {
            ErrorHandler.stop("Podczas wywołania funkcji "+identifier.getTextValue() +": Błąd typ wartości inny niż deklarowany typ parametru: " + name);
        }

        if (variableType == TokenType.MATRIX_T)
        {

            if(variable.value == null)
            {
                innerScope.setVarValue(name, val);


            }
            else
            {
                if (((MatrixVar) variable.value).row != ((MatrixVar) val).row || ((MatrixVar) variable.value).col != ((MatrixVar) val).col)
                {
                    ErrorHandler.stop("Podczas wywołania funkcji "+identifier.getTextValue() +": Próba przypisania macirzy: "+((MatrixVar) val).row+"x"+((MatrixVar) val).col +" do macierzy o rozmiarze: "+((MatrixVar) variable.value).row+"x"+((MatrixVar) variable.value).col);
                }
            }
        }
        innerScope.setVarValue(name, val);
    }



}
