package com.company.Parser.Grammar;

import com.company.Interpreter.Scope;
import com.company.Parser.Grammar.Expression.Expression;
import com.company.Parser.Grammar.Statement.DeclarationStatement;
import com.company.lexer.TokenType;

public class Variable extends Expression {
    public String name;
    public TokenType type;
    public Object value;

    public Variable(String name, TokenType type) {
        this.name = name;
        this.type = type;

    }
    public Variable(DeclarationStatement declarationStatement)
    {
        this.name = declarationStatement.name;
        this.type = declarationStatement.variableType;
        this.value = null;
    }


    @Override
    public Object evaluate(Scope scope) {
        return null;
    }
}
