package com.company.Interpreter;

import com.company.ErrorHandler.ErrorHandler;
import com.company.Parser.Grammar.Statement.FunctionStatement;
import com.company.Parser.Grammar.Statement.Statement;
import com.company.lexer.Token;
import com.company.lexer.TokenType;

import java.util.HashMap;

public class Interpreter {

    public static void run(HashMap<String, FunctionStatement> functions  )
    {

        FunctionStorage.setFunctions(functions);
        if(!functions.containsKey("main")) ErrorHandler.stop("Błąd interpretera: brak zdefiniowanej funkcji \"main\" ");
        functions.get("main").execute(new Scope(functions));
    }

}
