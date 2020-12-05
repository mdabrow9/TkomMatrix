package com.company.source;

import com.company.lexer.Position;

public interface Source {

    char getChar();
    char getNextChar();
    boolean isEOF();

    Position getPosition();

}
