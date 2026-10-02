package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

public class StringTokenizerTest_testBasic4 {

    @Test
    void testBasic4() {
        // With no quote matcher configured, double-quotes are treated as ordinary
        // characters and remain part of the token rather than being stripped.
        final String input = "a \"b\" c";
        final StringTokenizer tok = new StringTokenizer(input);
        assertEquals("a", tok.next());
        assertEquals("\"b\"", tok.next());
        assertEquals("c", tok.next());
        assertFalse(tok.hasNext());
    }
}
