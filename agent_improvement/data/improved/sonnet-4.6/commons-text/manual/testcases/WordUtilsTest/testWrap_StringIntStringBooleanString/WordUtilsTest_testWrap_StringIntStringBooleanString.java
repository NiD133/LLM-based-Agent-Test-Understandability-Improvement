package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class WordUtilsTest_testWrap_StringIntStringBooleanString {

    private static final String SLASH = "/";
    private static final String NEWLINE = "\n";

    /**
     * When the wrap column is wider than the entire input, no wrapping occurs.
     */
    @Test
    void wrap_wrapColumnWiderThanInput_returnsUnchanged() {
        String input = "flammable/inflammable";
        String expected = "flammable/inflammable";
        assertEquals(expected, WordUtils.wrap(input, 30, NEWLINE, false, SLASH));
    }

    /**
     * When the wrap column is very small, the string is split at the '/' delimiter.
     */
    @Test
    void wrap_smallWrapColumn_splitsAtDelimiter() {
        String input = "flammable/inflammable";
        String expected = "flammable\ninflammable";
        assertEquals(expected, WordUtils.wrap(input, 2, NEWLINE, false, SLASH));
    }

    /**
     * When wrapLongWords is true and a word exceeds the column, it is broken mid-word.
     */
    @Test
    void wrap_longWordExceedsColumn_breaksLongWord() {
        String input = "flammable/inflammable";
        String expected = "flammable\ninflammab\nle";
        assertEquals(expected, WordUtils.wrap(input, 9, NEWLINE, true, SLASH));
    }

    /**
     * When wrapLongWords is true but the column is wide enough for each segment, no mid-word break occurs.
     */
    @Test
    void wrap_longWordFitsWithinColumn_doesNotBreakMidWord() {
        String input = "flammable/inflammable";
        String expected = "flammable\ninflammable";
        assertEquals(expected, WordUtils.wrap(input, 15, NEWLINE, true, SLASH));
    }

    /**
     * When the entire input has no delimiter, a long word is broken mid-word at the column boundary.
     */
    @Test
    void wrap_noDelimiterInInput_breaksLongWordAtColumnBoundary() {
        String input = "flammableinflammable";
        String expected = "flammableinflam\nmable";
        assertEquals(expected, WordUtils.wrap(input, 15, NEWLINE, true, SLASH));
    }
}
