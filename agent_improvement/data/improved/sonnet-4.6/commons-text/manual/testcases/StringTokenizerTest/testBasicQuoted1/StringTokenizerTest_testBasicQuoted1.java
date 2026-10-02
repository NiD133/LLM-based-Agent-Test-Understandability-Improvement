package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import org.junit.jupiter.api.Test;

public class StringTokenizerTest_testBasicQuoted1 {

    @Test
    void testBasicQuoted1() {
        // Tokenize a string with single-quoted token using space as delimiter and single-quote as quote char.
        final String input = "a 'b' c";
        final StringTokenizer tok = new StringTokenizer(input, ' ', '\'');
        assertEquals("a", tok.next());
        assertEquals("b", tok.next());
        assertEquals("c", tok.next());
        assertFalse(tok.hasNext());
    }
}
