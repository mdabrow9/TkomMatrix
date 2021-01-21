package com.company.Parser.Grammar.Statement;

import com.company.Interpreter.FunctionStorage;
import com.company.Interpreter.Scope;
import com.company.Parser.Grammar.Expression.Expression;
import com.company.Parser.Grammar.Expression.Literal;
import com.company.Parser.MatrixVar;

import java.util.List;

public class FunctionCallStatement extends Statement {

    public String identifier;
    public List<Expression> arguments;

    public FunctionCallStatement(String identifier, List<Expression> arguments) {
        this.identifier = identifier;
        this.arguments = arguments;
    }

    @Override
    public Object execute(Scope scope)
    {

        if(identifier.equals("print") || identifier.equals( "getCol") || identifier.equals( "getRow"))
        {
            executeSystemFunction( scope,  arguments);
            return null;
        }
        return FunctionStorage.getFunctions().get(identifier).execute(scope,arguments);
    }
    private void executeSystemFunction(Scope scope, List<Expression> arguments)
    {

        if(identifier.equals("print"))
        {   //TODO jeszcze trzeba dobrze to przetestować
            var toPrint = arguments.get(0).evaluate(scope);
            if(toPrint==null)
            {
                System.out.println("null");
            }
            if(toPrint instanceof MatrixVar)
            {

                System.out.println(((MatrixVar) toPrint).toString(scope) );
                return;
            }
            System.out.println((arguments.get(0).evaluate(scope)).toString());
            return;
        }
    }
}
