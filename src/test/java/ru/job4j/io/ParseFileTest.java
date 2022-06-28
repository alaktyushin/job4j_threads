package ru.job4j.io;

import org.junit.Test;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import java.io.File;
import java.nio.file.Path;

public class ParseFileTest {

    @Test
    public void getAllContent() {
        File file = Path.of("./data/Job4j.html").toFile();
        ParseFile parseFile = new ParseFile(file);
        String expected = """
                <!DOCTYPE html>
                <p>2. Ð\u009FÐ¾Ð¿Ñ\u0080Ð°Ð²Ñ\u008CÑ\u0082Ðµ ÐºÐ¾Ð´ Ñ\u0081 Ð¾Ñ\u0088Ð¸Ð±ÐºÐ°Ð¼Ð¸ Ð² ÐºÐ¾Ð´Ðµ.</p>
                <p>4. Ð\u009FÐµÑ\u0080ÐµÐ²ÐµÐ´Ð¸Ñ\u0082Ðµ Ð¾Ñ\u0082Ð²ÐµÑ\u0082Ñ\u0081Ñ\u0082Ð²ÐµÐ½Ð½Ð¾Ð³Ð¾ Ð½Ð° Ð\u009FÐµÑ\u0082Ñ\u0080Ð° Ð\u0090Ñ\u0080Ñ\u0081ÐµÐ½Ñ\u0082Ñ\u008CÐµÐ²Ð°.</p>
                """;
        assertThat(parseFile.getContent(charset -> true), is(expected));
    }

    @Test
    public void getNonUnicodeContent() {
        File file = Path.of("./data/Job4j.html").toFile();
        ParseFile parseFile = new ParseFile(file);
        String expected = """
                <!DOCTYPE html>
                <p>2.      .</p>
                <p>4.     .</p>
                """;
        assertThat(parseFile.getContent(charset -> charset < 0x80), is(expected));
    }

    @Test
    public void saveContent() {
        ParseFile parseFile = new ParseFile(Path.of("./data/Job4j.html").toFile());
        SaveToFile save = new SaveToFile(Path.of("./data/save.html").toFile());
        save.saveContent(parseFile.getContent(c -> true));
    }
}
