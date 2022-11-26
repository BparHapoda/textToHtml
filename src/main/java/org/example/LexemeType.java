package org.example;

/**
 * - *текст в курсивном начертании* -- курсив
 * - **текст в полужирном начертании** -- полужирный
 * - ~~зачёркнутый текст~~ -- зачёркивание
 (***) должна трактоваться как "<b><i>
 пустыми строками, следует обернуть в теги <p>...</p>
 **/

public enum LexemeType {
    COURSIVE_START ("<i>"), //  курсив
    COURSIVE_END ("</i>"),
    BOLD_START ("<b>"),        // жирный
    BOLD_END ("</b>"),
    CROSS_START ("<s>"),            // зачеркнутый текст
    CROSS_END ("</s>"),
    CROSS_BOLD_START("<b><i>"),                                   // жирный курсив
    DOC_START("<html><body>"),   //  обозначение html файла
    DOC_END("</body></html>"),// конец файла
    PARAGRAPH_START("<p>"),         //  абзац
    PARAGRAPH_END("</p>"),
    TEXT("");   // просто текст

    String tag;
    LexemeType(String tag){
        this.tag=tag;
    }

}

