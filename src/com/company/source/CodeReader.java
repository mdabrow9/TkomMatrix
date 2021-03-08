package com.company.source;

import com.company.lexer.Position;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class CodeReader implements Source{

    private  Position  currentPos; // Position of recently read token

    private BufferedReader reader;
    private final boolean EOF = false;
    private char currChar = '\0'; // next sign to read and process

    public CodeReader(String fileName)
    {
        currentPos = new Position(1, 0);
        try {
            FileReader reader = new FileReader(fileName);
            this.reader = new BufferedReader(reader);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            currChar = (char)reader.read();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    @Override
    public char getChar() {
        if(currChar == '\n'){
            currentPos.setLine(1+currentPos.getLine() ); ;
            currentPos.setColumn(-1);
        }
        currentPos.setColumn(currentPos.getColumn()+1);


        char c = currChar;

        try {
            currChar = (char) reader.read();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return c;
    }

    @Override
    public char peekNextChar() {
        return currChar;
    }

    @Override
    public Position getPosition() {
        //return SerializationUtils.clone(Object);
        return new Position(currentPos.getLine(),currentPos.getColumn());
    }
}
