package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link WordUtils#wrap(String, int, String, boolean, String)} when the custom
 * break pattern matches twice in the input, producing two wrap points.
 */
public class WordUtilsTest_testWrapAtMiddleTwice {

    @Test
    void testWrapAtMiddleTwice() {
        // Wrap "abcdefggabcdef" using a look-ahead regex that breaks before every 'g'.
        // The two consecutive 'g's create two break points back to back, so the second
        // wrap yields an empty middle line between the two "abcdef" segments.
        final String input = "abcdefggabcdef";
        final int wrapLength = 2;
        final String newLine = "\n";
        final boolean wrapLongWords = false;
        final String breakBeforeG = "(?=g)";

        final String wrapped =
                WordUtils.wrap(input, wrapLength, newLine, wrapLongWords, breakBeforeG);

        assertEquals("abcdef\n\nabcdef", wrapped);
    }
}
