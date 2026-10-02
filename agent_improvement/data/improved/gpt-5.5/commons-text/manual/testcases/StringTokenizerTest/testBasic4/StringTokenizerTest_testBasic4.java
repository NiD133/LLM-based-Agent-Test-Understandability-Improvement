package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

public class StringTokenizerTest_testBasic4 {

    @Test
    void testBasic4() {
        final String inputWithQuotedMiddleToken = "a \"b\" c";
        final StringTokenizer tokenizer = new StringTokenizer(inputWithQuotedMiddleToken);

        assertEquals("a", tokenizer.next());
        assertEquals("\"b\"", tokenizer.next());
        assertEquals("c", tokenizer.next());
        assertFalse(tokenizer.hasNext());
    }
}
