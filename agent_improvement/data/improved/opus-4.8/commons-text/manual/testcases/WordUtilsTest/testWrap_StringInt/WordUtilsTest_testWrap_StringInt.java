package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link WordUtils#wrap(String, int)}, which wraps text so that no line
 * exceeds the requested column width, breaking only at whitespace.
 */
public class WordUtilsTest_testWrap_StringInt {

    /** The platform line separator that {@code wrap} inserts between wrapped lines. */
    private static final String NEW_LINE = System.lineSeparator();

    @Test
    void returnsNullWhenInputIsNull() {
        assertNull(WordUtils.wrap(null, 20));
        assertNull(WordUtils.wrap(null, -1));
    }

    @Test
    void returnsEmptyStringWhenInputIsEmpty() {
        assertEquals("", WordUtils.wrap("", 20));
        assertEquals("", WordUtils.wrap("", -1));
    }

    @Test
    void wrapsPlainTextAtWordBoundaries() {
        final String input = "Here is one line of text that is going to be wrapped after 20 columns.";
        final String expected =
                  "Here is one line of" + NEW_LINE
                + "text that is going" + NEW_LINE
                + "to be wrapped after" + NEW_LINE
                + "20 columns.";

        assertEquals(expected, WordUtils.wrap(input, 20));
    }

    @Test
    void keepsWordLongerThanWrapLengthIntactAtEnd() {
        final String input = "Click here to jump to the commons website - https://commons.apache.org";
        final String expected =
                  "Click here to jump" + NEW_LINE
                + "to the commons" + NEW_LINE
                + "website -" + NEW_LINE
                + "https://commons.apache.org";

        assertEquals(expected, WordUtils.wrap(input, 20));
    }

    @Test
    void keepsWordLongerThanWrapLengthIntactInMiddle() {
        final String input = "Click here, https://commons.apache.org, to jump to the commons website";
        final String expected =
                  "Click here," + NEW_LINE
                + "https://commons.apache.org," + NEW_LINE
                + "to jump to the" + NEW_LINE
                + "commons website";

        assertEquals(expected, WordUtils.wrap(input, 20));
    }

    @Test
    void stripsLeadingSpacesOnNewLinesButKeepsTrailingSpaces() {
        final String input = "word1             word2                        word3";
        final String expected =
                  "word1  " + NEW_LINE
                + "word2  " + NEW_LINE
                + "word3";

        assertEquals(expected, WordUtils.wrap(input, 7));
    }
}
