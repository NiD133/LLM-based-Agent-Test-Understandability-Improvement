package org.jsoup.internal;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests {@link StringUtil#isHexDigit(char)}, which recognises the characters of a
 * hexadecimal number: the decimal digits {@code 0-9} and the letters {@code a-f}
 * in both lower and upper case.
 */
public class StringUtilTest_isHexDigit {

    /** Characters that ARE valid hex digits. */
    private static final String HEX_DIGITS = "0123456789abcdefABCDEF";

    /**
     * Characters that are NOT valid hex digits:
     * letters just past 'f'/'F', and non-ASCII "digit"/letter look-alikes.
     */
    private static final char[] NON_HEX_DIGITS = {
        'g',  // first ASCII letter after the hex range (lower case)
        'G',  // first ASCII letter after the hex range (upper case)
        'ä',  // accented latin letter
        'Ä',  // accented latin letter, upper case
        '١',  // Arabic-Indic digit one
        '୳'   // Oriya fraction sign
    };

    @Test
    void recognisesEveryHexDigit() {
        for (char c : HEX_DIGITS.toCharArray()) {
            assertTrue(StringUtil.isHexDigit(c), "expected '" + c + "' to be a hex digit");
        }
    }

    @Test
    void rejectsNonHexDigits() {
        for (char c : NON_HEX_DIGITS) {
            assertFalse(StringUtil.isHexDigit(c), "expected '" + c + "' to not be a hex digit");
        }
    }
}
