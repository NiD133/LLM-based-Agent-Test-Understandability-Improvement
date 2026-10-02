package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

public class StringTokenizerTest_testBasicQuoted2 {

    private static final String INPUT_WITH_QUOTED_MIDDLE_TOKEN_AND_TRAILING_DELIMITER = "a:'b':";
    private static final char TOKEN_DELIMITER = ':';
    private static final char QUOTE_CHARACTER = '\'';

    @Test
    void testBasicQuoted2() {
        final StringTokenizer tokenizer = new StringTokenizer(
                INPUT_WITH_QUOTED_MIDDLE_TOKEN_AND_TRAILING_DELIMITER,
                TOKEN_DELIMITER,
                QUOTE_CHARACTER);

        tokenizer.setIgnoreEmptyTokens(false);
        tokenizer.setEmptyTokenAsNull(true);

        assertEquals("a", tokenizer.next());
        assertEquals("b", tokenizer.next());
        assertNull(tokenizer.next());
        assertFalse(tokenizer.hasNext());
    }
}
