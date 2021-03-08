package com.company.Parser.Grammar.Statement;

import com.company.ErrorHandler.ErrorHandler;
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

            return executeSystemFunction( scope,  arguments);
        }
        return FunctionStorage.getFunctions().get(identifier).execute(scope,arguments);
    }
    private Object executeSystemFunction(Scope scope, List<Expression> arguments)
    {

        if(identifier.equals("print"))
        {
            if(arguments.size() != 1) ErrorHandler.stop("nie poprawna ilość argumentów podczas wywołania funkcji " + identifier );
            var toPrint = arguments.get(0).evaluate(scope);
            if(toPrint==null)
            {
                System.out.print("null");
                return null;
            }
            if(toPrint instanceof MatrixVar)
            {

                System.out.println(((MatrixVar) toPrint).toString(scope) );
                return null;
            }
            System.out.println(toPrint);
            return null;
        }
        if (identifier.equals("getCol"))
        {
            if(arguments.size() != 1) ErrorHandler.stop("nie poprawna ilość argumentów podczas wywołania funkcji " + identifier );
            var arg = arguments.get(0).evaluate(scope);
            if(arg instanceof MatrixVar)
            {
                return ((MatrixVar) arg).col;
            }
            ErrorHandler.stop("podczas wywołania funkcji " + identifier+ ": operacja dostępna tylko na macierzach");
        }
        if(identifier.equals("getRow"))
        {
            if(arguments.size() != 1) ErrorHandler.stop("nie poprawna ilość argumentów podczas wywołania funkcji " + identifier );
            var arg = arguments.get(0).evaluate(scope);
            if(arg instanceof MatrixVar)
            {
                return ((MatrixVar) arg).row;
            }
            ErrorHandler.stop( "podczas wywołania funkcji " + identifier+ " operacja dostępna tylko na macierzach");
        }
        return null;
    }
}
