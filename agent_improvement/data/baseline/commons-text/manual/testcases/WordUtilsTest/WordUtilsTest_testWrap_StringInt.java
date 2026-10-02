package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTimeout;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.time.Duration;
import java.util.stream.IntStream;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

public class WordUtilsTest_testWrap_StringInt {

    private static final String WHITESPACE = IntStream.rangeClosed(Character.MIN_CODE_POINT, Character.MAX_CODE_POINT).filter(Character::isWhitespace).collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();

    @Test
    void testWrap_StringInt() {
        assertNull(WordUtils.wrap(null, 20));
        assertNull(WordUtils.wrap(null, -1));
        assertEquals("", WordUtils.wrap("", 20));
        assertEquals("", WordUtils.wrap("", -1));
        // normal
        final String systemNewLine = System.lineSeparator();
        String input = "Here is one line of text that is going to be wrapped after 20 columns.";
        String expected = "Here is one line of" + systemNewLine + "text that is going" + systemNewLine + "to be wrapped after" + systemNewLine + "20 columns.";
        assertEquals(expected, WordUtils.wrap(input, 20));
        // long word at end
        input = "Click here to jump to the commons website - https://commons.apache.org";
        expected = "Click here to jump" + systemNewLine + "to the commons" + systemNewLine + "website -" + systemNewLine + "https://commons.apache.org";
        assertEquals(expected, WordUtils.wrap(input, 20));
        // long word in middle
        input = "Click here, https://commons.apache.org, to jump to the commons website";
        expected = "Click here," + systemNewLine + "https://commons.apache.org," + systemNewLine + "to jump to the" + systemNewLine + "commons website";
        assertEquals(expected, WordUtils.wrap(input, 20));
        // leading spaces on a new line are stripped
        // trailing spaces are not stripped
        input = "word1             word2                        word3";
        expected = "word1  " + systemNewLine + "word2  " + systemNewLine + "word3";
        assertEquals(expected, WordUtils.wrap(input, 7));
    }
}
