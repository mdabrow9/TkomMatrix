package com.company.Parser.Grammar.Expression;

import com.company.Interpreter.Scope;
import com.company.Parser.MatrixVar;
import com.company.lexer.Token;
import com.company.lexer.TokenType;

public class Literal extends Expression {

    public Object value;
    public Token token;

    public Literal(Object value, Token token) {
        this.value = value;
        this.token = token;
    }

    @Override
    public Object evaluate(Scope scope) {

        if(token.getType() == TokenType.STRING)
        {
            return ((String)value).substring(1,((String) value).length()-1);
        }
        if(value instanceof MatrixVar)
        {
            return ((MatrixVar) value).evaluate(scope);
        }
        return value;

    }

    @Override
    public String toString() {
        return  value.toString();
    }
}
