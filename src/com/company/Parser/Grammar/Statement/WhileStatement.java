package com.company.Parser.Grammar.Statement;

import com.company.Interpreter.Scope;
import com.company.Parser.Grammar.Expression.Expression;

import java.util.List;

public class WhileStatement extends Statement {
    public Expression condition;
    public List<Statement> body;

    public WhileStatement(Expression condition, List<Statement> body) {
        this.condition = condition;
        this.body = body;
    }

    @Override
    public Object execute(Scope scope) {
        return null;
    }
}
