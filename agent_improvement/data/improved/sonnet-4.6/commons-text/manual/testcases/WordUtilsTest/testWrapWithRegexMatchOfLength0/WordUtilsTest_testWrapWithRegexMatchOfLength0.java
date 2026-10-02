package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class WordUtilsTest_testWrapWithRegexMatchOfLength0 {

    @Test
    void testWrapWithRegexMatchOfLength0() {
        // "(?=d)" is a zero-length lookahead that matches the position just before 'd'.
        // The wrap splits "abcdef" at that position, producing "abc" and "def" on separate lines.
        String input       = "abcdef";
        int    wrapLength  = 2;
        String newLine     = "\n";
        boolean wrapLongWords = false;
        String wrapOnRegex = "(?=d)"; // zero-length match: position before 'd'

        String result = WordUtils.wrap(input, wrapLength, newLine, wrapLongWords, wrapOnRegex);

        assertEquals("abc\ndef", result);
    }
}
