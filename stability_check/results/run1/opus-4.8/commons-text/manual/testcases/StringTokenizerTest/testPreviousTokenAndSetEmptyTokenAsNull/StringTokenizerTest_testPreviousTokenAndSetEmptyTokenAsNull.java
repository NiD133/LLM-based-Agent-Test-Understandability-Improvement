package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

public class StringTokenizerTest_testPreviousTokenAndSetEmptyTokenAsNull {

    /**
     * When a TSV tokenizer is built from input that contains only whitespace
     * (space, tab, newline, carriage return, form feed) and empty tokens are
     * configured to be reported as {@code null}, calling {@code previousToken()}
     * before any token has been read must return {@code null}.
     */
    @Test
    void testPreviousTokenAndSetEmptyTokenAsNull() {
        final String whitespaceOnlyInput = " \t\n\r\f";
        final StringTokenizer tokenizer = StringTokenizer.getTSVInstance(whitespaceOnlyInput);
        tokenizer.setEmptyTokenAsNull(true);

        assertNull(tokenizer.previousToken());
    }
}
