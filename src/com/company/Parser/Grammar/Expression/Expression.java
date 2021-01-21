package com.company.Parser.Grammar.Expression;

import com.company.Interpreter.Scope;

public abstract class Expression {

    public abstract Object evaluate(Scope scope);
    
}
