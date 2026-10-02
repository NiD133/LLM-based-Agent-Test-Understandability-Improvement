package org.jsoup.internal;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class StringUtilTest_isHexDigit {

    @Test
    void decimalDigitsAreValidHexDigits() {
        assertTrue(StringUtil.isHexDigit('0'));
        assertTrue(StringUtil.isHexDigit('1'));
        assertTrue(StringUtil.isHexDigit('2'));
        assertTrue(StringUtil.isHexDigit('3'));
        assertTrue(StringUtil.isHexDigit('4'));
        assertTrue(StringUtil.isHexDigit('5'));
        assertTrue(StringUtil.isHexDigit('6'));
        assertTrue(StringUtil.isHexDigit('7'));
        assertTrue(StringUtil.isHexDigit('8'));
        assertTrue(StringUtil.isHexDigit('9'));
    }

    @Test
    void lowercaseHexLettersAreValidHexDigits() {
        assertTrue(StringUtil.isHexDigit('a'));
        assertTrue(StringUtil.isHexDigit('b'));
        assertTrue(StringUtil.isHexDigit('c'));
        assertTrue(StringUtil.isHexDigit('d'));
        assertTrue(StringUtil.isHexDigit('e'));
        assertTrue(StringUtil.isHexDigit('f'));
    }

    @Test
    void uppercaseHexLettersAreValidHexDigits() {
        assertTrue(StringUtil.isHexDigit('A'));
        assertTrue(StringUtil.isHexDigit('B'));
        assertTrue(StringUtil.isHexDigit('C'));
        assertTrue(StringUtil.isHexDigit('D'));
        assertTrue(StringUtil.isHexDigit('E'));
        assertTrue(StringUtil.isHexDigit('F'));
    }

    @Test
    void nonHexAsciiLettersAreRejected() {
        assertFalse(StringUtil.isHexDigit('g'));
        assertFalse(StringUtil.isHexDigit('G'));
    }

    @Test
    void nonAsciiCharactersAreRejected() {
        assertFalse(StringUtil.isHexDigit('ä'));
        assertFalse(StringUtil.isHexDigit('Ä'));
        assertFalse(StringUtil.isHexDigit('١')); // Arabic-Indic digit
        assertFalse(StringUtil.isHexDigit('୳')); // Odia digit
    }
}
