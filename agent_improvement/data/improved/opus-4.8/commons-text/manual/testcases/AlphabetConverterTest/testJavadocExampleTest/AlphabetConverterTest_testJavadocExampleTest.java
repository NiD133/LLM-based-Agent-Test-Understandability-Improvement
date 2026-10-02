package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.UnsupportedEncodingException;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link AlphabetConverter} reproduces the worked example shown in
 * the class Javadoc.
 *
 * <p>The example converts from the original alphabet {@code a, b, c, d} into the
 * encoding alphabet {@code 0, 1, d}, while keeping {@code d} unencoded. Because
 * {@code d} is left as-is, the other letters are encoded as fixed-length pairs.</p>
 */
public class AlphabetConverterTest_testJavadocExampleTest {

    /** Letters of the source alphabet that the converter knows how to encode. */
    private static final Character[] ORIGINAL = { 'a', 'b', 'c', 'd' };

    /** Symbols available for building the encoded output. */
    private static final Character[] ENCODING = { '0', '1', 'd' };

    /** Letters that must be passed through unchanged instead of being encoded. */
    private static final Character[] DO_NOT_ENCODE = { 'd' };

    @Test
    void testJavadocExampleTest() throws UnsupportedEncodingException {
        final AlphabetConverter converter =
                AlphabetConverter.createConverterFromChars(ORIGINAL, ENCODING, DO_NOT_ENCODE);

        // Single characters: a/b/c become fixed-length pairs, while d is left unencoded.
        assertEquals("00", converter.encode("a"));
        assertEquals("01", converter.encode("b"));
        assertEquals("0d", converter.encode("c"));
        assertEquals("d", converter.encode("d"));

        // A full word is the concatenation of the per-character encodings above.
        assertEquals("00010dd", converter.encode("abcd"));
    }
}
