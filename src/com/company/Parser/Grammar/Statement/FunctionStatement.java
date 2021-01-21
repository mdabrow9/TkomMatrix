package com.company.Parser.Grammar.Statement;

import com.company.Interpreter.Scope;
import com.company.Parser.Grammar.Expression.Expression;
import com.company.Parser.Grammar.Expression.Literal;
import com.company.lexer.Token;

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
    public Object execute(Scope scope, List<Expression> arguments) {
        //dodawanie parametrów
        int i=0;

        if(identifier.getTextValue() != "main" )
        {
            for (DeclarationStatement item: parameters )
            {
                item.execute(scope);
                scope.setVarValue(item.name,arguments.get(i++).evaluate(scope));
            }
        }

        //wykonywanie statmentów
        for (Statement statement : body)
        {
            statement.execute(scope);
        }


        return null;
    }



}
