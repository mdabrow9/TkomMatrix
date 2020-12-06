package com.company.source;

import com.company.lexer.Position;

public class StringReader implements Source {

    private String code;
    private int nextCharIndex = 0;

    public StringReader(String code) {
        this.code = code + (char)-1;

    }

    @Override
    public char getChar() {
        return code.charAt(nextCharIndex++);
    }

    @Override
    public char peekNextChar() {
        return code.charAt(nextCharIndex);
    }


    @Override
    public Position getPosition() {
        return new Position(0,nextCharIndex-1);
    }

    @Override
    public String toString() {
        return "StringReader{" +
                "code='" + code + '\'' +
                ", nextCharIndex=" + (nextCharIndex-1) +
                '}';
    }
}
