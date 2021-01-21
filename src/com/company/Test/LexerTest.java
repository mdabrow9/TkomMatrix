package com.company.Test;

import com.company.lexer.Lexer;
import com.company.lexer.Token;
import com.company.lexer.TokenType;
import com.company.source.StringReader;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class LexerTest {

    private List<Token> getTokens(String code)
    {
        Lexer lex = new Lexer(new StringReader(code));
        List<Token> tokens = new ArrayList<>();
        Token t = lex.advanceToken();
        tokens.add(t);
        while(t.getType() != TokenType.EOF)
        {
            t=lex.advanceToken();
            tokens.add(t);

        }
        tokens.add(t);
        return tokens;

    }

    @Test
    public void testString()
    {
        String code ="  \"" + "\\"+"\"" +"hello world"+ "\\"+"\"" +"\"  ";
        var tokens = getTokens(code);
        int i =0;
        assertTrue(tokens.get(i++).getType() == TokenType.STRING);
        assertTrue(tokens.get(i++).getType() == TokenType.EOF);
    }

    @Test
    public void testMatrixAssigment()
    {
        String code ="matrix <3,i> m1 = {[1,3],\n[3,1],\n[1,0]};" ;

        var tokens = getTokens(code);
        int i =0;
        assertTrue(tokens.get(i++).getType() == TokenType.MATRIX_T);
        assertTrue(tokens.get(i++).getType() == TokenType.LESS);
        assertTrue(tokens.get(i++).getType() == TokenType.INT);
        assertTrue(tokens.get(i++).getType() == TokenType.COMA);
        assertTrue(tokens.get(i++).getType() == TokenType.IDENTIFIER);
        assertTrue(tokens.get(i++).getType() == TokenType.GREATER);
        assertTrue(tokens.get(i++).getType() == TokenType.IDENTIFIER);
        assertTrue(tokens.get(i++).getType() == TokenType.EQUAL);
        assertTrue(tokens.get(i++).getType() == TokenType.LEFT_CURLY_BRACKET);
        for(int k =0 ;k<2;k++)
        {
            assertTrue(tokens.get(i++).getType() == TokenType.LEFT_SQUARE_BRACKET);
            assertTrue(tokens.get(i++).getType() == TokenType.INT);
            assertTrue(tokens.get(i++).getType() == TokenType.COMA);
            assertTrue(tokens.get(i++).getType() == TokenType.INT);
            assertTrue(tokens.get(i++).getType() == TokenType.RIGHT_SQUARE_BRACKET);
            assertTrue(tokens.get(i++).getType() == TokenType.COMA); //[1,3],
        }
            assertTrue(tokens.get(i++).getType() == TokenType.LEFT_SQUARE_BRACKET);
            assertTrue(tokens.get(i++).getType() == TokenType.INT);
            assertTrue(tokens.get(i++).getType() == TokenType.COMA);
            assertTrue(tokens.get(i++).getType() == TokenType.INT);
            assertTrue(tokens.get(i++).getType() == TokenType.RIGHT_SQUARE_BRACKET);


        assertTrue(tokens.get(i++).getType() == TokenType.RIGHT_CURLY_BRACKET); //[1,3],
        assertTrue(tokens.get(i++).getType() == TokenType.SEMICOLON);


        assertTrue(tokens.get(i++).getType() == TokenType.EOF);
    }

    @Test
    public void testFunction()
    {
        String code ="function  main()\n" +
                "{\n" +
                "    \n" +
                " \n\n\n\n\n\n        \n\n\n\n   \n\n\n\n                  " +
                "return;\n" +
                "} ";
        var tokens = getTokens(code);
        int i =0;
        assertTrue(tokens.get(i++).getType() == TokenType.FUNCTION);
        assertTrue(tokens.get(i++).getType() == TokenType.IDENTIFIER);
        assertTrue(tokens.get(i++).getType() == TokenType.LEFT_ROUND_BRACKET);
        assertTrue(tokens.get(i++).getType() == TokenType.RIGHT_ROUND_BRACKET);
        assertTrue(tokens.get(i++).getType() == TokenType.LEFT_CURLY_BRACKET);
        assertTrue(tokens.get(i++).getType() == TokenType.RETURN);
        assertTrue(tokens.get(i++).getType() == TokenType.SEMICOLON);
        assertTrue(tokens.get(i++).getType() == TokenType.RIGHT_CURLY_BRACKET);

        assertTrue(tokens.get(i++).getType() == TokenType.EOF);
    }
    @Test
    public void testKeywords()
    {
        String code ="return int string float matrix if else while print function getRow getCol main";
        var tokens = getTokens(code);
        int i =0;
        assertTrue(tokens.get(i++).getType() == TokenType.RETURN);
        assertTrue(tokens.get(i++).getType() == TokenType.INT_T);
        assertTrue(tokens.get(i++).getType() == TokenType.STRING_T);
        assertTrue(tokens.get(i++).getType() == TokenType.FLOAT_T);
        assertTrue(tokens.get(i++).getType() == TokenType.MATRIX_T);
        assertTrue(tokens.get(i++).getType() == TokenType.IF);
        assertTrue(tokens.get(i++).getType() == TokenType.ELSE);
        assertTrue(tokens.get(i++).getType() == TokenType.WHILE);
        assertTrue(tokens.get(i++).getType() == TokenType.PRINT);
        assertTrue(tokens.get(i++).getType() == TokenType.FUNCTION);
        assertTrue(tokens.get(i++).getType() == TokenType.GET_ROW);
        assertTrue(tokens.get(i++).getType() == TokenType.GET_COL);
        assertTrue(tokens.get(i++).getType() == TokenType.IDENTIFIER);
        assertTrue(tokens.get(i++).getType() == TokenType.EOF);
    }

    @Test
    public void testConditions()
    {
        String code ="if( m1 == 3 ||  ( matrix2 != ~matrix3 * |matrix3| && 3.54356 <= 0.1 ) )";
        var tokens = getTokens(code);
        int i =0;
        assertTrue(tokens.get(i++).getType() == TokenType.IF);
        assertTrue(tokens.get(i++).getType() == TokenType.LEFT_ROUND_BRACKET);
        assertTrue(tokens.get(i++).getType() == TokenType.IDENTIFIER);
        assertTrue(tokens.get(i++).getType() == TokenType.EQUALS);
        assertTrue(tokens.get(i++).getType() == TokenType.INT);
        assertTrue(tokens.get(i++).getType() == TokenType.OR);
        assertTrue(tokens.get(i++).getType() == TokenType.LEFT_ROUND_BRACKET);
        assertTrue(tokens.get(i++).getType() == TokenType.IDENTIFIER);
        assertTrue(tokens.get(i++).getType() == TokenType.NOT_EQUAL);
        assertTrue(tokens.get(i++).getType() == TokenType.TILDE);
        assertTrue(tokens.get(i++).getType() == TokenType.IDENTIFIER);
        assertTrue(tokens.get(i++).getType() == TokenType.STAR);
        assertTrue(tokens.get(i++).getType() == TokenType.PIPE);
        assertTrue(tokens.get(i++).getType() == TokenType.IDENTIFIER);
        assertTrue(tokens.get(i++).getType() == TokenType.PIPE);
        assertTrue(tokens.get(i++).getType() == TokenType.AND);
        /*assertTrue(tokens.get(i++).getType() == TokenType.NUMBER);
        assertTrue(tokens.get(i++).getType() == TokenType.DOT);
        assertTrue(tokens.get(i++).getType() == TokenType.NUMBER);*/
        assertTrue(tokens.get(i++).getType() == TokenType.FLOAT);

        assertTrue(tokens.get(i++).getType() == TokenType.LESS_EQUAL);
        /*assertTrue(tokens.get(i++).getType() == TokenType.NUMBER);
        assertTrue(tokens.get(i++).getType() == TokenType.DOT);
        assertTrue(tokens.get(i++).getType() == TokenType.NUMBER);*/
        assertTrue(tokens.get(i++).getType() == TokenType.FLOAT);
        assertTrue(tokens.get(i++).getType() == TokenType.RIGHT_ROUND_BRACKET);
        assertTrue(tokens.get(i++).getType() == TokenType.RIGHT_ROUND_BRACKET);


        assertTrue(tokens.get(i++).getType() == TokenType.EOF);
    }

    @Test
    public void testLoop()
    {
        String code ="string tekst2 = \" przykładowy\";\n" +
                "    while(a>=5)\n" +
                "    {\n" +
                "        print(a);\n" +
                "        if(a==5 || a==9)\n" +
                "        {\n" +
                "            print(tekst1 + tekst2);\n" +
                "        }\n" +
                "        a = a-1;\n" +
                "    }";
        var tokens = getTokens(code);
        int i =0;
        assertTrue(tokens.get(i++).getType() == TokenType.STRING_T);
        assertTrue(tokens.get(i++).getType() == TokenType.IDENTIFIER);
        assertTrue(tokens.get(i++).getType() == TokenType.EQUAL);
        assertTrue(tokens.get(i++).getType() == TokenType.STRING);
        assertTrue(tokens.get(i++).getType() == TokenType.SEMICOLON);
        assertTrue(tokens.get(i++).getType() == TokenType.WHILE);
        assertTrue(tokens.get(i++).getType() == TokenType.LEFT_ROUND_BRACKET);
        assertTrue(tokens.get(i++).getType() == TokenType.IDENTIFIER);
        assertTrue(tokens.get(i++).getType() == TokenType.GREATER_EQUAL);
        assertTrue(tokens.get(i++).getType() == TokenType.INT);
        assertTrue(tokens.get(i++).getType() == TokenType.RIGHT_ROUND_BRACKET);
        assertTrue(tokens.get(i++).getType() == TokenType.LEFT_CURLY_BRACKET);
            assertTrue(tokens.get(i++).getType() == TokenType.PRINT);
                assertTrue(tokens.get(i++).getType() == TokenType.LEFT_ROUND_BRACKET);
                assertTrue(tokens.get(i++).getType() == TokenType.IDENTIFIER);
                assertTrue(tokens.get(i++).getType() == TokenType.RIGHT_ROUND_BRACKET);
                assertTrue(tokens.get(i++).getType() == TokenType.SEMICOLON);
            assertTrue(tokens.get(i++).getType() == TokenType.IF);
                assertTrue(tokens.get(i++).getType() == TokenType.LEFT_ROUND_BRACKET);
                assertTrue(tokens.get(i++).getType() == TokenType.IDENTIFIER);
                assertTrue(tokens.get(i++).getType() == TokenType.EQUALS);
                assertTrue(tokens.get(i++).getType() == TokenType.INT);
                assertTrue(tokens.get(i++).getType() == TokenType.OR);
                assertTrue(tokens.get(i++).getType() == TokenType.IDENTIFIER);
                assertTrue(tokens.get(i++).getType() == TokenType.EQUALS);
                assertTrue(tokens.get(i++).getType() == TokenType.INT);
                assertTrue(tokens.get(i++).getType() == TokenType.RIGHT_ROUND_BRACKET);
            assertTrue(tokens.get(i++).getType() == TokenType.LEFT_CURLY_BRACKET);
                assertTrue(tokens.get(i++).getType() == TokenType.PRINT);
                assertTrue(tokens.get(i++).getType() == TokenType.LEFT_ROUND_BRACKET);
                assertTrue(tokens.get(i++).getType() == TokenType.IDENTIFIER);
                assertTrue(tokens.get(i++).getType() == TokenType.PLUS);
                assertTrue(tokens.get(i++).getType() == TokenType.IDENTIFIER);
                assertTrue(tokens.get(i++).getType() == TokenType.RIGHT_ROUND_BRACKET);
                assertTrue(tokens.get(i++).getType() == TokenType.SEMICOLON);
        assertTrue(tokens.get(i++).getType() == TokenType.RIGHT_CURLY_BRACKET);

        assertTrue(tokens.get(i++).getType() == TokenType.IDENTIFIER);
        assertTrue(tokens.get(i++).getType() == TokenType.EQUAL);
        assertTrue(tokens.get(i++).getType() == TokenType.IDENTIFIER);
        assertTrue(tokens.get(i++).getType() == TokenType.MINUS);
        assertTrue(tokens.get(i++).getType() == TokenType.INT);
        assertTrue(tokens.get(i++).getType() == TokenType.SEMICOLON);
        assertTrue(tokens.get(i++).getType() == TokenType.RIGHT_CURLY_BRACKET);
        assertTrue(tokens.get(i++).getType() == TokenType.EOF);
    }




}