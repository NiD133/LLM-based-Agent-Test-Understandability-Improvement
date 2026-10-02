package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

public class WordUtilsTest_testWrap_StringIntStringBoolean {

    @Test
    void testWrap_nullInputReturnsNull() {
        assertNull(WordUtils.wrap(null, 20, "\n", false));
        assertNull(WordUtils.wrap(null, 20, "\n", true));
        assertNull(WordUtils.wrap(null, 20, null, true));
        assertNull(WordUtils.wrap(null, 20, null, false));
        assertNull(WordUtils.wrap(null, -1, null, true));
        assertNull(WordUtils.wrap(null, -1, null, false));
    }

    @Test
    void testWrap_emptyInputReturnsEmpty() {
        assertEquals("", WordUtils.wrap("", 20, "\n", false));
        assertEquals("", WordUtils.wrap("", 20, "\n", true));
        assertEquals("", WordUtils.wrap("", 20, null, false));
        assertEquals("", WordUtils.wrap("", 20, null, true));
        assertEquals("", WordUtils.wrap("", -1, null, false));
        assertEquals("", WordUtils.wrap("", -1, null, true));
    }

    @Test
    void testWrap_normalTextAt20Columns() {
        String input = "Here is one line of text that is going to be wrapped after 20 columns.";
        String expected = "Here is one line of\ntext that is going\nto be wrapped after\n20 columns.";

        assertEquals(expected, WordUtils.wrap(input, 20, "\n", false));
        assertEquals(expected, WordUtils.wrap(input, 20, "\n", true));
    }

    @Test
    void testWrap_customHtmlNewlineSeparator() {
        String input = "Here is one line of text that is going to be wrapped after 20 columns.";
        String expected = "Here is one line of<br />text that is going<br />to be wrapped after<br />20 columns.";

        assertEquals(expected, WordUtils.wrap(input, 20, "<br />", false));
        assertEquals(expected, WordUtils.wrap(input, 20, "<br />", true));
    }

    @Test
    void testWrap_shortLineLengthForcesEarlyBreaks() {
        String input = "Here is one line";

        assertEquals("Here\nis one\nline", WordUtils.wrap(input, 6, "\n", false));

        String expectedWhenVeryShort = "Here\nis\none\nline";
        assertEquals(expectedWhenVeryShort, WordUtils.wrap(input, 2, "\n", false));
        assertEquals(expectedWhenVeryShort, WordUtils.wrap(input, -1, "\n", false));
    }

    @Test
    void testWrap_nullNewlineStringUsesSystemLineSeparator() {
        String input = "Here is one line of text that is going to be wrapped after 20 columns.";
        String sep = System.lineSeparator();
        String expected = "Here is one line of" + sep + "text that is going" + sep
                + "to be wrapped after" + sep + "20 columns.";

        assertEquals(expected, WordUtils.wrap(input, 20, null, false));
        assertEquals(expected, WordUtils.wrap(input, 20, null, true));
    }

    @Test
    void testWrap_extraSpacesInInputArePreserved() {
        String input = " Here:  is  one  line  of  text  that  is  going  to  be  wrapped  after  20  columns.";
        String expected = "Here:  is  one  line\nof  text  that  is \ngoing  to  be \nwrapped  after  20 \ncolumns.";

        assertEquals(expected, WordUtils.wrap(input, 20, "\n", false));
        assertEquals(expected, WordUtils.wrap(input, 20, "\n", true));
    }

    @Test
    void testWrap_tabCharacterWithinLine() {
        String input = "Here is\tone line of text that is going to be wrapped after 20 columns.";
        String expected = "Here is\tone line of\ntext that is going\nto be wrapped after\n20 columns.";

        assertEquals(expected, WordUtils.wrap(input, 20, "\n", false));
        assertEquals(expected, WordUtils.wrap(input, 20, "\n", true));
    }

    @Test
    void testWrap_tabCharacterAtWrapColumn() {
        String input = "Here is one line of\ttext that is going to be wrapped after 20 columns.";
        String expected = "Here is one line\nof\ttext that is\ngoing to be wrapped\nafter 20 columns.";

        assertEquals(expected, WordUtils.wrap(input, 20, "\n", false));
        assertEquals(expected, WordUtils.wrap(input, 20, "\n", true));
    }

    @Test
    void testWrap_longWordAtEndIsNotBrokenByDefault() {
        String input = "Click here to jump to the commons website - https://commons.apache.org";

        String expectedNoBreak = "Click here to jump\nto the commons\nwebsite -\nhttps://commons.apache.org";
        assertEquals(expectedNoBreak, WordUtils.wrap(input, 20, "\n", false));

        String expectedWithBreak = "Click here to jump\nto the commons\nwebsite -\nhttps://commons.apac\nhe.org";
        assertEquals(expectedWithBreak, WordUtils.wrap(input, 20, "\n", true));
    }

    @Test
    void testWrap_longWordInMiddleIsNotBrokenByDefault() {
        String input = "Click here, https://commons.apache.org, to jump to the commons website";

        String expectedNoBreak = "Click here,\nhttps://commons.apache.org,\nto jump to the\ncommons website";
        assertEquals(expectedNoBreak, WordUtils.wrap(input, 20, "\n", false));

        String expectedWithBreak = "Click here,\nhttps://commons.apac\nhe.org, to jump to\nthe commons website";
        assertEquals(expectedWithBreak, WordUtils.wrap(input, 20, "\n", true));
    }
}
