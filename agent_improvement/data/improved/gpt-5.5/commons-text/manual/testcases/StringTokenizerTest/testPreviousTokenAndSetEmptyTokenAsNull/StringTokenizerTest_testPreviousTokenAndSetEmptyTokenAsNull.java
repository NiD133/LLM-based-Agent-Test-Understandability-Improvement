package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

public class StringTokenizerTest_testPreviousTokenAndSetEmptyTokenAsNull {

    private static final String WHITESPACE_ONLY_TSV_INPUT = " \t\n\r\f";

    @Test
    void testPreviousTokenAndSetEmptyTokenAsNull() {
        final StringTokenizer tokenizer = StringTokenizer.getTSVInstance(WHITESPACE_ONLY_TSV_INPUT);
        tokenizer.setEmptyTokenAsNull(true);

        assertNull(tokenizer.previousToken());
    }
}
