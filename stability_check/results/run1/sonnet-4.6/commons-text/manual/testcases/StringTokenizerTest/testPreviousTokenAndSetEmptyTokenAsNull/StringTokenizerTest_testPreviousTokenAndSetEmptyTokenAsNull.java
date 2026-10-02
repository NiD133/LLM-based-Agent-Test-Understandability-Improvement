package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

public class StringTokenizerTest_testPreviousTokenAndSetEmptyTokenAsNull {

    @Test
    void testPreviousTokenAndSetEmptyTokenAsNull() {
        // TSV tokenizer on whitespace-only input: all tokens are empty.
        // With emptyTokenAsNull=true, empty tokens map to null.
        // Calling previousToken() before any forward iteration (tokenPos == 0)
        // means hasPrevious() is false, so previousToken() must return null.
        final StringTokenizer strTokenizer = StringTokenizer.getTSVInstance(" \t\n\r\f");
        strTokenizer.setEmptyTokenAsNull(true);
        assertNull(strTokenizer.previousToken());
    }
}
