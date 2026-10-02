package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

public class StringTokenizerTest_testPreviousTokenAndSetEmptyTokenAsNull {

    /**
     * When a tokenizer is configured to treat empty tokens as {@code null},
     * asking for the previous token at the start of an all-delimiter input
     * yields {@code null} rather than an empty string.
     */
    @Test
    void testPreviousTokenAndSetEmptyTokenAsNull() {
        // Input made up entirely of whitespace delimiters (no real tokens).
        final StringTokenizer tokenizer = StringTokenizer.getTSVInstance(" \t\n\r\f");
        tokenizer.setEmptyTokenAsNull(true);

        assertNull(tokenizer.previousToken());
    }
}
