package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class WordUtilsTest_testWrap_StringIntStringBooleanString {

    private static final String LINE_SEPARATOR = "\n";
    private static final String SLASH_REGEX = "/";
    private static final String SLASH_SEPARATED_WORDS = "flammable/inflammable";
    private static final String UNBROKEN_WORDS = "flammableinflammable";

    @Test
    void testWrap_StringIntStringBooleanString() {
        assertWrapsOnSlash("flammable/inflammable", SLASH_SEPARATED_WORDS, 30, false);
        assertWrapsOnSlash("flammable\ninflammable", SLASH_SEPARATED_WORDS, 2, false);
        assertWrapsOnSlash("flammable\ninflammab\nle", SLASH_SEPARATED_WORDS, 9, true);
        assertWrapsOnSlash("flammable\ninflammable", SLASH_SEPARATED_WORDS, 15, true);
        assertWrapsOnSlash("flammableinflam\nmable", UNBROKEN_WORDS, 15, true);
    }

    private static void assertWrapsOnSlash(final String expected,
                                           final String input,
                                           final int wrapLength,
                                           final boolean wrapLongWords) {
        assertEquals(expected, WordUtils.wrap(input, wrapLength, LINE_SEPARATOR, wrapLongWords, SLASH_REGEX));
    }
}
