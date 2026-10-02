package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

public class StringTokenizerTest_testBasic5 {

    /**
     * Verifies that a quote character (') appearing mid-token — not at the start — is treated as a literal
     * character rather than opening a quoted section. In "a:b':c" with ':' as delimiter and '\'' as quote
     * character, the token "b'" contains the quote literally, and the following ':' still acts as a delimiter.
     */
    @Test
    void testBasic5() {
        final String input = "a:b':c";
        final StringTokenizer tok = new StringTokenizer(input, ':', '\'');

        assertEquals("a", tok.next());    // first token before delimiter
        assertEquals("b'", tok.next());  // mid-token quote is treated as a literal character
        assertEquals("c", tok.next());   // third token after the second delimiter
        assertFalse(tok.hasNext());       // no more tokens remain
    }
}
