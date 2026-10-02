package org.jsoup.internal;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Verifies {@link StringUtil#isHexDigit(char)}, which recognises the characters
 * that make up a hexadecimal number: the decimal digits 0-9 plus the letters
 * a-f in either lower or upper case.
 */
public class StringUtilTest_isHexDigit {

    private static final String DECIMAL_DIGITS = "0123456789";
    private static final String LOWERCASE_HEX_LETTERS = "abcdef";
    private static final String UPPERCASE_HEX_LETTERS = "ABCDEF";

    @Test
    void isHexDigit() {
        assertAllHexDigits(DECIMAL_DIGITS);
        assertAllHexDigits(LOWERCASE_HEX_LETTERS);
        assertAllHexDigits(UPPERCASE_HEX_LETTERS);

        // Letters just past the hex range are not hex digits.
        assertNotHexDigit('g');
        assertNotHexDigit('G');
        // Accented and non-ASCII digit characters are not hex digits either.
        assertNotHexDigit('ä');
        assertNotHexDigit('Ä');
        assertNotHexDigit('١'); // Arabic-Indic digit one
        assertNotHexDigit('୳'); // Oriya fraction one quarter
    }

    private static void assertAllHexDigits(String hexChars) {
        for (char c : hexChars.toCharArray()) {
            assertTrue(StringUtil.isHexDigit(c), c + " should be recognised as a hex digit");
        }
    }

    private static void assertNotHexDigit(char c) {
        assertFalse(StringUtil.isHexDigit(c), c + " should not be recognised as a hex digit");
    }
}
