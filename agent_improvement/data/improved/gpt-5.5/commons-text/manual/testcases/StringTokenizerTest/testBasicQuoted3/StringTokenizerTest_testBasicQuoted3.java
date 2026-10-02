package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

public class StringTokenizerTest_testBasicQuoted3 {

    private static final String INPUT_WITH_ESCAPED_QUOTE = "a:'b''c'";
    private static final char FIELD_DELIMITER = ':';
    private static final char QUOTE_CHARACTER = '\'';

    @Test
    void testBasicQuoted3() {
        final StringTokenizer tokenizer = new StringTokenizer(INPUT_WITH_ESCAPED_QUOTE, FIELD_DELIMITER, QUOTE_CHARACTER);
        tokenizer.setIgnoreEmptyTokens(false);
        tokenizer.setEmptyTokenAsNull(true);

        assertEquals("a", tokenizer.next());
        assertEquals("b'c", tokenizer.next());
        assertFalse(tokenizer.hasNext());
    }
}
