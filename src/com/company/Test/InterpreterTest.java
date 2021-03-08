package com.company.Test;

import com.company.Interpreter.Interpreter;
import com.company.Parser.Grammar.Expression.Literal;
import com.company.Parser.MatrixVar;
import com.company.Parser.Parser;
import com.company.lexer.Lexer;
import com.company.source.CodeReader;
import com.company.source.StringReader;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.math.BigDecimal;

import static org.junit.Assert.*;

public class InterpreterTest
{





    private Object setup(String code , boolean isMainBody, String returnType)
    {
        final ByteArrayOutputStream myOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(myOut));

        if(isMainBody)
        {
            code = " function " +returnType + " main()" + "{" + code + "} ";
        }
        Lexer lex = new Lexer(new StringReader(code));
        Parser parser = new Parser(lex);

        var functions = parser.parse();
        Object returned =  Interpreter.run(functions);
        return  returned;
    }

    @Test
    public void test1()
    {
        String body = "return 1;";
       assertEquals(setup(body,true,"int"), 1 );
    }
    @Test
    public void test2()
    {
        String body = "return 1.5;";
       assertEquals(setup(body,true,"float"), new BigDecimal("1.5"));
    }
    @Test
    public void test3()
    {
        String body = "return \"hello\";";
        assertEquals(setup(body,true,"string"), "hello");
    }
    @Test
    public void test4()
    {
        String body = "return \"hello\";";
        assertEquals(setup(body,true,"string"), "hello");
    }

    @Test
    public void test5()
    {
        String body = "int a =5;\n" + "int a2 = -12;  return a*a2;";
        assertEquals(setup(body,true ,"int"), -60);

    }
    @Test
    public void test6()
    {
        String body = "int a =5;\n" + "float a2 = -1.2;  return a*a2;";
        assertEquals(setup(body,true,"float"), new BigDecimal("-6.0"));

    }
    @Test
    public void test7()
    {
        String body = "float a =5.1;\n" + "float a2 = -1.2;  return a*a2;";
        assertEquals( new BigDecimal("-6.12"),setup(body,true,"float"));

    }
    @Test
    public void test8()
    {
        String body = "string a =\"a\";\n" + "string a2 = \"a\";  return a+a2;";
        assertEquals( "aa",setup(body,true,"string"));

    }
    //dodawanie
    @Test
    public void test9()
    {
        String body = "int a =5;\n" + "int a2 = -12;  return a+a2;";
        assertEquals( -7,   setup(body,true,"int"));

    }
    @Test
    public void test10()
    {
        String body = "int a =5;\n" + "float a2 = -1.2;  return a+a2;";
        assertEquals(new BigDecimal("3.8") , setup(body,true,"float") );

    }
    @Test
    public void test11()
    {
        String body = "float a =5.1;\n" + "float a2 = -1.2;  return a+a2;";
        assertEquals( new BigDecimal("3.9"),setup(body,true,"float"));

    }

    //odejmowanie

    @Test
    public void test12()
    {
        String body = "int a =5;\n" + "int a2 = -12;  return a-a2;";
        assertEquals( 17,   setup(body,true,"int"));

    }
    @Test
    public void test13()
    {
        String body = "int a =5;\n" + "float a2 = -1.2;  return a-a2;";
        assertEquals(new BigDecimal("6.2") , setup(body,true,"float") );

    }
    @Test
    public void test14()
    {
        String body = "float a =5.1;\n" + "float a2 = -1.2;  return a-a2;";
        assertEquals( new BigDecimal("6.3"),setup(body,true,"float"));

    }

    //odwrotne

    @Test
    public void test15()
    {
        String body = "int a =-10;  return -a;";
        assertEquals( 10,   setup(body,true,"int"));

    }
    @Test
    public void test16()
    {
        String body = "int a =5; return -a;";
        assertEquals(-5 , setup(body,true,"int") );

    }
    @Test
    public void test17()
    {
        String body = "float a =5.1; return -a;";
        assertEquals( new BigDecimal("-5.1"),setup(body,true ,"float"));

    }


    //macierze

    @Test
    public void test18()
    {
        String body = "matrix m = {[1,2],[3,4]};  return m;";
        MatrixVar ret = (MatrixVar) setup(body,true,"matrix");
        int values [][]={{   (int )((Literal)ret.elements[0][0]).value,(int )((Literal)ret.elements[0][1]).value },
                {   (int )((Literal)ret.elements[1][0]).value,(int )((Literal)ret.elements[1][1]).value }
        };
        assertEquals(values[0][0] ,1   );
        assertEquals(values[0][1] ,2  );
        assertEquals(values[1][0] ,3   );
        assertEquals(values[1][1] ,4   );


    }
    @Test
    public void test19()
    {
        String body = "matrix m = {[1,2],[3,4]};  return m+m;";
        MatrixVar ret = (MatrixVar) setup(body,true,"matrix");
        int values [][]={{   (int )((Literal)ret.elements[0][0]).value,(int )((Literal)ret.elements[0][1]).value },
                {   (int )((Literal)ret.elements[1][0]).value,(int )((Literal)ret.elements[1][1]).value }
        };
        assertEquals(values[0][0] ,2   );
        assertEquals(values[0][1] ,4  );
        assertEquals(values[1][0] ,6   );
        assertEquals(values[1][1] ,8   );

    }
    @Test
    public void test20()
    {
        String body = "matrix m = {[1,2],[3,4]};  return m*3;";
        MatrixVar ret = (MatrixVar) setup(body,true,"matrix");
        int values [][]={{   (int )((Literal)ret.elements[0][0]).value,(int )((Literal)ret.elements[0][1]).value },
                {   (int )((Literal)ret.elements[1][0]).value,(int )((Literal)ret.elements[1][1]).value }
        };
        assertEquals(values[0][0] ,1 *3  );
        assertEquals(values[0][1] ,2 *3 );
        assertEquals(values[1][0] ,3  *3 );
        assertEquals(values[1][1] ,4  *3 );

    }
    @Test
    public void test21()
    {
        String body = "matrix m = {[1,2],[3,4]};  return m*m;";
        MatrixVar ret = (MatrixVar) setup(body,true,"matrix");
        int values [][]={{   (int )((Literal)ret.elements[0][0]).value,(int )((Literal)ret.elements[0][1]).value },
                {   (int )((Literal)ret.elements[1][0]).value,(int )((Literal)ret.elements[1][1]).value }
        };
        assertEquals(values[0][0] ,7   );
        assertEquals(values[0][1] ,10  );
        assertEquals(values[1][0] ,15  );
        assertEquals(values[1][1] ,22   );


    }
    @Test
    public void test22()
    {
        String body = "matrix m = {[1,2],[3,4]};  return m#m;";
        MatrixVar ret = (MatrixVar) setup(body,true,"matrix");
        int values [][]={{   (int )((Literal)ret.elements[0][0]).value,(int )((Literal)ret.elements[0][1]).value },
                {   (int )((Literal)ret.elements[1][0]).value,(int )((Literal)ret.elements[1][1]).value }
        };
        assertEquals(values[0][0] ,1   );
        assertEquals(values[0][1] ,4  );
        assertEquals(values[1][0] ,9 );
        assertEquals(values[1][1] ,16   );


    }

    @Test
    public void test23()
    {
        String body = "matrix m = {[1,2],[3,4]};  return @m;";
        MatrixVar ret = (MatrixVar) setup(body,true,"matrix");
        int values [][]={{   (int )((Literal)ret.elements[0][0]).value,(int )((Literal)ret.elements[0][1]).value },
                {   (int )((Literal)ret.elements[1][0]).value,(int )((Literal)ret.elements[1][1]).value }
        };
        assertEquals(values[0][0] ,1   );
        assertEquals(values[0][1] ,3  );
        assertEquals(values[1][0] ,2 );
        assertEquals(values[1][1] ,4   );


    }

    @Test
    public void test24()
    {
        String body = "matrix m = {[1,2],[3,4]};  return |m|;";
        assertEquals(setup(body,true,"int") ,-2  );

    }


    @Test
    public void test25()
    {
        String body = "int a =10; a = -3; return a;";
        assertEquals(setup(body,true,"int") ,-3  );

    }

    @Test
    public void test26()
    {
        String body = "matrix m = {[1,2],[3,4]};  return m[1,0];";
        assertEquals(setup(body,true,"int") ,3  );

    }
    @Test
    public void test27()
    {
        String body = "matrix <10,20> m ;  return getCol(m);";
        assertEquals(setup(body,true,"int") ,20  );

    }
    @Test
    public void test28()
    {
        String body = "matrix <10,20> m ;  return getRow(m);";
        assertEquals(setup(body,true,"int") ,10  );

    }

    @Test
    public void test29()
    {
        String body = "matrix m = {[1,2],[3,4]};  return ~m;";
        MatrixVar ret = (MatrixVar) setup(body,true,"matrix");
        int values [][]={{   (int )((Literal)ret.elements[0][0]).value,(int )((Literal)ret.elements[0][1]).value },
                {   (int )((Literal)ret.elements[1][0]).value,(int )((Literal)ret.elements[1][1]).value }
        };
        assertEquals(values[0][0] ,4   );
        assertEquals(values[0][1] ,-3  );
        assertEquals(values[1][0] ,-2 );
        assertEquals(values[1][1] ,1   );
    }

    @Test
    public void test30()
    {
        String body = "if(1==1){ return 1; } return 0;";
        assertEquals(setup(body,true,"int") ,1  );

    }
    @Test
    public void test31()
    {
        String body = "if(1!=1){ return 1; } return 0;";
        assertEquals(setup(body,true,"int") ,0  );

    }

    @Test
    public void test32()
    {
        String body = "if(10>1){ return 1; } return 0;";
        assertEquals(setup(body,true,"int") ,1  );

    }
    @Test
    public void test33()
    {
        String body = "if(1<10){ return 1; } return 0;";
        assertEquals(setup(body,true,"int") ,1  );

    }
    @Test
    public void test34()
    {
        String body = "if(10>=1){ return 1; } return 0;";
        assertEquals(setup(body,true,"int") ,1  );

    }
    @Test
    public void test35()
    {
        String body = "if(1<=10){ return 1; } return 0;";
        assertEquals(setup(body,true,"int") ,1  );

    }
    @Test
    public void test36()
    {
        String body = "if(10>=10){ return 1; } return 0;";
        assertEquals(setup(body,true,"int") ,1  );

    }
    @Test
    public void test37()
    {
        String body = "if(10<=10){ return 1; } return 0;";
        assertEquals(setup(body,true,"int") ,1  );

    }
    @Test
    public void test38()
    {
        String body = "if(1==1 && 2==2){ return 1; } return 0;";
        assertEquals(setup(body,true,"int") ,1  );

    }
    @Test
    public void test39()
    {
        String body = "if(1==1 || 0==2){ return 1; } return 0;";
        assertEquals(setup(body,true,"int") ,1  );

    }

    @Test
    public void test40()
    {
        String body = "if(1==1 && ( 0>5 || 1!= 5 ) ){ return 1; } return 0;";
        assertEquals(setup(body,true,"int") ,1  );

    }
    @Test
    public void test41()
    {
        String body = "if( !( 0>5 || 1!= 5 ) ){ return 1; } return 0;";
        assertEquals(setup(body,true,"int") ,0  );

    }
    @Test
    public void test42()
    {
        String body = "int a =5; while(a>0){a=a-1;} return a;";
        assertEquals(setup(body,true,"int") ,0  );

    }
    @Test
    public void test43()
    {
        String body = "int a =5; while(a>0){a=a-100;} return a;";
        assertEquals(setup(body,true,"int") ,-95  );

    }

    @Test
    public void test44()
    {
        String body =
                "function int main()" + "{" +
                        "    return foo(5);" + "}" +
                        "function int foo(int a)" +
                        "{" +
                        "    return a + 11;"
                        + "}";
        assertEquals(setup(body,false,"int") ,16  );

    }
    @Test
    public void test45()
    {
        String body =
                "function int main()" + "{" +
                        "    return foo(5);" + "}" +
                        "function int foo(int a)" +
                        "{" +
                        " if(a==0){ return -22;} " +
                        "return foo(a-1);  "
                        + "}";
        assertEquals(setup(body,false,"int") ,-22  );

    }





  /*  @Test
    public void testError()
    {
        final ByteArrayOutputStream myOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(myOut));

        setup("print(1);",true);

         String standardOutput = myOut.toString();

        assertTrue(standardOutput.equals("2"));


    }*/
}