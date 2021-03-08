package com.company.Parser.Grammar.Expression;

import com.company.ErrorHandler.ErrorHandler;
import com.company.Interpreter.Scope;
import com.company.lexer.Token;

public class Identifier extends  Expression{

    String name;
    Token token;

    public Identifier(Token token) {
        this.name = token.getTextValue();
        this.token = token;
    }

    @Override
    public Object evaluate(Scope scope) {

        Object value = scope.getVar(name).value;
        if(value == null)
        {
            ErrorHandler.stop("Zmienna: " + name+ " nie ma zadeklarowaniej wartości" + (token.getPosition()!=null?  token.getPosition().toString() : ""));
        }

        return value;
    }
}
