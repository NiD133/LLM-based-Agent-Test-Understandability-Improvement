package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

public class WordUtilsTest_testWrap_StringInt {

    private static final String NL = System.lineSeparator();

    @Test
    void testWrap_nullInput_returnsNull() {
        assertNull(WordUtils.wrap(null, 20));
        assertNull(WordUtils.wrap(null, -1));
    }

    @Test
    void testWrap_emptyInput_returnsEmpty() {
        assertEquals("", WordUtils.wrap("", 20));
        assertEquals("", WordUtils.wrap("", -1));
    }

    @Test
    void testWrap_normalText_wrapsAtColumnBoundary() {
        String input = "Here is one line of text that is going to be wrapped after 20 columns.";
        String expected = "Here is one line of" + NL
                + "text that is going" + NL
                + "to be wrapped after" + NL
                + "20 columns.";
        assertEquals(expected, WordUtils.wrap(input, 20));
    }

    @Test
    void testWrap_longWordAtEnd_isNotWrapped() {
        String input = "Click here to jump to the commons website - https://commons.apache.org";
        String expected = "Click here to jump" + NL
                + "to the commons" + NL
                + "website -" + NL
                + "https://commons.apache.org";
        assertEquals(expected, WordUtils.wrap(input, 20));
    }

    @Test
    void testWrap_longWordInMiddle_isNotWrapped() {
        String input = "Click here, https://commons.apache.org, to jump to the commons website";
        String expected = "Click here," + NL
                + "https://commons.apache.org," + NL
                + "to jump to the" + NL
                + "commons website";
        assertEquals(expected, WordUtils.wrap(input, 20));
    }

    @Test
    void testWrap_multipleSpacesBetweenWords_leadingSpacesStrippedTrailingPreserved() {
        // Leading spaces on a new line are stripped; trailing spaces are not stripped.
        String input = "word1             word2                        word3";
        String expected = "word1  " + NL
                + "word2  " + NL
                + "word3";
        assertEquals(expected, WordUtils.wrap(input, 7));
    }
}
