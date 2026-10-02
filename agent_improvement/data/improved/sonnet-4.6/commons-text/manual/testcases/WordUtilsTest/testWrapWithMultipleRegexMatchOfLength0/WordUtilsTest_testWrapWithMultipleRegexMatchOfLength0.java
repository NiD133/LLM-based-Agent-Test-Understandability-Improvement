package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class WordUtilsTest_testWrapWithMultipleRegexMatchOfLength0 {

    private static final String INPUT          = "abcdefabcdef";
    private static final int    WRAP_LENGTH    = 2;
    private static final String NEW_LINE       = "\n";
    // Zero-width lookahead: matches the position before every 'd', producing zero-length matches
    private static final String WRAP_ON_REGEX  = "(?=d)";
    private static final String EXPECTED       = "abc\ndefabc\ndef";

    @Test
    void testWrapWithMultipleRegexMatchOfLength0() {
        assertEquals(EXPECTED, WordUtils.wrap(INPUT, WRAP_LENGTH, NEW_LINE, false, WRAP_ON_REGEX));
    }
}
