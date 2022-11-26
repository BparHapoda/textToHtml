package org.example;

public class Lexeme {
LexemeType type;
String text;

public Lexeme (LexemeType type){
    this.type=type;
    this.text=type.tag;
}
    public Lexeme (LexemeType type,String text){
        this.type=type;
        this.text=type.tag;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LexemeType getType() {
        return type;
    }

}
