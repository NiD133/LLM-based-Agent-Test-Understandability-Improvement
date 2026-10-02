package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Verifies {@link WordUtils#initials} on words built from supplementary-plane
 * (surrogate-pair) characters.
 *
 * <p>Each "character" below is a single Unicode code point above U+FFFF, so it
 * occupies two {@code char} values (a high/low surrogate pair) in a Java
 * {@code String}. The test confirms that {@code initials} extracts the first
 * whole code point of every word, never splitting a surrogate pair.</p>
 */
public class WordUtilsTest_testInitialsSurrogatePairs {

    // Old Italic letters in the Unicode supplementary plane (U+103xx).
    // Each constant is one code point encoded as a UTF-16 surrogate pair.
    private static final String C0 = codePoint(0x10300);
    private static final String C1 = codePoint(0x10301);
    private static final String C2 = codePoint(0x10302);
    private static final String C3 = codePoint(0x10303);
    private static final String C14 = codePoint(0x10314);
    private static final String C18 = codePoint(0x10318);

    /** Two-code-point words; {@code initials} should keep their leading code points: C0 and C2. */
    private static final String WORD_A = C0 + C1;
    private static final String WORD_B = C2 + C3;
    private static final String EXPECTED_INITIALS = C0 + C2;

    @Test
    void testInitialsSurrogatePairs() {
        // Default delimiter (whitespace): single-arg and explicit-null overloads behave the same.
        assertEquals(EXPECTED_INITIALS, WordUtils.initials(WORD_A + " " + WORD_B));
        assertEquals(EXPECTED_INITIALS, WordUtils.initials(WORD_A + " " + WORD_B, (char[]) null));
        // Single-code-point words separated (and trailed) by whitespace.
        assertEquals(EXPECTED_INITIALS, WordUtils.initials(C0 + " " + C2 + " ", (char[]) null));

        // Delimiters that are ordinary UTF-16 (BMP) characters.
        assertEquals(EXPECTED_INITIALS, WordUtils.initials(WORD_A + "." + WORD_B, new char[] { '.' }));
        assertEquals(EXPECTED_INITIALS, WordUtils.initials(WORD_A + "A" + WORD_B, new char[] { 'A' }));

        // Delimiters that are themselves supplementary-plane code points.
        assertEquals(EXPECTED_INITIALS,
                WordUtils.initials(WORD_A + C14 + WORD_B, surrogatePair(0x10314)));
        assertEquals(EXPECTED_INITIALS,
                WordUtils.initials(WORD_A + C14 + C18 + WORD_B, twoSurrogatePairs(0x10314, 0x10318)));
    }

    /** Returns the given code point as a String (a surrogate pair when above U+FFFF). */
    private static String codePoint(final int cp) {
        return new String(Character.toChars(cp));
    }

    /** Returns the high/low surrogate chars of a single supplementary-plane code point. */
    private static char[] surrogatePair(final int cp) {
        return Character.toChars(cp);
    }

    /** Returns the surrogate chars of two supplementary-plane code points, concatenated. */
    private static char[] twoSurrogatePairs(final int first, final int second) {
        return (codePoint(first) + codePoint(second)).toCharArray();
    }
}
