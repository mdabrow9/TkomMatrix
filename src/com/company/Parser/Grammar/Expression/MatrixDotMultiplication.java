package com.company.Parser.Grammar.Expression;

import com.company.ErrorHandler.ErrorHandler;
import com.company.Interpreter.Scope;
import com.company.Parser.MatrixVar;
import com.company.lexer.Token;

public class MatrixDotMultiplication extends MultiplicativeExpr{
    public MatrixDotMultiplication(Expression left, Expression right, Token operator) {
        super(left, right, operator);
    }

    @Override
    public Object evaluate(Scope scope) {

        Object left = this.left.evaluate(scope);
        Object right = this.right.evaluate(scope);
        if( left instanceof MatrixVar && right instanceof MatrixVar)
        {

            return  ((MatrixVar) right).dotMultiplication( (MatrixVar) right,scope);
        }
        ErrorHandler.stop("Operacja dostępna tylko na macierzach" + (operator.getPosition()!=null?  operator.getPosition().toString() : "") );
        return null;
    }
}
