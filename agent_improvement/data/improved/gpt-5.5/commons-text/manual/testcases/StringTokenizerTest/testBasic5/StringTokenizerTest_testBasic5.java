package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

public class StringTokenizerTest_testBasic5 {

    private static final String INPUT_WITH_UNMATCHED_QUOTE = "a:b':c";
    private static final char TOKEN_DELIMITER = ':';
    private static final char QUOTE_CHARACTER = '\'';

    @Test
    void testBasic5() {
        final StringTokenizer tokenizer = new StringTokenizer(INPUT_WITH_UNMATCHED_QUOTE, TOKEN_DELIMITER, QUOTE_CHARACTER);

        assertEquals("a", tokenizer.next());
        assertEquals("b'", tokenizer.next());
        assertEquals("c", tokenizer.next());
        assertFalse(tokenizer.hasNext());
    }
}
