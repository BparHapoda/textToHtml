package org.example;

import javax.annotation.processing.FilerException;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

/**
 * Реализовать транслитерацию текста в заданном формате разметки в формат разметки HTML.
 * <p>
 * Во входном файле с именем inputName содержится текст, содержащий в себе элементы текстовой разметки следующих типов:
 * - *текст в курсивном начертании* -- курсив
 * - **текст в полужирном начертании** -- полужирный
 * - ~~зачёркнутый текст~~ -- зачёркивание
 * <p>
 * Следует вывести в выходной файл этот же текст в формате HTML:
 * - <i>текст в курсивном начертании</i>
 * - <b>текст в полужирном начертании</b>
 * - <s>зачёркнутый текст</s>
 * <p>
 * Кроме того, все абзацы исходного текста, отделённые друг от друга пустыми строками, следует обернуть в теги <p>...</p>,
 * а весь текст целиком в теги <html><body>...</body></html>.
 * <p>
 * Все остальные части исходного текста должны остаться неизменными с точностью до наборов пробелов и переносов строк.
 * Отдельно следует заметить, что открывающая последовательность из трёх звёздочек (***) должна трактоваться как "<b><i>"
 * и никак иначе.
 * <p>
 * При решении этой и двух следующих задач полезно прочитать статью Википедии "Стек".
 * <p>
 * Пример входного файла:
 * Lorem ipsum *dolor sit amet*, consectetur **adipiscing** elit.
 * Vestibulum lobortis, ~~Est vehicula rutrum *suscipit*~~, ipsum ~~lib~~ero *placerat **tortor***,
 * Suspendisse ~~et elit in enim tempus iaculis~~.
 * <p>
 * Соответствующий выходной файл:
 * <html>
 * <body>
 * <p>
 * Lorem ipsum <i>dolor sit amet</i>, consectetur <b>adipiscing</b> elit.
 * Vestibulum lobortis. <s>Est vehicula rutrum <i>suscipit</i></s>, ipsum <s>lib</s>ero <i>placerat <b>tortor</b></i>.
 * </p>
 * <p>
 * Suspendisse <s>et elit in enim tempus iaculis</s>.
 * </p>
 * </body>
 * </html>
 * <p>
 * (Отступы и переносы строк в примере добавлены для наглядности, при решении задачи их реализовывать не обязательно)
 **/
public class Main {
    public static void main(String[] args) {
       analyser( readFile());

    }

    public static String readFile() {

        String fileName = "inputText";
        File file = new File(fileName);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            FileWriter fileWriter = null;
            try {
                fileWriter = new FileWriter(file);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            try {
                fileWriter.write("Lorem ipsum *dolor sit amet*, consectetur **adipiscing** elit.\n" +
                        "Vestibulum lobortis, ~~Est vehicula rutrum *suscipit*~~, " +
                        "ipsum ~~lib~~ero *placerat **tortor***,\n" +
                        " Suspendisse ~~et elit in enim tempus iaculis~~.");
                fileWriter.flush();
                fileWriter.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        String string = null;
        StringBuilder stringBuilder = new StringBuilder();
        try (FileReader fileReader = new FileReader(file)) {
            int symbol;
            while ((symbol = fileReader.read()) != -1)
            {
            stringBuilder.append((char) symbol);}
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return stringBuilder.toString();
    }
    public static void analyser(String text){
        ArrayList<Lexeme> lexemes =new ArrayList<>();
        lexemes.add(new Lexeme(LexemeType.DOC_START));
        int index = 0 ;

        boolean bold=false;
        boolean coursive=false;
        boolean cross=false;
        boolean paragraph=false;

        StringBuilder stringBuilder =new StringBuilder();
        while (index<text.length()-1){
        char c = text.charAt(index);
        switch (c){
            case ('*'):
                if(text.charAt(index+1)=='*' & text.charAt(index+2)=='*')
                {
                    lexemes.add(new Lexeme(LexemeType.CROSS_BOLD_END));
                    index=index+3;
                    bold=false;
                    coursive=false;
                    continue;
                }
                else if(text.charAt(index+1)=='*')
                {
                    if(!bold){
                    lexemes.add(new Lexeme(LexemeType.BOLD_START));
                        bold=true;
                    }
                    else
                    {
                        lexemes.add(new Lexeme(LexemeType.BOLD_END));
                        bold=false;
                    }
                    index=index+2;
                    continue;
                }
                else
                {
                    if(!coursive){
                        lexemes.add(new Lexeme(LexemeType.COURSIVE_START));
                        coursive=true;
                    }
                    else
                    {
                        lexemes.add(new Lexeme(LexemeType.COURSIVE_END));
                        coursive=false;
                    }
                }
                index++;
                continue;

            case('~'):
            {
                if (text.charAt(index + 1)=='~') {
                    if(!cross)
                    {lexemes.add(new Lexeme(LexemeType.CROSS_START));
                        cross=true;
                    index=index+2;
                    continue;
                    }
                    else {
                        lexemes.add(new Lexeme(LexemeType.CROSS_END));
                        cross=false;
                        index=index+2;
                            continue;
                    }
                }
                }


        }

            boolean exp=text.charAt(index)=='*'||
                    text.charAt(index)=='~'||
                    text.charAt(index)=='\n';
stringBuilder.delete(0,stringBuilder.length());
        if(!exp)
{
    while (true)
    {stringBuilder.append(text.charAt(index));
      index++;
       if (text.charAt(index)=='*'||
               text.charAt(index)=='~'||
               text.charAt(index)=='\n'){break;}
    }


}
if (stringBuilder.length()==0){index++;}
else {lexemes.add(new Lexeme(LexemeType.TEXT));
lexemes.get(lexemes.size()-1).setText(stringBuilder.toString());}
        }
        lexemes.add(new Lexeme(LexemeType.DOC_END));
        System.out.println(text);
File output = new File("output.html");
        FileWriter fileWriter= null;
        try {
            fileWriter = new FileWriter(output);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        for (Lexeme lexeme:lexemes
             ) {
            try {
                fileWriter.write(lexeme.getText());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        try {
            fileWriter.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            fileWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}