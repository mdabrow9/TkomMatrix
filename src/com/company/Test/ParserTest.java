package com.company.Test;

import com.company.Parser.Grammar.Expression.*;
import com.company.Parser.Grammar.Statement.*;
import com.company.Parser.MatrixVar;
import com.company.Parser.Parser;
import com.company.lexer.Lexer;
import com.company.lexer.TokenType;
import com.company.source.StringReader;
import org.junit.Test;

import java.util.List;
import java.util.function.ObjIntConsumer;

import static org.junit.Assert.*;

public class ParserTest {

    @Test
    public void parseFunctionTest()
    {
        String code = "function int main(int a, matrix b){}";
        Lexer lex = new Lexer(new StringReader(code));
        Parser parser = new Parser(lex);

        var result = parser.parse();
        assertTrue(result.get("main")  != null);
        var func = result.get("main");
        assertTrue(func instanceof FunctionStatement);
        assertTrue(func.returnType.getType() == TokenType.INT_T );
        assertTrue(func.parameters.get(0).name.equals("a"));
        assertTrue(func.parameters.get(0).variableType == TokenType.INT_T);
        assertTrue(func.parameters.get(1).name.equals("b") );
        assertTrue(func.parameters.get(1).variableType == TokenType.MATRIX_T);

        assertTrue(func.parameters.size() == 2);
    }

    @Test
    public void parseIfTest()
    {
        IfStatement result = (IfStatement) setupFunctionBody("if( a<b ){ a=b; }else { a= -b;}").get(0);
        assertTrue(result instanceof IfStatement);
        assertTrue(result.condition instanceof Comparison);
        assertTrue(result.ifBody.get(0) instanceof AssignStatement);
        assertTrue(result.elseBody.get(0) instanceof AssignStatement);
    }
    @Test
    public void parseWhileTest()
    {
        WhileStatement result = (WhileStatement) setupFunctionBody("while( a<b ){ a= a+b; }").get(0);
        assertTrue(result instanceof WhileStatement);
        assertTrue(result.condition instanceof Comparison);
        assertTrue(result.body.get(0) instanceof AssignStatement);

    }
    @Test
    public void parseFunctionCallTest()
    {
        FunctionCallStatement result = (FunctionCallStatement) setupFunctionBody("print(a+b , 2);").get(0);
        assertTrue(result instanceof FunctionCallStatement);
        assertEquals("print", result.identifier);
        assertTrue(result.arguments.get(0) instanceof Sum);
        assertTrue(result.arguments.get(1) instanceof Literal);


    }

    @Test
    public void parseAssignAndMatrixDeclarationTest()
    {
        AssignStatement result = (AssignStatement) setupFunctionBody("myVariable = {[1,3],[2,1],[1,0]};").get(0);
        assertTrue(result instanceof AssignStatement);
        assertEquals("myVariable", result.var);
        assertTrue(result.expression instanceof Literal);
        assertTrue(((Literal) result.expression).value instanceof MatrixVar);

        MatrixVar matrixVar = (MatrixVar)((Literal) result.expression).value;
        assertEquals(3, matrixVar.row);
        assertEquals(2, matrixVar.col);
        assertEquals(1, (int) ((Literal) matrixVar.elements[0][0]).value);
        assertEquals(3, (int) ((Literal) matrixVar.elements[0][1]).value);
        assertEquals(2, (int) ((Literal) matrixVar.elements[1][0]).value);
        assertEquals(1, (int) ((Literal) matrixVar.elements[1][1]).value);
        assertEquals(1, (int) ((Literal) matrixVar.elements[2][0]).value);
        assertEquals(0, (int) ((Literal) matrixVar.elements[2][1]).value);
    }

    @Test
    public void parseAssignAndMatrixDeclarationTest2()
    {
        AssignStatement result = (AssignStatement) setupFunctionBody("myVariable = {[1]};").get(0);
        assertTrue(result instanceof AssignStatement);
        assertEquals("myVariable", result.var);
        assertTrue(result.expression instanceof Literal);
        assertTrue(((Literal) result.expression).value instanceof MatrixVar);

        MatrixVar matrixVar = (MatrixVar)((Literal) result.expression).value;
        assertEquals(1, matrixVar.row);
        assertEquals(1, matrixVar.col);
        assertEquals(1, (int) ((Literal) matrixVar.elements[0][0]).value);

    }

    @Test
    public void parseCondition()
    {

        IfStatement result = (IfStatement) setupFunctionBody("if( a <b && b==c ||  !(a >  1 || b != 4 )  && |foo2(a , 3 ,3.5 )| > a[2,4] ){ }").get(0);
        assertTrue(result instanceof IfStatement);
        assertTrue(result.condition instanceof OrExpr);
        Expression l,r,ll,lr,lll,llr,lrl,lrr,rl,rr,rll,rlr,rrl,rrr,rlrl,rlrr;
        l = ((Binary) result.condition).left;
        r=((Binary) result.condition).right;
        ll=((Binary)l).left;
        lr = ((Binary)l).right;
        lll = ((Binary)ll).left;
        llr = ((Binary)ll).right;
        lrl = ((Binary)lr).left;
        lrr = ((Binary)lr).right;
        rl= ((Binary)r).left;
        rr = ((Binary)r).right;
        rlr = ((Unary)rl).right;
        rrl = ((Binary)rr).left;
        rrr= ((Binary)rr).right;
        rlrl =((Binary)rlr).left;
        rlrr = ((Binary)rlr).right;


                                                                                        assertTrue(l instanceof AndExpr);
                                                                                        assertTrue(r instanceof AndExpr);


                                    assertTrue(ll instanceof Comparison);                                                                                    assertTrue(rl instanceof Not);
                                    assertTrue(lr instanceof Equality);                                                                                     assertTrue(rr instanceof Comparison);

        assertTrue(lll instanceof Identifier);     assertTrue(lrl instanceof Identifier);                               assertTrue(rlr instanceof OrExpr);                           assertTrue(rrl instanceof MatrixDet);
        assertTrue(llr instanceof Identifier);      assertTrue(lrr instanceof Identifier);                                                                                                assertTrue(rrr instanceof MatrixElement);

                                                                                                                         assertTrue(rlrl instanceof Comparison);
                                                                                                                         assertTrue(rlrr instanceof Equality);











    }

    @Test
    public void parseReturnAndExpressionTest()
    {
        ReturnStatement result = (ReturnStatement) setupFunctionBody("return 1+2+3*(4*5+6)*7;").get(0);
        assertTrue(result instanceof ReturnStatement);

        Expression l,r,ll,lr,rl,rr,rll,rlr,rlrl,rlrr,rlrll,rlrlr;
        l = ((Binary) result.expression).left; //a
        r=((Binary) result.expression).right; //b
        ll=((Binary)l).left;//c
        lr = ((Binary)l).right;//d
        rl = ((Binary)r).left;//e
        rr = ((Binary)r).right;//f
        rll = ((Binary)rl).left;//g
        rlr = ((Binary)rl).right;//h
        rlrl = ((Binary)rlr).left;//i
        rlrr = ((Binary)rlr).right;//j
        rlrll = ((Binary)rlrl).left;//k
        rlrlr = ((Binary)rlrl).right;//l

        assertTrue( l instanceof Sum);
        assertTrue(r instanceof Multiplication );
        assertTrue( ll instanceof Literal);
        assertTrue(lr  instanceof Literal);
        assertTrue(rl instanceof Multiplication);
        assertTrue(rr  instanceof Literal);
        assertTrue(rll instanceof Literal);
        assertTrue(rlr instanceof Sum);
        assertTrue(rlrl instanceof Multiplication);
        assertTrue(rlrr instanceof Literal);
        assertTrue(rlrll instanceof Literal);
        assertTrue(rlrlr instanceof Literal);
    }

    private List<Statement> setupFunctionBody(String body)
    {
        String code = "function int main(){" + body + "}";
        Lexer lex = new Lexer(new StringReader(code));
        Parser parser = new Parser(lex);
        return  parser.parse().get("main").body;
    }
}