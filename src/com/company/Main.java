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
import com.company.source.CodeReader;
import com.company.source.StringReader;

public class Main {

    public static void main(String[] args) {
	// write your code here

        //src\com\company\code.txt
        if(args.length != 1)
        {
            System.out.println("Niepoprawna ilość argumentów, jako argument podaj tylko scieżkę do pliku z kodem źródłowym");
        }

        Lexer lex = new Lexer(new CodeReader(args[0]));

        //Lexer lex = new Lexer(new CodeReader("src\\com\\company\\code.txt"));
        Parser parser = new Parser(lex);

        var functions = parser.parse();
        Object returned =  Interpreter.run(functions);

        if(returned instanceof MatrixVar)
        {

            System.out.println("Proces zwrócił: " + ((MatrixVar) returned).toString(null) );

        }
        else
        {
            System.out.println( "Proces zwrócił: " + returned);
        }



    }
}
