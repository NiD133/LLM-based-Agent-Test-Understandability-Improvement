package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

public class StringTokenizerTest_testDelimString {

    @Test
    void testDelimString() {
        final String input = "a##b##c";
        final StringTokenizer tok = new StringTokenizer(input, "##");
        assertEquals("a", tok.next());
        assertEquals("b", tok.next());
        assertEquals("c", tok.next());
        assertFalse(tok.hasNext());
    }
}
