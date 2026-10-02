package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

/**
 * Tests for {@link WordUtils#wrap(String, int, String, boolean)}.
 *
 * <p>The four arguments are: the text to wrap, the column width to wrap at,
 * the new-line string to insert (a {@code null} value means the system line
 * separator), and whether words longer than the wrap width should themselves
 * be broken ({@code wrapLongWords}).</p>
 *
 * <p>Each test below isolates one behaviour so that a failure points directly
 * at the scenario that broke.</p>
 */
public class WordUtilsTest_testWrap_StringIntStringBoolean {

    /** A {@code null} input always wraps to {@code null}, regardless of the other arguments. */
    @Test
    void nullInputAlwaysReturnsNull() {
        assertNull(WordUtils.wrap(null, 20, "\n", false));
        assertNull(WordUtils.wrap(null, 20, "\n", true));
        assertNull(WordUtils.wrap(null, 20, null, true));
        assertNull(WordUtils.wrap(null, 20, null, false));
        assertNull(WordUtils.wrap(null, -1, null, true));
        assertNull(WordUtils.wrap(null, -1, null, false));
    }

    /** An empty input always wraps to an empty string, regardless of the other arguments. */
    @Test
    void emptyInputAlwaysReturnsEmptyString() {
        assertEquals("", WordUtils.wrap("", 20, "\n", false));
        assertEquals("", WordUtils.wrap("", 20, "\n", true));
        assertEquals("", WordUtils.wrap("", 20, null, false));
        assertEquals("", WordUtils.wrap("", 20, null, true));
        assertEquals("", WordUtils.wrap("", -1, null, false));
        assertEquals("", WordUtils.wrap("", -1, null, true));
    }

    /** A typical line is wrapped at the requested column; wrapLongWords has no effect when no word is too long. */
    @Test
    void wrapsTextAtRequestedColumn() {
        final String input = "Here is one line of text that is going to be wrapped after 20 columns.";
        final String expected = "Here is one line of\ntext that is going\nto be wrapped after\n20 columns.";

        assertEquals(expected, WordUtils.wrap(input, 20, "\n", false));
        assertEquals(expected, WordUtils.wrap(input, 20, "\n", true));
    }

    /** The new-line string can be any text, such as an HTML line break. */
    @Test
    void usesCustomNewLineString() {
        final String input = "Here is one line of text that is going to be wrapped after 20 columns.";
        final String expected = "Here is one line of<br />text that is going<br />to be wrapped after<br />20 columns.";

        assertEquals(expected, WordUtils.wrap(input, 20, "<br />", false));
        assertEquals(expected, WordUtils.wrap(input, 20, "<br />", true));
    }

    /** A small wrap width wraps after each word; widths below 1 are treated as 1. */
    @Test
    void wrapsAtShortAndNonPositiveWidths() {
        final String input = "Here is one line";

        assertEquals("Here\nis one\nline", WordUtils.wrap(input, 6, "\n", false));

        final String expectedPerWord = "Here\nis\none\nline";
        assertEquals(expectedPerWord, WordUtils.wrap(input, 2, "\n", false));
        assertEquals(expectedPerWord, WordUtils.wrap(input, -1, "\n", false));
    }

    /** A {@code null} new-line string falls back to the platform line separator. */
    @Test
    void nullNewLineStringUsesSystemLineSeparator() {
        final String systemNewLine = System.lineSeparator();
        final String input = "Here is one line of text that is going to be wrapped after 20 columns.";
        final String expected = "Here is one line of" + systemNewLine
                + "text that is going" + systemNewLine
                + "to be wrapped after" + systemNewLine
                + "20 columns.";

        assertEquals(expected, WordUtils.wrap(input, 20, null, false));
        assertEquals(expected, WordUtils.wrap(input, 20, null, true));
    }

    /** Runs of spaces are preserved; only leading spaces of a wrapped line are stripped. */
    @Test
    void preservesExtraSpaces() {
        final String input = " Here:  is  one  line  of  text  that  is  going  to  be  wrapped  after  20  columns.";
        final String expected = "Here:  is  one  line\nof  text  that  is \ngoing  to  be \nwrapped  after  20 \ncolumns.";

        assertEquals(expected, WordUtils.wrap(input, 20, "\n", false));
        assertEquals(expected, WordUtils.wrap(input, 20, "\n", true));
    }

    /** Tab characters are treated as ordinary content and counted toward the column width. */
    @Test
    void treatsTabAsContent() {
        final String input = "Here is\tone line of text that is going to be wrapped after 20 columns.";
        final String expected = "Here is\tone line of\ntext that is going\nto be wrapped after\n20 columns.";

        assertEquals(expected, WordUtils.wrap(input, 20, "\n", false));
        assertEquals(expected, WordUtils.wrap(input, 20, "\n", true));
    }

    /** A tab sitting exactly at the wrap column does not trigger a break on the tab. */
    @Test
    void treatsTabAtWrapColumnAsContent() {
        final String input = "Here is one line of\ttext that is going to be wrapped after 20 columns.";
        final String expected = "Here is one line\nof\ttext that is\ngoing to be wrapped\nafter 20 columns.";

        assertEquals(expected, WordUtils.wrap(input, 20, "\n", false));
        assertEquals(expected, WordUtils.wrap(input, 20, "\n", true));
    }

    /** A word longer than the wrap width is broken only when wrapLongWords is true. */
    @Test
    void wrapLongWordsControlsBreakingOfTrailingLongWord() {
        final String input = "Click here to jump to the commons website - https://commons.apache.org";

        final String keptIntact = "Click here to jump\nto the commons\nwebsite -\nhttps://commons.apache.org";
        assertEquals(keptIntact, WordUtils.wrap(input, 20, "\n", false));

        final String brokenUp = "Click here to jump\nto the commons\nwebsite -\nhttps://commons.apac\nhe.org";
        assertEquals(brokenUp, WordUtils.wrap(input, 20, "\n", true));
    }

    /** The same wrapLongWords behaviour applies to a long word in the middle of the text. */
    @Test
    void wrapLongWordsControlsBreakingOfMiddleLongWord() {
        final String input = "Click here, https://commons.apache.org, to jump to the commons website";

        final String keptIntact = "Click here,\nhttps://commons.apache.org,\nto jump to the\ncommons website";
        assertEquals(keptIntact, WordUtils.wrap(input, 20, "\n", false));

        final String brokenUp = "Click here,\nhttps://commons.apac\nhe.org, to jump to\nthe commons website";
        assertEquals(brokenUp, WordUtils.wrap(input, 20, "\n", true));
    }
}
