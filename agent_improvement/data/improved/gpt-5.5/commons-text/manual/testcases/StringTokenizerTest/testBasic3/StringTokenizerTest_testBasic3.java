package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

public class StringTokenizerTest_testBasic3 {

    private static final String INPUT_WITH_DEFAULT_DELIMITERS = "a \nb\u0001\fc";

    @Test
    void testBasic3() {
        final StringTokenizer tokenizer = new StringTokenizer(INPUT_WITH_DEFAULT_DELIMITERS);

        assertEquals("a", tokenizer.next());
        assertEquals("b\u0001", tokenizer.next());
        assertEquals("c", tokenizer.next());
        assertFalse(tokenizer.hasNext());
    }
}
