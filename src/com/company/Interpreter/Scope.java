package com.company.Interpreter;

import com.company.ErrorHandler.ErrorHandler;
import com.company.Parser.Grammar.Variable;

import java.util.HashMap;

public class Scope {
    public HashMap<String, Variable> variables = new HashMap<>();
    //private HashMap<String, FunctionStatement> functions;

    public Scope() {

    }
    /*
    public Scope(HashMap<String, FunctionStatement> functions) {
        this.functions = functions;
    }

     */

    public boolean contains (String name)
    {
       return variables.containsKey(name);
    }

    public void putVar(Variable var)
    {
        if(contains(var.name))
        {
            ErrorHandler.stop("Zduplikowana nazwa zmiennej: " + var.name );
        }
        else
        {
            variables.put(var.name,var);
        }
    }
    public Variable getVar(String name)
    {
        if(!contains(name)) ErrorHandler.stop("Brak definicji zmiennej: " + name);
        var variable = variables.get(name);
        return variable;
    }
    public void setVarValue(String name,Object val)
    {
        variables.get(name).value = val;
    }
}
