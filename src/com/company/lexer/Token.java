package com.company.lexer;

public class Token {
    private TokenType type;
    private Position position;
    private String text;

    public Token(TokenType type, Position position, String text) {
        this.type = type;
        this.position = position;
        this.text = text;
    }

    @Override
    public String toString() {
        return "Token{" +
                "type=" + type +
                ", position=[" + position.getLine() +","+position.getColumn()+"]"+
                ", text='" + text + '\'' +
                '}';
    }

    public TokenType getType() {
        return type;
    }

    public Position getPosition() {
        return position;
    }

    public String getText() {
        return text;
    }
}
