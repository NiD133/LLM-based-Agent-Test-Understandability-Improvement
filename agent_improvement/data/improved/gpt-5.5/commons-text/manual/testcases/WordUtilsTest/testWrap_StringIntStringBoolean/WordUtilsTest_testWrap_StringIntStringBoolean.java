package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

public class WordUtilsTest_testWrap_StringIntStringBoolean {

    private static final String LONG_TEXT = "Here is one line of text that is going to be wrapped after 20 columns.";
    private static final String LONG_TEXT_WRAPPED_AT_20 = "Here is one line of\n"
            + "text that is going\n"
            + "to be wrapped after\n"
            + "20 columns.";
    private static final String URL_AT_END = "Click here to jump to the commons website - https://commons.apache.org";
    private static final String URL_IN_MIDDLE = "Click here, https://commons.apache.org, to jump to the commons website";

    @Test
    void testWrap_StringIntStringBoolean() {
        assertNullInputAlwaysReturnsNull();
        assertEmptyInputAlwaysReturnsEmptyString();
        assertWrapsNormalTextAtWordBoundaries();
        assertUsesCustomNewLineString();
        assertWrapsShortLines();
        assertUsesSystemLineSeparatorWhenNewLineStringIsNull();
        assertHandlesExtraSpaces();
        assertHandlesTabs();
        assertTreatsLongWordsAccordingToWrapLongWordsFlag();
    }

    private void assertNullInputAlwaysReturnsNull() {
        assertNull(WordUtils.wrap(null, 20, "\n", false));
        assertNull(WordUtils.wrap(null, 20, "\n", true));
        assertNull(WordUtils.wrap(null, 20, null, true));
        assertNull(WordUtils.wrap(null, 20, null, false));
        assertNull(WordUtils.wrap(null, -1, null, true));
        assertNull(WordUtils.wrap(null, -1, null, false));
    }

    private void assertEmptyInputAlwaysReturnsEmptyString() {
        assertEquals("", WordUtils.wrap("", 20, "\n", false));
        assertEquals("", WordUtils.wrap("", 20, "\n", true));
        assertEquals("", WordUtils.wrap("", 20, null, false));
        assertEquals("", WordUtils.wrap("", 20, null, true));
        assertEquals("", WordUtils.wrap("", -1, null, false));
        assertEquals("", WordUtils.wrap("", -1, null, true));
    }

    private void assertWrapsNormalTextAtWordBoundaries() {
        assertEquals(LONG_TEXT_WRAPPED_AT_20, WordUtils.wrap(LONG_TEXT, 20, "\n", false));
        assertEquals(LONG_TEXT_WRAPPED_AT_20, WordUtils.wrap(LONG_TEXT, 20, "\n", true));
    }

    private void assertUsesCustomNewLineString() {
        final String expected = "Here is one line of<br />"
                + "text that is going<br />"
                + "to be wrapped after<br />"
                + "20 columns.";

        assertEquals(expected, WordUtils.wrap(LONG_TEXT, 20, "<br />", false));
        assertEquals(expected, WordUtils.wrap(LONG_TEXT, 20, "<br />", true));
    }

    private void assertWrapsShortLines() {
        final String input = "Here is one line";
        final String wrappedAtSix = "Here\nis one\nline";
        final String wrappedAtTwo = "Here\nis\none\nline";

        assertEquals(wrappedAtSix, WordUtils.wrap(input, 6, "\n", false));
        assertEquals(wrappedAtTwo, WordUtils.wrap(input, 2, "\n", false));
        assertEquals(wrappedAtTwo, WordUtils.wrap(input, -1, "\n", false));
    }

    private void assertUsesSystemLineSeparatorWhenNewLineStringIsNull() {
        final String systemNewLine = System.lineSeparator();
        final String expected = "Here is one line of" + systemNewLine
                + "text that is going" + systemNewLine
                + "to be wrapped after" + systemNewLine
                + "20 columns.";

        assertEquals(expected, WordUtils.wrap(LONG_TEXT, 20, null, false));
        assertEquals(expected, WordUtils.wrap(LONG_TEXT, 20, null, true));
    }

    private void assertHandlesExtraSpaces() {
        final String input = " Here:  is  one  line  of  text  that  is  going  to  be  wrapped  after  20  columns.";
        final String expected = "Here:  is  one  line\n"
                + "of  text  that  is \n"
                + "going  to  be \n"
                + "wrapped  after  20 \n"
                + "columns.";

        assertEquals(expected, WordUtils.wrap(input, 20, "\n", false));
        assertEquals(expected, WordUtils.wrap(input, 20, "\n", true));
    }

    private void assertHandlesTabs() {
        assertTabsBeforeWrapColumn();
        assertTabsAtWrapColumn();
    }

    private void assertTabsBeforeWrapColumn() {
        final String input = "Here is\tone line of text that is going to be wrapped after 20 columns.";
        final String expected = "Here is\tone line of\n"
                + "text that is going\n"
                + "to be wrapped after\n"
                + "20 columns.";

        assertEquals(expected, WordUtils.wrap(input, 20, "\n", false));
        assertEquals(expected, WordUtils.wrap(input, 20, "\n", true));
    }

    private void assertTabsAtWrapColumn() {
        final String input = "Here is one line of\ttext that is going to be wrapped after 20 columns.";
        final String expected = "Here is one line\n"
                + "of\ttext that is\n"
                + "going to be wrapped\n"
                + "after 20 columns.";

        assertEquals(expected, WordUtils.wrap(input, 20, "\n", false));
        assertEquals(expected, WordUtils.wrap(input, 20, "\n", true));
    }

    private void assertTreatsLongWordsAccordingToWrapLongWordsFlag() {
        assertLongWordAtEnd();
        assertLongWordInMiddle();
    }

    private void assertLongWordAtEnd() {
        final String expectedWithoutBreakingLongWords = "Click here to jump\n"
                + "to the commons\n"
                + "website -\n"
                + "https://commons.apache.org";
        final String expectedWithBreakingLongWords = "Click here to jump\n"
                + "to the commons\n"
                + "website -\n"
                + "https://commons.apac\n"
                + "he.org";

        assertEquals(expectedWithoutBreakingLongWords, WordUtils.wrap(URL_AT_END, 20, "\n", false));
        assertEquals(expectedWithBreakingLongWords, WordUtils.wrap(URL_AT_END, 20, "\n", true));
    }

    private void assertLongWordInMiddle() {
        final String expectedWithoutBreakingLongWords = "Click here,\n"
                + "https://commons.apache.org,\n"
                + "to jump to the\n"
                + "commons website";
        final String expectedWithBreakingLongWords = "Click here,\n"
                + "https://commons.apac\n"
                + "he.org, to jump to\n"
                + "the commons website";

        assertEquals(expectedWithoutBreakingLongWords, WordUtils.wrap(URL_IN_MIDDLE, 20, "\n", false));
        assertEquals(expectedWithBreakingLongWords, WordUtils.wrap(URL_IN_MIDDLE, 20, "\n", true));
    }
}
