package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

public class StringTokenizerTest_testBasicDelim1 {

    private static final String COLON_DELIMITED_TEXT = "a:b:c";
    private static final char COLON_DELIMITER = ':';

    @Test
    void testBasicDelim1() {
        final StringTokenizer tokenizer = new StringTokenizer(COLON_DELIMITED_TEXT, COLON_DELIMITER);

        assertEquals("a", tokenizer.next());
        assertEquals("b", tokenizer.next());
        assertEquals("c", tokenizer.next());
        assertFalse(tokenizer.hasNext());
    }
}
