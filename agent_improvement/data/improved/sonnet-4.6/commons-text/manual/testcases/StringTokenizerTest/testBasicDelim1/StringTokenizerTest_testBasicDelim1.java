package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

public class StringTokenizerTest_testBasicDelim1 {

    @Test
    void testBasicDelim1() {
        // Tokenize a colon-delimited string and verify each token in order
        final String input = "a:b:c";
        final StringTokenizer tok = new StringTokenizer(input, ':');

        assertEquals("a", tok.next());
        assertEquals("b", tok.next());
        assertEquals("c", tok.next());
        assertFalse(tok.hasNext(), "No more tokens expected after the last element");
    }
}
