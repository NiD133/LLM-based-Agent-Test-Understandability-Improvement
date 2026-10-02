package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.apache.commons.lang3.ArrayUtils;
import org.junit.jupiter.api.Test;

/**
 * Tests the {@link StringTokenizer#StringTokenizer(char[], char)} constructor,
 * which parses a {@code char[]} input using a single delimiter character.
 */
public class StringTokenizerTest_testConstructor_charArray_char {

    @Test
    void testConstructor_charArray_char() {
        // A space-delimited input should split into the tokens "a" and "b".
        final StringTokenizer spaceDelimited = new StringTokenizer("a b".toCharArray(), ' ');

        // The space character was registered as the delimiter, so its matcher
        // reports a one-character match for both char[] and String overloads.
        assertEquals(1, spaceDelimited.getDelimiterMatcher().isMatch(" ".toCharArray(), 0, 0, 1));
        assertEquals(1, spaceDelimited.getDelimiterMatcher().isMatch(" ", 0, 0, 1));

        // Iterate over the two expected tokens, then confirm none remain.
        assertEquals("a", spaceDelimited.next());
        assertEquals("b", spaceDelimited.next());
        assertFalse(spaceDelimited.hasNext());

        // An empty input array yields no tokens.
        final StringTokenizer emptyInput = new StringTokenizer(ArrayUtils.EMPTY_CHAR_ARRAY, ' ');
        assertFalse(emptyInput.hasNext());

        // A null input array also yields no tokens.
        final StringTokenizer nullInput = new StringTokenizer((char[]) null, ' ');
        assertFalse(nullInput.hasNext());
    }
}
