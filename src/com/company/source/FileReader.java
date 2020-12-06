package com.company.source;

import com.company.lexer.Position;

public class FileReader implements Source{
    @Override
    public char getChar() {
        return 0;
    }

    @Override
    public char peekNextChar() {
        return 0;
    }

    @Override
    public Position getPosition() {
        return null;
    }
}
