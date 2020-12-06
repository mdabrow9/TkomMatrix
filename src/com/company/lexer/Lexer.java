package com.company.lexer;

import com.company.ErrorHandler.ErrorHandler;
import com.company.source.Source;

import java.util.HashMap;

public class Lexer {
    private Source source;
    private StringBuilder lexeme = new StringBuilder();

    private Token token;
    private Token previousToken;

    private static HashMap<String,TokenType> keywords = new HashMap<String,TokenType>();
    private static HashMap<Character,TokenType> singleCharacter = new HashMap<Character,TokenType>();
    private static HashMap<String,TokenType> doubleCharacter = new HashMap<String,TokenType>();

    static{

        keywords.put("return",TokenType.RETURN);
        keywords.put("function",TokenType.FUNCTION);
        keywords.put("matrix",TokenType.MATRIX);
        keywords.put("int",TokenType.INT_T);
        keywords.put("float",TokenType.FLOAT_T);
        keywords.put("string",TokenType.STRING_T);
        keywords.put("if",TokenType.IF);
        keywords.put("else",TokenType.ELSE);
        keywords.put("while",TokenType.WHILE);
        keywords.put("print",TokenType.PRINT);
        keywords.put("getCol",TokenType.GET_COL);
        keywords.put("getRow",TokenType.GET_ROW);


        singleCharacter.put('(',TokenType.LEFT_ROUND_BRACKET);
        singleCharacter.put(')',TokenType.RIGHT_ROUND_BRACKET);
        singleCharacter.put('[',TokenType.LEFT_SQUARE_BRACKET);
        singleCharacter.put(']',TokenType.RIGHT_SQUARE_BRACKET);
        singleCharacter.put('{',TokenType.LEFT_CURLY_BRACKET);
        singleCharacter.put('}',TokenType.RIGHT_CURLY_BRACKET);
        singleCharacter.put('<',TokenType.LESS);
        singleCharacter.put('>',TokenType.GREATER);
        singleCharacter.put('=',TokenType.EQUAL);
        singleCharacter.put(',',TokenType.COMA);
        singleCharacter.put('.',TokenType.DOT);
        singleCharacter.put(';',TokenType.SEMICOLON);
        singleCharacter.put('"',TokenType.QUOTE);
        singleCharacter.put('+',TokenType.PLUS);
        singleCharacter.put('-',TokenType.MINUS);
        singleCharacter.put('/',TokenType.SLASH);
        singleCharacter.put('*',TokenType.STAR);
        singleCharacter.put('!',TokenType.EXCLAMATION_MARK);
        singleCharacter.put('@',TokenType.AT);
        singleCharacter.put('#',TokenType.HASHTAG);
        singleCharacter.put('~',TokenType.TILDE);
        singleCharacter.put('|',TokenType.PIPE);


        doubleCharacter.put("!=",TokenType.NOT_EQUAL);
        doubleCharacter.put("==",TokenType.EQUALS);
        doubleCharacter.put(">=",TokenType.GREATER_EQUAL);
        doubleCharacter.put("<=",TokenType.LESS_EQUAL);
        doubleCharacter.put("&&",TokenType.AND);
        doubleCharacter.put("||",TokenType.OR);
        doubleCharacter.put("\\"+"\"",TokenType.STRING_QUOTE);

    }

    public Lexer(Source source) {
        this.source = source;
    }

    public Token advanceToken()
    {
        previousToken = token;
        skipWhitespace();
        char c = advanceChar();


        if(isEOF(c))
        {
            token = new Token(TokenType.EOF,source.getPosition(),"");
            return token;
        }
        var result = singleCharacter.get(c);
        //wartość string
        if(result == TokenType.QUOTE) //TODO przerobić na oddzielnie cudzysłów i oddzielnie tekst w razie czego
        {
            token  = getStringToken();
            return token;
        }

        if(result!=null ||  c == '&')
        {
            char nextC = source.peekNextChar();
            if( nextC == '=' || nextC == '&' || nextC == '|' ) //dwuznakowy token
            {
                advanceChar();
                var type = doubleCharacter.get(lexeme.toString());
                token = new Token(type,source.getPosition(),getLexeme());
                return token;
            }
            else                                            //jednoznakowy token
            {
                var type = singleCharacter.get(Character.valueOf(lexeme.charAt(0)));
                if(c == '&')
                {
                    token = new Token(TokenType.ERROR,source.getPosition(),getLexeme());

                    ErrorHandler.stop("niepasujący token");
                    return token;
                }
                token = new Token(type,source.getPosition(),getLexeme());
                return token;
            }


        }
        //identifier lub keyword
        if(Character.isLetter(c))
        {
            token = getIdentifierToken();
            return token;
        }
        //liczba
        if(Character.isDigit(c)) //TODO dowiedzieć się czy lepiej rozpoznawać na int czy float na lex czy par
        {
            token = getNumberToken();
            return token;

        }
        token =new Token(TokenType.ERROR,source.getPosition(),getLexeme());
        ErrorHandler.stop("niepasujący token");
        return token;





    }
    private boolean isEOF(char c)
    {
        return c == (char) -1;
    }

    private  Token getNumberToken()
    {
        char c;
        while (Character.isDigit(source.peekNextChar()))
        {
            c = advanceChar();
        }
        return new Token(TokenType.NUMBER,source.getPosition(),getLexeme());
    }

    private Token getIdentifierToken()
    {
        char c ;//= advanceChar();
        while(Character.isLetterOrDigit(source.peekNextChar()))
        {
            c = advanceChar();
        }
        String lex = getLexeme();
        var keywordType = keywords.get(lex);
        if(keywordType != null)
        {
            return new Token(keywordType,source.getPosition(),lex);
        }

        return new Token(TokenType.IDENTIFIER,source.getPosition(),lex);
    }


    private Token getStringToken()
    {
        char prev = 'a' , c = advanceChar();
        while (c!= '"' || prev =='\\' )
        {
            if(c =='"') //usuwanie '\'
            {
                lexeme.deleteCharAt(lexeme.length()-2);
            }

            prev = c;
            c = advanceChar();
            if(isEOF(c))
            {
                token =new Token(TokenType.ERROR,source.getPosition(),getLexeme());
                ErrorHandler.stop("niepasujący token: nie zakończony string");
                return token;
            }
        }
        return new Token(TokenType.STRING,source.getPosition(),getLexeme());


    }

    private String getLexeme()
    {
        String result = lexeme.toString();
        lexeme.setLength(0);
        return result;
    }
    private char advanceChar()
    {
        char c = source.getChar();
        lexeme.append(c);
        return c;
    }

    private void skipWhitespace()
    {
        while(isWhitespace(source.peekNextChar()))
        {
            source.getChar();
        }
    }
    private boolean isWhitespace(char c)
    {
        return c == ' ' || c == '\n' || c == '\t';
    }



}
