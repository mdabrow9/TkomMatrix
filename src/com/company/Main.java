package com.company;

import java.math.BigDecimal;


import com.company.Interpreter.Interpreter;
import com.company.Parser.Grammar.Expression.Literal;
import com.company.Parser.Grammar.Statement.AssignStatement;
import com.company.Parser.MatrixVar;
import com.company.Parser.Parser;
import com.company.lexer.Lexer;
import com.company.lexer.Token;
import com.company.lexer.TokenType;
import com.company.source.StringReader;

public class Main {

    public static void main(String[] args) {
	// write your code here

        String code ="if  \"" + "\\"+"\"" +"hello world"+ "\\"+"\"" +"\"  ";
        code = "matrix <3,i> m1 = {[1,3],\n[3,1],\n[1,0]};";
        code = "{[2+2,3],[3,1],[1,0,4]}";
        code = "function int main(int a, matrix b) " +
                "{ " +
                "if( {[1,3],[3,1],[1,0]} < 1  && |foo2(a , 3 ,3.5 )| > a[2,4] )" +
                "{ " +
                    "while(1>0) " +
                    "{ " +
                        "Print(\"text\"); " +
                    "} " +
                "} " +
                "}" + "function int foo() { int a =3+1*25/3;" +
                "a = 5;" +
                "return a;}"
        ;



        code = "function int main() " +
                "{ " +
                "matrix <2,2> a  ;"
                +
                "print(a);"
                +"a =  {[1,3],[3,1],[1,0]}; "
                +
                "print(a);" +

                "}" ;


        Lexer lex = new Lexer(new StringReader(code));


        /*Token t = lex.advanceToken();
        System.out.println(t.toString());

        while (t.getType() != TokenType.EOF)
        {
            t = lex.advanceToken();
            System.out.println(t.toString());
        }*/


        Parser parser = new Parser(lex);

        var lel = parser.parse();

        Interpreter.run(lel);

        System.out.println( "koniec");


    }
}
