package com.company.lexer;

import java.math.BigDecimal;

public class Token {
    private final TokenType type;
    private final Position position;
    private String textValue = null;
    private int intValue;
    private BigDecimal  floatValue;
    private enum ValueType {TEXT , FLOAT , INT};
    private ValueType valueType;

    public Token(TokenType type, Position position, String textValue) {
        this.type = type;
        this.position = position;
        this.textValue = textValue;
        this.valueType = ValueType.TEXT;
    }

    public Token(TokenType type, Position position, int intValue) {
        this.type = type;
        this.position = position;
        this.intValue = intValue;
        this.valueType = ValueType.INT;
    }
    public Token(TokenType type, Position position, BigDecimal floatValue) {
        this.type = type;
        this.position = position;
        this.floatValue = floatValue;
        this.valueType = ValueType.FLOAT;
    }

    @Override
    public String toString() {
        String tmp;
        if(valueType == ValueType.TEXT)
        {
            tmp ="textValue='" + textValue + '\'' ;
        }
        else if(valueType == ValueType.INT) tmp ="numberValue=" + intValue;
        else tmp = "floatValue=" + floatValue;

        return "Token{" +
                "type=" + type +
                ", position=[" + position.getLine() +","+position.getColumn()+"]"
                + ", "+tmp +
                '}';
    }

    public TokenType getType() {
        return type;
    }

    public Position getPosition() {
        return position;
    }

    public String getTextValue() {
        return textValue;
    }

    public int getIntValue() {
        return intValue;
    }

    public BigDecimal getFloatValue() {
        return floatValue;
    }
}
