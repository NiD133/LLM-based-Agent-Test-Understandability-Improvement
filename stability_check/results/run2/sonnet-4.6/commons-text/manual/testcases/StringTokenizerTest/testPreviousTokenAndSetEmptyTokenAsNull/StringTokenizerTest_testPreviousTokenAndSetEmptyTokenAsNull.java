package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

public class StringTokenizerTest_testPreviousTokenAndSetEmptyTokenAsNull {

    /**
     * Verifies that calling previousToken() at the start of iteration returns null
     * even when emptyTokenAsNull is enabled. The iterator starts at position 0,
     * so hasPrevious() is false and previousToken() must return null regardless
     * of the emptyTokenAsNull setting.
     *
     * Input " \t\n\r\f" is whitespace-only; with TSV trimming all tokens collapse
     * to empty strings, making the emptyTokenAsNull interaction worth confirming.
     */
    @Test
    void testPreviousTokenAndSetEmptyTokenAsNull() {
        final StringTokenizer tokenizer = StringTokenizer.getTSVInstance(" \t\n\r\f");
        tokenizer.setEmptyTokenAsNull(true);

        // No tokens have been consumed yet, so there is no previous token.
        assertNull(tokenizer.previousToken());
    }
}
