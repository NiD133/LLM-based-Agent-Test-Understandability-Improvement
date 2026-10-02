package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class WordUtilsTest_testWrapWithMultipleRegexMatchOfLength0 {

    @Test
    void testWrapWithMultipleRegexMatchOfLength0() {
        final String textToWrap = "abcdefabcdef";
        final int wrapLength = 2;
        final String lineSeparator = "\n";
        final boolean wrapLongWords = false;
        final String zeroLengthRegexBeforeD = "(?=d)";

        assertEquals(
                "abc\ndefabc\ndef",
                WordUtils.wrap(textToWrap, wrapLength, lineSeparator, wrapLongWords, zeroLengthRegexBeforeD));
    }
}
