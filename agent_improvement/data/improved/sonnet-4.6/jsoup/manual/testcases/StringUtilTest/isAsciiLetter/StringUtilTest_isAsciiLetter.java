package org.jsoup.internal;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StringUtilTest_isAsciiLetter {

    @Test
    @DisplayName("lowercase ASCII letters a-z are recognized as letters")
    void lowercaseAsciiLettersReturnTrue() {
        assertTrue(StringUtil.isAsciiLetter('a')); // boundary: first lowercase
        assertTrue(StringUtil.isAsciiLetter('n')); // midpoint lowercase
        assertTrue(StringUtil.isAsciiLetter('z')); // boundary: last lowercase
    }

    @Test
    @DisplayName("uppercase ASCII letters A-Z are recognized as letters")
    void uppercaseAsciiLettersReturnTrue() {
        assertTrue(StringUtil.isAsciiLetter('A')); // boundary: first uppercase
        assertTrue(StringUtil.isAsciiLetter('N')); // midpoint uppercase
        assertTrue(StringUtil.isAsciiLetter('Z')); // boundary: last uppercase
    }

    @Test
    @DisplayName("non-letter characters are not recognized as ASCII letters")
    void nonLetterCharactersReturnFalse() {
        assertFalse(StringUtil.isAsciiLetter(' '));  // whitespace
        assertFalse(StringUtil.isAsciiLetter('-'));  // punctuation
        assertFalse(StringUtil.isAsciiLetter('0'));  // ASCII digit
        assertFalse(StringUtil.isAsciiLetter('ß')); // non-ASCII lowercase letter
        assertFalse(StringUtil.isAsciiLetter('Ě')); // non-ASCII uppercase letter
    }
}
