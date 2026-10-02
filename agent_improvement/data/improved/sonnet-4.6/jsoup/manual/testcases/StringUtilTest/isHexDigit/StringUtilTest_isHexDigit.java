package org.jsoup.internal;

import org.junit.jupiter.api.Test;
import static org.jsoup.internal.StringUtil.isHexDigit;
import static org.junit.jupiter.api.Assertions.*;

public class StringUtilTest_isHexDigit {

    @Test
    void decimalDigitsAreValidHexDigits() {
        assertTrue(isHexDigit('0'));
        assertTrue(isHexDigit('1'));
        assertTrue(isHexDigit('2'));
        assertTrue(isHexDigit('3'));
        assertTrue(isHexDigit('4'));
        assertTrue(isHexDigit('5'));
        assertTrue(isHexDigit('6'));
        assertTrue(isHexDigit('7'));
        assertTrue(isHexDigit('8'));
        assertTrue(isHexDigit('9'));
    }

    @Test
    void lowercaseHexLettersAreValidHexDigits() {
        assertTrue(isHexDigit('a'));
        assertTrue(isHexDigit('b'));
        assertTrue(isHexDigit('c'));
        assertTrue(isHexDigit('d'));
        assertTrue(isHexDigit('e'));
        assertTrue(isHexDigit('f'));
    }

    @Test
    void uppercaseHexLettersAreValidHexDigits() {
        assertTrue(isHexDigit('A'));
        assertTrue(isHexDigit('B'));
        assertTrue(isHexDigit('C'));
        assertTrue(isHexDigit('D'));
        assertTrue(isHexDigit('E'));
        assertTrue(isHexDigit('F'));
    }

    @Test
    void lettersAfterFAreNotHexDigits() {
        assertFalse(isHexDigit('g'));
        assertFalse(isHexDigit('G'));
    }

    @Test
    void nonAsciiCharactersAreNotHexDigits() {
        assertFalse(isHexDigit('ä'));  // Latin extended
        assertFalse(isHexDigit('Ä'));  // Latin extended uppercase
        assertFalse(isHexDigit('١'));  // Arabic-Indic digit
        assertFalse(isHexDigit('୳'));  // Odia digit
    }
}
