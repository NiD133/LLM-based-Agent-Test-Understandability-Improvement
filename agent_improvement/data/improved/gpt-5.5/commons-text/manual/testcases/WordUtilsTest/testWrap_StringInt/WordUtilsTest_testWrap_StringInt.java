package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

public class WordUtilsTest_testWrap_StringInt {

    @Test
    void testWrap_StringInt() {
        assertNullInputsRemainNull();
        assertEmptyInputsRemainEmpty();
        assertNormalTextWrapsOnWordBoundaries();
        assertLongWordAtEndStartsOnItsOwnLine();
        assertLongWordInMiddleStartsOnItsOwnLine();
        assertLeadingSpacesAreStrippedFromWrappedLines();
    }

    private void assertNullInputsRemainNull() {
        assertNull(WordUtils.wrap(null, 20));
        assertNull(WordUtils.wrap(null, -1));
    }

    private void assertEmptyInputsRemainEmpty() {
        assertEquals("", WordUtils.wrap("", 20));
        assertEquals("", WordUtils.wrap("", -1));
    }

    private void assertNormalTextWrapsOnWordBoundaries() {
        final String systemNewLine = System.lineSeparator();
        final String input = "Here is one line of text that is going to be wrapped after 20 columns.";
        final String expected = "Here is one line of" + systemNewLine
                + "text that is going" + systemNewLine
                + "to be wrapped after" + systemNewLine
                + "20 columns.";

        assertEquals(expected, WordUtils.wrap(input, 20));
    }

    private void assertLongWordAtEndStartsOnItsOwnLine() {
        final String systemNewLine = System.lineSeparator();
        final String input = "Click here to jump to the commons website - https://commons.apache.org";
        final String expected = "Click here to jump" + systemNewLine
                + "to the commons" + systemNewLine
                + "website -" + systemNewLine
                + "https://commons.apache.org";

        assertEquals(expected, WordUtils.wrap(input, 20));
    }

    private void assertLongWordInMiddleStartsOnItsOwnLine() {
        final String systemNewLine = System.lineSeparator();
        final String input = "Click here, https://commons.apache.org, to jump to the commons website";
        final String expected = "Click here," + systemNewLine
                + "https://commons.apache.org," + systemNewLine
                + "to jump to the" + systemNewLine
                + "commons website";

        assertEquals(expected, WordUtils.wrap(input, 20));
    }

    private void assertLeadingSpacesAreStrippedFromWrappedLines() {
        final String systemNewLine = System.lineSeparator();
        final String input = "word1             word2                        word3";
        final String expected = "word1  " + systemNewLine
                + "word2  " + systemNewLine
                + "word3";

        assertEquals(expected, WordUtils.wrap(input, 7));
    }
}
