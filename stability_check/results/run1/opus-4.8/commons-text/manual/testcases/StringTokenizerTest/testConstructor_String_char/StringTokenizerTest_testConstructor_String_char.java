package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

/**
 * Verifies the {@link StringTokenizer#StringTokenizer(String, char)} constructor,
 * which builds a tokenizer that splits the given input on a single delimiter character.
 */
public class StringTokenizerTest_testConstructor_String_char {

    /** The single space character used as the field delimiter throughout this test. */
    private static final char SPACE_DELIMITER = ' ';

    /** Expected match length reported by the delimiter matcher for a single space. */
    private static final int MATCH_LENGTH_SINGLE_SPACE = 1;

    @Test
    void testConstructor_String_char() {
        // A space-delimited input splits into the expected tokens.
        final StringTokenizer spaceDelimited = new StringTokenizer("a b", SPACE_DELIMITER);

        // The configured delimiter matcher recognises a space, whether given as a char[] or a String.
        assertEquals(MATCH_LENGTH_SINGLE_SPACE,
                spaceDelimited.getDelimiterMatcher().isMatch(" ".toCharArray(), 0, 0, 1));
        assertEquals(MATCH_LENGTH_SINGLE_SPACE,
                spaceDelimited.getDelimiterMatcher().isMatch(" ", 0, 0, 1));

        assertEquals("a", spaceDelimited.next());
        assertEquals("b", spaceDelimited.next());
        assertFalse(spaceDelimited.hasNext());

        // An empty input yields no tokens.
        final StringTokenizer emptyInput = new StringTokenizer("", SPACE_DELIMITER);
        assertFalse(emptyInput.hasNext());

        // A null input yields no tokens.
        final StringTokenizer nullInput = new StringTokenizer((String) null, SPACE_DELIMITER);
        assertFalse(nullInput.hasNext());
    }
}
