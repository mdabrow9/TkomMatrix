package com.company.Parser;

import com.company.ErrorHandler.ErrorHandler;
import com.company.Parser.Grammar.Expression.*;
import com.company.Parser.Grammar.Statement.*;
import com.company.lexer.Lexer;
import com.company.lexer.Token;
import com.company.lexer.TokenType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Parser {


    private Lexer lexer;
    private HashMap<String, FunctionStatement> functions;
    private Token current,next;

    private static final TokenType[] VarToken = {TokenType.INT_T,TokenType.FLOAT_T,TokenType.MATRIX_T,TokenType.STRING_T};

    public HashMap<String, FunctionStatement> parse()
    {
        while (lexer.getToken().getType()!=TokenType.EOF)
        {
            var newFunc = parseFunction();
            if(functions.containsKey(newFunc.identifier.getTextValue())) ErrorHandler.stop("Funkcja o nazwie: "+ newFunc.identifier.getTextValue() + "już istnieje "+ newFunc.identifier.getPosition().toString());
            functions.put(newFunc.identifier.getTextValue(),newFunc);
        }

        return functions;
    }
    private FunctionStatement parseFunction()
    {
        acceptAndAdvanceToken(TokenType.FUNCTION);
        Token returnType= acceptAndAdvanceToken(TokenType.INT_T,TokenType.FLOAT_T,TokenType.MATRIX_T,TokenType.STRING_T);

        Token funcId = acceptAndAdvanceToken(TokenType.IDENTIFIER);
        List<DeclarationStatement> vars = new ArrayList<>();

        acceptAndAdvanceToken(TokenType.LEFT_ROUND_BRACKET);
        if(peekToken()!= TokenType.RIGHT_ROUND_BRACKET)
        {
            vars.add( parseVarDeclaration() );
            while (peekToken()==TokenType.COMA)
            {
                acceptAndAdvanceToken(TokenType.COMA);
                vars.add( parseVarDeclaration());
            }

        }
        acceptAndAdvanceToken(TokenType.RIGHT_ROUND_BRACKET);
        acceptAndAdvanceToken(TokenType.LEFT_CURLY_BRACKET);

        List<Statement> body = parseBody();

        acceptAndAdvanceToken(TokenType.RIGHT_CURLY_BRACKET);


        return new FunctionStatement(returnType,funcId,vars,body);


    }

    private List<Statement> parseBody()
    {
        List<Statement> result =new ArrayList<>();
        while (peekToken()!=TokenType.RIGHT_CURLY_BRACKET)
        {

            result.add(parseStatement());
        }

        return result;
    }

    private Statement parseStatement()
    {
        Token token = acceptAndAdvanceToken(TokenType.IF,TokenType.WHILE,TokenType.INT_T,TokenType.FLOAT_T,TokenType.MATRIX_T,TokenType.STRING_T,TokenType.RETURN,TokenType.IDENTIFIER);

        Statement result = null;
        switch (token.getType())
        {
            case IF:
                result = parseIf();
                break;
            case WHILE:
                result = parseWhile();
                break;
            case MATRIX_T,INT_T,STRING_T,FLOAT_T://deklaracja
                result = parseDeclaration(token);
                break;

            case RETURN: //zwrot

                Expression expression = parseExpression();
                acceptAndAdvanceToken(TokenType.SEMICOLON);
                result = new ReturnStatement(token,expression );
                break;
            case IDENTIFIER: // przypisanie lub wywołanie funkcji
                if(peekToken() == TokenType.LEFT_ROUND_BRACKET)
                {
                    acceptAndAdvanceToken(TokenType.LEFT_ROUND_BRACKET);
                    var args = parseFunctionCallArguments();
                    acceptAndAdvanceToken(TokenType.RIGHT_ROUND_BRACKET);
                    acceptAndAdvanceToken(TokenType.SEMICOLON);
                    return new FunctionCallStatement(token.getTextValue(),args);
                }
                else
                {
                    acceptAndAdvanceToken(TokenType.EQUAL);
                    Expression expr = parseExpression();
                    acceptAndAdvanceToken(TokenType.SEMICOLON);
                    return new AssignStatement(expr,token.getTextValue());
                }


            default:
                ErrorHandler.stop("nieznany błąd parsowania" + lexer.getToken().toString());
                break;
        }

        return result;
    }



    private DeclarationStatement parseDeclaration(Token typeToken)
    {
        if(current.getType() == TokenType.MATRIX_T && peekToken() == TokenType.LESS)
        {
            return parseMatrixDeclaration(typeToken);
        }
        Token id = acceptAndAdvanceToken(TokenType.IDENTIFIER);
        Expression val = null;
        if(peekToken() == TokenType.EQUAL)
        {
            acceptAndAdvanceToken(TokenType.EQUAL);
            val = parseExpression();
        }
        acceptAndAdvanceToken(TokenType.SEMICOLON);
        return new DeclarationStatement(typeToken.getType(),val,id.getTextValue(),false);
    }
    private DeclarationStatement parseMatrixDeclaration(Token typeToken)
    {
        acceptAndAdvanceToken(TokenType.LESS);
        Expression row = parseAdditiveExpr();
        acceptAndAdvanceToken(TokenType.COMA);
        Expression col = parseAdditiveExpr();
        acceptAndAdvanceToken(TokenType.GREATER);

        Token id = acceptAndAdvanceToken(TokenType.IDENTIFIER);
        Expression val = null;
        if(peekToken() == TokenType.EQUAL)
        {
            acceptAndAdvanceToken(TokenType.EQUAL);
            val = parseExpression();
        }
        acceptAndAdvanceToken(TokenType.SEMICOLON);
        return new DeclarationStatement(typeToken.getType(),val,id.getTextValue(),row,col,false);

    }

    private Statement parseWhile()
    {
        acceptAndAdvanceToken(TokenType.LEFT_ROUND_BRACKET);
        Expression condition = parseExpression();
        acceptAndAdvanceToken(TokenType.RIGHT_ROUND_BRACKET);
        acceptAndAdvanceToken(TokenType.LEFT_CURLY_BRACKET);
        List<Statement> whileBody = parseBody();

        acceptAndAdvanceToken(TokenType.RIGHT_CURLY_BRACKET);

        return new WhileStatement(condition,whileBody);
    }

    private Statement parseIf()
    {

        acceptAndAdvanceToken(TokenType.LEFT_ROUND_BRACKET);
        Expression condition = parseExpression();
        acceptAndAdvanceToken(TokenType.RIGHT_ROUND_BRACKET);
        acceptAndAdvanceToken(TokenType.LEFT_CURLY_BRACKET);
        List<Statement> ifBody = parseBody();
        List<Statement> elseBody = null;
        acceptAndAdvanceToken(TokenType.RIGHT_CURLY_BRACKET);
        if(peekToken() == TokenType.ELSE)
        {
            acceptAndAdvanceToken(TokenType.ELSE);
            acceptAndAdvanceToken(TokenType.LEFT_CURLY_BRACKET);
            elseBody = parseBody();
            acceptAndAdvanceToken(TokenType.RIGHT_CURLY_BRACKET);
        }
        return new IfStatement(condition,ifBody,elseBody);
    }

    private Expression parseExpression()
    {
        return parseOr();
    }

    private Expression parseOr()
    {
        Expression expr = parseAnd();

        while(peekToken()== TokenType.OR)
        {
            Token token = acceptAndAdvanceToken(TokenType.OR);
            Expression right = parseAnd();
            expr = new OrExpr(expr,right,token);
        }

        return expr;
    }

    private Expression parseAnd()
    {
        Expression expr = parseEqual();

        while(peekToken()== TokenType.AND)
        {
            Token token = acceptAndAdvanceToken(TokenType.AND);
            Expression right = parseEqual();
            expr = new AndExpr(expr,right,token);
        }

        return expr;

    }

    private Expression parseEqual()
    {
        Expression expr = parseComparison();

        while(peekToken()== TokenType.EQUALS || peekToken()== TokenType.NOT_EQUAL )
        {
            Token token = acceptAndAdvanceToken(TokenType.EQUALS,TokenType.NOT_EQUAL);
            Expression right = parseComparison();
            expr = new Equality(expr,right,token);
        }

        return expr;
    }

    private Expression parseComparison()
    {
        Expression expr = parseAdditiveExpr();

        while(peekToken()== TokenType.GREATER_EQUAL || peekToken()== TokenType.LESS_EQUAL || peekToken()== TokenType.LESS || peekToken()== TokenType.GREATER  )
        {
            Token token = acceptAndAdvanceToken(TokenType.GREATER_EQUAL,TokenType.LESS_EQUAL,TokenType.LESS,TokenType.GREATER);
            Expression right = parseAdditiveExpr();
            expr = new Comparison(expr,right,token);
        }

        return expr;
    }

    private Expression parseAdditiveExpr()
    {
        Expression expr = parseMultiplicative();

        while(peekToken()== TokenType.PLUS || peekToken()== TokenType.MINUS )
        {
            Token token = acceptAndAdvanceToken(TokenType.PLUS,TokenType.MINUS);
            Expression right = parseMultiplicative();
            if(token.getType() == TokenType.PLUS)
            {
                expr= new Sum(expr,right,token);
            }else expr= new Subtraction(expr,right,token);

        }

        return expr;


    }

    private Expression parseMultiplicative()
    {
        Expression expr = parseUnary();
        while(peekToken()== TokenType.SLASH || peekToken()== TokenType.STAR  || peekToken()== TokenType.HASHTAG)
        {
            Token token = acceptAndAdvanceToken(TokenType.SLASH,TokenType.STAR,TokenType.HASHTAG);
            Expression right = parseUnary();


            if(token.getType() == TokenType.STAR)
            {
                expr= new Multiplication(expr,right,token);
            }
            else if(token.getType() ==TokenType.SLASH)
            {
                expr= new Division(expr,right,token);
            }
            else if(token.getType() == TokenType.HASHTAG)
            {
                expr = new MatrixDotMultiplication(expr,right,token);
            }
        }

        return expr;
    }


    private Expression parseUnary()
    {

        if(peekToken() == TokenType.EXCLAMATION_MARK)
        {
                Token token = acceptAndAdvanceToken(TokenType.EXCLAMATION_MARK);

                Expression expr = parseBasic();
                return new Not(expr,token);

        }else if(peekToken() == TokenType.MINUS)
        {
            Token token = acceptAndAdvanceToken(TokenType.MINUS);

            Expression expr = parseBasic();
            return new Inverse(expr,token);
        }
        else if(peekToken() == TokenType.PIPE)
        {
            Token token = acceptAndAdvanceToken(TokenType.PIPE);
            Expression expr = parseBasic();
            acceptAndAdvanceToken(TokenType.PIPE);
            return new MatrixDet(expr,token);
        }
        else if(peekToken() == TokenType.AT)
        {
            Token token = acceptAndAdvanceToken(TokenType.AT);
            Expression expr = parseBasic();
            return new Transposition(expr,token);
        }else if(peekToken() == TokenType.TILDE)
        {
            Token token = acceptAndAdvanceToken(TokenType.TILDE);

            Expression expr = parseBasic();
            return new Adjugate(expr,token);
        }


        return  parseBasic();
    }

    private Expression parseBasic()
    {

        Token token;

        switch (peekToken())
        {
            case LEFT_ROUND_BRACKET:
                acceptAndAdvanceToken(TokenType.LEFT_ROUND_BRACKET);
                Expression e = parseOr();
                acceptAndAdvanceToken(TokenType.RIGHT_ROUND_BRACKET);
                return e;
            case IDENTIFIER:
                token = acceptAndAdvanceToken(TokenType.IDENTIFIER);
                if(peekToken() == TokenType.LEFT_SQUARE_BRACKET)
                {
                    Token item = acceptAndAdvanceToken(TokenType.LEFT_SQUARE_BRACKET);
                    Expression row = parseExpression();
                    acceptAndAdvanceToken(TokenType.COMA);
                    Expression col = parseExpression();
                    acceptAndAdvanceToken(TokenType.RIGHT_SQUARE_BRACKET);
                    return new MatrixElement(new Identifier(token),item,col,row);
                }
                else if(peekToken() == TokenType.LEFT_ROUND_BRACKET)
                {
                    acceptAndAdvanceToken(TokenType.LEFT_ROUND_BRACKET);
                    var args = parseFunctionCallArguments();
                    acceptAndAdvanceToken(TokenType.RIGHT_ROUND_BRACKET);
                    return new FunctionCallExpression(token.getTextValue(),args);
                }


                return new Identifier(token);

            case INT:
                 token = acceptAndAdvanceToken(TokenType.INT);
                return new Literal(token.getIntValue(),token);

            case STRING:
                 token = acceptAndAdvanceToken(TokenType.STRING );
                return new Literal(token.getTextValue(),token);

            case FLOAT:
                token = acceptAndAdvanceToken( TokenType.FLOAT);
                return new Literal(token.getFloatValue(),token);

            case LEFT_CURLY_BRACKET:
               return parseMatrixLiteral();
            default:
                acceptAndAdvanceToken(TokenType.LEFT_ROUND_BRACKET,TokenType.IDENTIFIER,TokenType.INT,TokenType.STRING,TokenType.FLOAT,TokenType.LEFT_CURLY_BRACKET);
        }
        return null;
    }

    private List<Expression> parseFunctionCallArguments()
    {
        List <Expression> args = new ArrayList<>();
        if(peekToken() == TokenType.RIGHT_ROUND_BRACKET)
        {
            return args;
        }
        args.add(parseExpression());
        while (peekToken()== TokenType.COMA)
        {
            acceptAndAdvanceToken(TokenType.COMA);
            args.add(parseExpression());
        }
        return args;
    }

    private Literal parseMatrixLiteral()
    {
        Token token =acceptAndAdvanceToken( TokenType.LEFT_CURLY_BRACKET);

        Expression val =null;
        List<Expression> values= new ArrayList<>();
        int i=0,j=0,k=0;
        while (peekToken()!= TokenType.RIGHT_CURLY_BRACKET || current.getType() == TokenType.COMA)
        {
            acceptAndAdvanceToken(TokenType.LEFT_SQUARE_BRACKET);
            while (peekToken()!= TokenType.RIGHT_SQUARE_BRACKET || current.getType() == TokenType.COMA)
            {

                val = parseExpression();
                values.add(val);
                i++;

                if(peekToken() != TokenType.RIGHT_SQUARE_BRACKET)
                {
                    acceptAndAdvanceToken(TokenType.COMA);

                }
            }
            if(current.getType() ==TokenType.LEFT_SQUARE_BRACKET) ErrorHandler.stop("Wiersz w macierzy nie może być pusty "+current.getPosition().toString());
            acceptAndAdvanceToken(TokenType.RIGHT_SQUARE_BRACKET);

            j++;
            k=i;
            i=0;

            if(peekToken() != TokenType.RIGHT_CURLY_BRACKET)
            {
                acceptAndAdvanceToken(TokenType.COMA);

            }

        }

        if(k*j != values.size()) ErrorHandler.stop("Błąd parsowania: " + token.getPosition().toString() + "nie równa liczba elementów w wierszach");
        acceptAndAdvanceToken(TokenType.RIGHT_CURLY_BRACKET);

        Expression[][]  expressions = new Expression[j][k];
        i=0;
        for(int ip =0;ip<j;ip++)
        {
            for(int l =0; l< k;l++)
            {
                expressions[ip][l] = values.get(i++);
            }
        }
        return new Literal(new MatrixVar(k,j,expressions),token);


    }




    private DeclarationStatement parseVarDeclaration()
    {

        Token type = acceptAndAdvanceToken(VarToken);
        if(type.getType() == TokenType.MATRIX_T && peekToken() == TokenType.LESS)
        {
            acceptAndAdvanceToken(TokenType.LESS);
            Expression row = parseAdditiveExpr();
            acceptAndAdvanceToken(TokenType.COMA);
            Expression col = parseAdditiveExpr();
            acceptAndAdvanceToken(TokenType.GREATER);

            Token id = acceptAndAdvanceToken(TokenType.IDENTIFIER);

            return new DeclarationStatement(type.getType(),null,id.getTextValue(),row,col,true);
        }

        return new DeclarationStatement(type.getType(),null,acceptAndAdvanceToken(TokenType.IDENTIFIER).getTextValue(),true);
    }



    public Parser(Lexer lexer) {
        this.lexer = lexer;
        this.functions = new HashMap<>();
        current = null;
        next = lexer.advanceToken();


    }

    private Token advanceToken()
    {
        current = next;
        next = lexer.advanceToken();

        return current;
    }
    private TokenType peekToken()
    {
        return next.getType();
    }
    private Token acceptAndAdvanceToken(TokenType... expected)
    {
        if(expected.length==1)
        {
            return acceptToken(advanceToken(),expected[0]);
        }
        else
        {
            return acceptTokens(advanceToken(),expected);
        }
    }

    private Token acceptTokens(Token token, TokenType... expected)
    {
        for (TokenType item: expected)
        {
            if(token.getType() == item) return token;
        }
        ErrorHandler.stop("Spodziewano się: "+ Arrays.toString(expected) + "a napotkano: " +token.getType() + "pozycja: " +token.getPosition().toString());
        return token;

    }

    private Token acceptToken( Token token ,TokenType expected)
    {
        if(expected != token.getType())
        {
            ErrorHandler.stop("Spodziewano się: "+ expected + " ,a napotkano: " +token.getType() + " pozycja: " +token.toString());
        }
        return token;

    }


}
