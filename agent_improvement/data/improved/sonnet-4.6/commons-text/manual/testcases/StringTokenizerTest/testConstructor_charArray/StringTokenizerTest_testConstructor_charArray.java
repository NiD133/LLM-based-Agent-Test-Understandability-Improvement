package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.apache.commons.lang3.ArrayUtils;
import org.junit.jupiter.api.Test;

public class StringTokenizerTest_testConstructor_charArray {

    @Test
    void testConstructor_charArray() {
        // A non-empty char array: whitespace-delimited tokens "a" and "b" should be produced
        StringTokenizer tok = new StringTokenizer("a b".toCharArray());
        assertEquals("a", tok.next());
        assertEquals("b", tok.next());
        assertFalse(tok.hasNext());

        // An empty char array: no tokens should be available
        tok = new StringTokenizer(ArrayUtils.EMPTY_CHAR_ARRAY);
        assertFalse(tok.hasNext());

        // A null char array: treated as empty input, so no tokens should be available
        tok = new StringTokenizer((char[]) null);
        assertFalse(tok.hasNext());
    }
}
