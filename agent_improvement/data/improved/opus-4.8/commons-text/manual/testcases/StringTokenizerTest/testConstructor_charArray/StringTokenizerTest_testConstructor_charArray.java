package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.apache.commons.lang3.ArrayUtils;
import org.junit.jupiter.api.Test;

/**
 * Tests the {@link StringTokenizer#StringTokenizer(char[])} constructor,
 * which tokenizes the given character array using the default settings
 * (whitespace as the delimiter).
 */
public class StringTokenizerTest_testConstructor_charArray {

    @Test
    void testConstructor_charArray() {
        // A non-empty input is split into whitespace-separated tokens.
        final StringTokenizer whitespaceSeparated = new StringTokenizer("a b".toCharArray());
        assertEquals("a", whitespaceSeparated.next());
        assertEquals("b", whitespaceSeparated.next());
        assertFalse(whitespaceSeparated.hasNext(), "no tokens remain after 'a' and 'b'");

        // An empty character array yields no tokens.
        final StringTokenizer emptyInput = new StringTokenizer(ArrayUtils.EMPTY_CHAR_ARRAY);
        assertFalse(emptyInput.hasNext(), "empty input has no tokens");

        // A null character array is treated as empty and yields no tokens.
        final StringTokenizer nullInput = new StringTokenizer((char[]) null);
        assertFalse(nullInput.hasNext(), "null input has no tokens");
    }
}
