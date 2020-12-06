package com.company;

import com.company.lexer.Lexer;
import com.company.lexer.Token;
import com.company.lexer.TokenType;
import com.company.source.StringReader;

public class Main {

    public static void main(String[] args) {
	// write your code here

        /*String code ="if  \"" + "\\"+"\"" +"hello world"+ "\\"+"\"" +"\"  ";
        code = "matrix <3,i> m1 = {[1,3],\n[3,1],\n[1,0]};";
        code = "1 >=  == != || && <= 2";
        System.out.println(code);
        Lexer lex = new Lexer(new StringReader(code));
        Token t = lex.advanceToken();
        while(t.getType() != TokenType.EOF)
        {
            System.out.println(t.toString());
            t=lex.advanceToken();
        }
        System.out.println(t.toString());*/

    }
}
