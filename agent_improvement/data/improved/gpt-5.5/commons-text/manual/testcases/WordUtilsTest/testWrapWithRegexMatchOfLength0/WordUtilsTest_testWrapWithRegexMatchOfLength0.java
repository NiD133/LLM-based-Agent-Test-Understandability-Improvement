package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class WordUtilsTest_testWrapWithRegexMatchOfLength0 {

    @Test
    void testWrapWithRegexMatchOfLength0() {
        final String input = "abcdef";
        final int wrapLength = 2;
        final String newLine = "\n";
        final boolean wrapLongWords = false;
        final String zeroLengthRegexBeforeD = "(?=d)";

        assertEquals("abc\ndef", WordUtils.wrap(input, wrapLength, newLine, wrapLongWords, zeroLengthRegexBeforeD));
    }
}
