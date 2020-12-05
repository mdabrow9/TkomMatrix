package com.company.lexer;

public enum TokenType {
    //pojedyncze zanki
    LEFT_ROUND_BRACKET,     //(
    RIGHT_ROUND_BRACKET,    //)
    LEFT_SQUARE_BRACKET,    //[
    RIGHT_SQUARE_BRACKET,   //[
    LEFT_CURLY_BRACKET,     //{
    RIGHT_CURLY_BRACKET,    //}
    LESS,                   //<
    GREATER,                //>
    EQUAL,                  //=
    COMA,                   //,
    DOT,                    //.
    SEMICOLON,              //;
    QUOTE,                  //"
    PLUS,                   //+
    MINUS,                  //-
    SLASH,                  // /
    STAR,                   //*
    EXCLAMATION_MARK,       //!
    AT,                     //@
    HASHTAG,                //#
    TILDE,                  //~
    PIPE,                   //|

    //PODWÓJNE
    NOT_EQUAL,              //!=
    EQUALS,                 //==
    GREATER_EQUAL,          //>=
    LESS_EQUAL,             //<=
    AND,                    //&&
    OR,                     //||
    STRING_QUOTE,           //\"

    //SŁOWA KLUCZOWE
    RETURN,                 //return
    FUNCTION,               //function
    MATRIX,                 //matrix
    INT_T,                  //int
    FLOAT_T,                //float
    STRING_T,               //string
    IF,                     //if
    ELSE,                   //else
    WHILE,                  //while
    PRINT,                  //print
    GET_COL,                //getCol
    GET_ROW,                //getRow

    //literały
    IDENTIFIER,             //np. przykladowaZmienna
    STRING,                 //np. "hello world"
    NUMBER,                 //np. 10

    //INT,                    //np. 10
    //FLOAT,                  //np. 10.5
    //zastąpione przez number

    //pomocnicze
    ERROR,
    EOF

}
