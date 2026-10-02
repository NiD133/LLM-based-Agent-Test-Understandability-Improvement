package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class StringTokenizerTest_testBasicEmpty2 {

    @Test
    @DisplayName("Empty tokens between delimiters are returned as null when emptyTokenAsNull is enabled")
    void testBasicEmpty2() {
        // "a  b c" has two consecutive spaces between 'a' and 'b', producing one empty token.
        // With ignoreEmptyTokens=false and emptyTokenAsNull=true, that empty token comes back as null.
        final String input = "a  b c";
        final StringTokenizer tok = new StringTokenizer(input);
        tok.setIgnoreEmptyTokens(false);
        tok.setEmptyTokenAsNull(true);

        assertEquals("a", tok.next());
        assertNull(tok.next());   // empty token between the two spaces
        assertEquals("b", tok.next());
        assertEquals("c", tok.next());
        assertFalse(tok.hasNext());
    }
}
