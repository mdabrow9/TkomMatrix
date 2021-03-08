package com.company.Parser.Grammar.Expression;

import com.company.ErrorHandler.ErrorHandler;
import com.company.Interpreter.Scope;
import com.company.lexer.Token;
import com.company.lexer.TokenType;

public class AndExpr extends Binary{
    public AndExpr(Expression left, Expression right, Token operator) {
        super(left, right, operator);
    }

    @Override
    public Object evaluate(Scope scope) {
        Object left = this.left.evaluate(scope);
        Object right = this.right.evaluate(scope);


        if(left instanceof Boolean && right instanceof Boolean)
        {
            return   (Boolean) left && (Boolean) right;
        }
        ErrorHandler.stop("Obie strony porówniania muszą być tego samego typu!!!" + (operator.getPosition()!=null?  operator.getPosition().toString() : "") );
        return null;
    }
}
