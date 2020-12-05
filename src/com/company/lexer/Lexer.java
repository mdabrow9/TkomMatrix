package com.company.lexer;

import com.company.source.Source;

import java.util.HashMap;

public class Lexer {
    private Source source;
    private StringBuilder lexeme;

    private Token token;

    private static HashMap<String,TokenType> keywords;
    private static HashMap<Character,TokenType> singleCharacter;
    private static HashMap<String,TokenType> doubleCharacter;

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



}
