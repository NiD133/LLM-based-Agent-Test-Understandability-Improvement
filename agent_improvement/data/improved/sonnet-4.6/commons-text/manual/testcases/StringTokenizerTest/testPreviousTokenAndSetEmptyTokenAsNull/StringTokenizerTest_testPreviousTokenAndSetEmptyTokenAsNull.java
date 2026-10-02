package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

public class StringTokenizerTest_testPreviousTokenAndSetEmptyTokenAsNull {

    @Test
    void testPreviousTokenAndSetEmptyTokenAsNull() {
        // A TSV tokenizer over whitespace-only input has no tokens.
        // With emptyTokenAsNull enabled, previousToken() must return null
        // because there is no preceding token to return.
        final StringTokenizer strTokenizer = StringTokenizer.getTSVInstance(" \t\n\r\f");
        strTokenizer.setEmptyTokenAsNull(true);
        assertNull(strTokenizer.previousToken());
    }
}
