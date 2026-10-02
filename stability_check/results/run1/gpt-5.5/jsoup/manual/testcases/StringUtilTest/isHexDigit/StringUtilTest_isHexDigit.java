package org.jsoup.internal;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StringUtilTest_isHexDigit {
    private static final char[] DECIMAL_DIGITS = {
        '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'
    };

    private static final char[] LOWERCASE_HEX_LETTERS = {
        'a', 'b', 'c', 'd', 'e', 'f'
    };

    private static final char[] UPPERCASE_HEX_LETTERS = {
        'A', 'B', 'C', 'D', 'E', 'F'
    };

    private static final char[] NON_HEX_CHARACTERS = {
        'g', 'G', 'ä', 'Ä', '١', '୳'
    };

    @Test
    void isHexDigit() {
        assertHexDigits(DECIMAL_DIGITS);
        assertHexDigits(LOWERCASE_HEX_LETTERS);
        assertHexDigits(UPPERCASE_HEX_LETTERS);
        assertNotHexDigits(NON_HEX_CHARACTERS);
    }

    private static void assertHexDigits(char[] characters) {
        for (char character : characters) {
            assertTrue(StringUtil.isHexDigit(character));
        }
    }

    private static void assertNotHexDigits(char[] characters) {
        for (char character : characters) {
            assertFalse(StringUtil.isHexDigit(character));
        }
    }
}
