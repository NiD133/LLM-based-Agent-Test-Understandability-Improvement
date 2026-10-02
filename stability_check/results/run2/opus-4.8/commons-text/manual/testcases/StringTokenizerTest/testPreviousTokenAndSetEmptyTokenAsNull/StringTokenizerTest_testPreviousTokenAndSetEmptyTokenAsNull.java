package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

/**
 * Verifies {@link StringTokenizer#previousToken()} behavior when the tokenizer holds
 * no reachable previous token and empty tokens are configured to be returned as null.
 */
public class StringTokenizerTest_testPreviousTokenAndSetEmptyTokenAsNull {

    @Test
    void testPreviousTokenAndSetEmptyTokenAsNull() {
        // A TSV tokenizer whose input contains only whitespace (space, tab, newline,
        // carriage return, form feed) yields no tokens positioned before the cursor.
        final StringTokenizer tokenizer = StringTokenizer.getTSVInstance(" \t\n\r\f");
        tokenizer.setEmptyTokenAsNull(true);

        // With the cursor at the start, there is no previous token to return.
        assertNull(tokenizer.previousToken());
    }
}
