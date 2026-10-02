package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class WordUtilsTest_testWrapAtMiddleTwice {

    @Test
    void testWrapAtMiddleTwice() {
        // "abcdefggabcdef" contains two consecutive 'g' characters in the middle.
        // The wrapOn regex "(?=g)" is a zero-width lookahead that matches just before
        // each 'g', so the string is split at both 'g' positions, producing two
        // consecutive newlines between the two halves.
        String input = "abcdefggabcdef";
        String wrapOnBeforeEachG = "(?=g)";
        String expected = "abcdef\n\nabcdef";

        assertEquals(expected, WordUtils.wrap(input, 2, "\n", false, wrapOnBeforeEachG));
    }
}
