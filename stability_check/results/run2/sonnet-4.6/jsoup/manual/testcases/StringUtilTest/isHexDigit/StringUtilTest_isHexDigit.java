package org.jsoup.internal;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static org.junit.jupiter.api.Assertions.*;

public class StringUtilTest_isHexDigit {

    @ParameterizedTest
    @ValueSource(chars = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'})
    void decimalDigitsAreValidHexDigits(char c) {
        assertTrue(StringUtil.isHexDigit(c));
    }

    @ParameterizedTest
    @ValueSource(chars = {'a', 'b', 'c', 'd', 'e', 'f'})
    void lowercaseHexLettersAreValidHexDigits(char c) {
        assertTrue(StringUtil.isHexDigit(c));
    }

    @ParameterizedTest
    @ValueSource(chars = {'A', 'B', 'C', 'D', 'E', 'F'})
    void uppercaseHexLettersAreValidHexDigits(char c) {
        assertTrue(StringUtil.isHexDigit(c));
    }

    @ParameterizedTest
    @ValueSource(chars = {'g', 'G', 'ä', 'Ä', '١', '୳'})
    void nonHexCharactersAreNotValidHexDigits(char c) {
        assertFalse(StringUtil.isHexDigit(c));
    }
}
