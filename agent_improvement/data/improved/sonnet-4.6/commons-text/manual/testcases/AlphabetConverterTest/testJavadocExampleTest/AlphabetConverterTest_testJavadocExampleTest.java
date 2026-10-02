package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.UnsupportedEncodingException;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link AlphabetConverter} behaves exactly as described in its
 * class-level Javadoc example.
 *
 * <p>Setup: original = {a, b, c, d}, encoding = {0, 1, d}, doNotEncode = {d}.
 * Because the encoding alphabet (3 chars) is smaller than the original (4 chars),
 * and 'd' is excluded from the leftmost position, each non-exempt letter maps to
 * a 2-character sequence, while 'd' passes through unchanged.</p>
 */
public class AlphabetConverterTest_testJavadocExampleTest {

    /**
     * Builds the converter described in the AlphabetConverter Javadoc:
     *   original   = {a, b, c, d}
     *   encoding   = {0, 1, d}
     *   doNotEncode = {d}  → 'd' is left as-is; a/b/c each get a 2-char code.
     */
    private AlphabetConverter createJavadocConverter() {
        final Character[] original    = { 'a', 'b', 'c', 'd' };
        final Character[] encoding    = { '0', '1', 'd' };
        final Character[] doNotEncode = { 'd' };
        return AlphabetConverter.createConverterFromChars(original, encoding, doNotEncode);
    }

    /**
     * Asserts that each character and the combined string encode to the values
     * shown in the AlphabetConverter Javadoc:
     *   a → 00, b → 01, c → 0d, d → d (pass-through), abcd → 00010dd
     */
    @Test
    void testJavadocExampleTest() throws UnsupportedEncodingException {
        final AlphabetConverter ac = createJavadocConverter();

        // single-character encodings match the Javadoc table
        assertEquals("00", ac.encode("a")); // first non-exempt letter → 00
        assertEquals("01", ac.encode("b")); // second non-exempt letter → 01
        assertEquals("0d", ac.encode("c")); // third non-exempt letter → 0d
        assertEquals("d",  ac.encode("d")); // exempt letter passes through unchanged

        // concatenation of individual encodings: 00 + 01 + 0d + d = 00010dd
        assertEquals("00010dd", ac.encode("abcd"));
    }
}
