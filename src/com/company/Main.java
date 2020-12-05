package com.company;

import com.company.lexer.Lexer;
import com.company.lexer.Token;
import com.company.lexer.TokenType;
import com.company.source.StringReader;

public class Main {

    public static void main(String[] args) {
	// write your code here

        Lexer lex = new Lexer(new StringReader(" ! "));
        Token t = lex.advanceToken();
        while(t.getType() != TokenType.EOF)
        {
            System.out.println(t.toString());
            t=lex.advanceToken();
        }

    }
}
