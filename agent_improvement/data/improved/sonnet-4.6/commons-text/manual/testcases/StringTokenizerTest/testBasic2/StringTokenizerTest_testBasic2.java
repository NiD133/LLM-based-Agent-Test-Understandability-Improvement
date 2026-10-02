package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

public class StringTokenizerTest_testBasic2 {

    @Test
    void testBasic2() {
        // Default delimiter set includes space, \n (newline), and \f (form feed)
        final String input = "a \nb\fc";
        final StringTokenizer tok = new StringTokenizer(input);
        assertEquals("a", tok.next());
        assertEquals("b", tok.next());
        assertEquals("c", tok.next());
        assertFalse(tok.hasNext());
    }
}
