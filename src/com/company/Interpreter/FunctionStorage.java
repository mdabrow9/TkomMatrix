package com.company.Interpreter;

import com.company.Parser.Grammar.Statement.FunctionStatement;

import java.util.HashMap;

public class FunctionStorage
{
    private  static HashMap<String, FunctionStatement> functions;

    public static void setFunctions(HashMap<String, FunctionStatement> functions) {
        FunctionStorage.functions = functions;
    }

    public static HashMap<String, FunctionStatement> getFunctions() {
        return functions;
    }
}
