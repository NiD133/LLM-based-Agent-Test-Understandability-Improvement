package org.jsoup.internal;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests {@link StringUtil#isAsciiLetter(char)}, which recognises only the
 * ASCII letters a-z and A-Z. Every other character (including digits,
 * punctuation, whitespace, and accented/non-ASCII letters) is rejected.
 */
public class StringUtilTest_isAsciiLetter {

    @Test
    void recognisesAsciiLettersAndRejectsEverythingElse() {
        // Lowercase ASCII letters: start, middle, and end of the a-z range.
        assertTrue(StringUtil.isAsciiLetter('a'));
        assertTrue(StringUtil.isAsciiLetter('n'));
        assertTrue(StringUtil.isAsciiLetter('z'));

        // Uppercase ASCII letters: start, middle, and end of the A-Z range.
        assertTrue(StringUtil.isAsciiLetter('A'));
        assertTrue(StringUtil.isAsciiLetter('N'));
        assertTrue(StringUtil.isAsciiLetter('Z'));

        // Non-letters: whitespace, punctuation, and a digit are not letters.
        assertFalse(StringUtil.isAsciiLetter(' '));
        assertFalse(StringUtil.isAsciiLetter('-'));
        assertFalse(StringUtil.isAsciiLetter('0'));

        // Non-ASCII letters fall outside the supported range.
        assertFalse(StringUtil.isAsciiLetter('ß'));
        assertFalse(StringUtil.isAsciiLetter('Ě'));
    }
}
