package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

public class StringTokenizerTest_testPreviousTokenAndSetEmptyTokenAsNull {

    private static final String TSV_INPUT_WITH_ONLY_EMPTY_TOKEN_CONTENT = " \t\n\r\f";

    @Test
    void testPreviousTokenAndSetEmptyTokenAsNull() {
        final StringTokenizer tokenizer = StringTokenizer.getTSVInstance(TSV_INPUT_WITH_ONLY_EMPTY_TOKEN_CONTENT);

        tokenizer.setEmptyTokenAsNull(true);

        assertNull(tokenizer.previousToken());
    }
}
