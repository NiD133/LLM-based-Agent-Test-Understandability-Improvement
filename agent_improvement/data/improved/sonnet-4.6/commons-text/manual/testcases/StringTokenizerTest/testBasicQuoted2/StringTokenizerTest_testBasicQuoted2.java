package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

public class StringTokenizerTest_testBasicQuoted2 {

    @Test
    void testBasicQuoted2() {
        // Input "a:'b':" uses ':' as delimiter and '\'' as quote char.
        // Tokens: "a", quoted "b", and a trailing empty token (returned as null
        // because emptyTokenAsNull=true and ignoreEmptyTokens=false).
        final String input = "a:'b':";
        final StringTokenizer tok = new StringTokenizer(input, ':', '\'');
        tok.setIgnoreEmptyTokens(false);
        tok.setEmptyTokenAsNull(true);

        assertEquals("a", tok.next());
        assertEquals("b", tok.next());
        assertNull(tok.next());
        assertFalse(tok.hasNext());
    }
}
