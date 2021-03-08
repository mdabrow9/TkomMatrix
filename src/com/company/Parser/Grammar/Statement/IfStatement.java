package com.company.Parser.Grammar.Statement;

import com.company.Interpreter.Scope;
import com.company.Parser.Grammar.Expression.Expression;

import java.util.List;

public   class  IfStatement extends Statement
{
    public Expression condition;
    public List<Statement> ifBody;

    public List<Statement> elseBody;

    public IfStatement(Expression condition, List<Statement> ifBody, List<Statement> elseBody) {
        this.condition = condition;
        this.ifBody = ifBody;
        this.elseBody = elseBody;
    }

    @Override
    public Object execute(Scope scope) {

        if((Boolean) condition.evaluate(scope))
        {
            //wykonywanie statmentów
            for (Statement statement : ifBody)
            {
                if(statement instanceof ReturnStatement)
                {
                    return statement;
                }
                statement.execute(scope);
            }
        }
        else
        {
            if(elseBody != null)
            {
                //wykonywanie statmentów
                for (Statement statement : elseBody)
                {
                    if(statement instanceof ReturnStatement)
                    {
                        return statement;
                    }
                    statement.execute(scope);
                }
            }
        }
        return null;
    }
}