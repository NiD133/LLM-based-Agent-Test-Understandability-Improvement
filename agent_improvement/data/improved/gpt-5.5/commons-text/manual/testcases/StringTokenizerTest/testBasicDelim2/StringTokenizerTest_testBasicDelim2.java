package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

public class StringTokenizerTest_testBasicDelim2 {

    @Test
    void testBasicDelim2() {
        final String input = "a:b:c";
        final StringTokenizer tokenizer = new StringTokenizer(input, ',');

        assertEquals("a:b:c", tokenizer.next());
        assertFalse(tokenizer.hasNext());
    }
}
