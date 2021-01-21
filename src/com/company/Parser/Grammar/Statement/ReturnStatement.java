package com.company.Parser.Grammar.Statement;

import com.company.Interpreter.Scope;
import com.company.Parser.Grammar.Expression.Expression;
import com.company.lexer.Token;

public class ReturnStatement extends Statement {

    public Expression expression;
    Token token;

    public ReturnStatement(Token token,Expression expression) {
        this.expression = expression;
        this.token = token;
    }

    @Override
    public Object execute(Scope scope) {
        return null;
    }
}
