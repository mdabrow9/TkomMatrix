package com.company.Parser.Grammar.Expression;

import com.company.Interpreter.FunctionStorage;
import com.company.Interpreter.Scope;

import java.util.List;

public class FunctionCallExpression extends Expression{

    public String identifier;
    public List<Expression> arguments;

    public FunctionCallExpression(String identifier, List<Expression> arguments) {
        this.identifier = identifier;
        this.arguments = arguments;
    }



    @Override
    public Object evaluate(Scope scope) {
        return FunctionStorage.getFunctions().get(identifier).execute(scope,arguments);
    }
}
