package com.company.Interpreter;

import com.company.ErrorHandler.ErrorHandler;
import com.company.Parser.Grammar.Statement.FunctionStatement;

import java.util.HashMap;

public class Interpreter {

    public static Object run(HashMap<String, FunctionStatement> functions  )
    {
        FunctionStorage.setFunctions(functions);
        if(!functions.containsKey("main")) ErrorHandler.stop("Błąd interpretera: brak zdefiniowanej funkcji \"main\" ");
        return functions.get("main").execute(null);
    }

}
