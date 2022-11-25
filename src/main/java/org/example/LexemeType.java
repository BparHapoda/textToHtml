package org.example;

public enum LexemeType {
    COURSIVE ("<i>","</i>"),
    HALF_BOLD ("<b>","</b>"),
    CROSS ("<s>","</s>"),
    DOC("<html>\n<body>","</body>\n</html>"),
    PARAGRAPH("<p>","</p>"),
    TEXT,
    EOF;

    String startTag;
    String endTag;
    String text;
    LexemeType(String startTag,String endTag){
        this.startTag=startTag;
        this.endTag=endTag;
    }
    LexemeType(String text){
        this.text=text;
    }
 LexemeType(){}

    public void setText(String text) {
        this.text = text;
    }
}
