package org.jsoup.internal;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests {@link StringUtil#isDigit(char)}, which recognises only the ASCII digits '0' through '9'.
 */
public class StringUtilTest_isDigit {

    @Test
    void recognisesAsciiDigitsZeroThroughNine() {
        for (char digit = '0'; digit <= '9'; digit++) {
            assertTrue(StringUtil.isDigit(digit), "Expected '" + digit + "' to be recognised as a digit");
        }
    }

    @Test
    void rejectsNonAsciiDigitCharacters() {
        // Latin letters (lower and upper case) are not ASCII digits.
        assertFalse(StringUtil.isDigit('a'));
        assertFalse(StringUtil.isDigit('A'));

        // Accented Latin letters are not ASCII digits.
        assertFalse(StringUtil.isDigit('ä'));
        assertFalse(StringUtil.isDigit('Ä'));

        // Digits from non-ASCII scripts (Arabic-Indic '١', Oriya '୳') are not ASCII digits.
        assertFalse(StringUtil.isDigit('١'));
        assertFalse(StringUtil.isDigit('୳'));
    }
}
