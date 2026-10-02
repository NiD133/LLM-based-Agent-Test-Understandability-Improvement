package org.jsoup.internal;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class StringUtilTest_isDigit {

    @ParameterizedTest
    @ValueSource(chars = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'})
    void asciiDigitsAreRecognized(char digit) {
        assertTrue(StringUtil.isDigit(digit));
    }

    @Test
    void asciiLettersAreNotDigits() {
        assertFalse(StringUtil.isDigit('a'));
        assertFalse(StringUtil.isDigit('A'));
    }

    @Test
    void nonAsciiCharactersAreNotDigits() {
        assertFalse(StringUtil.isDigit('ä')); // Latin letter with diaeresis
        assertFalse(StringUtil.isDigit('Ä')); // Latin capital letter with diaeresis
        assertFalse(StringUtil.isDigit('١')); // Arabic-Indic digit — not ASCII
        assertFalse(StringUtil.isDigit('୳')); // Odia digit — not ASCII
    }
}
