package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

public class AlphabetConverterTest_testCreateConverterFromCharsAndEquals {

    @Test
    void testCreateConverterFromCharsAndEquals() {
        final Character[] duplicatePlusAlphabet = { '+', '+' };
        final char plusCharacter = '+';

        final AlphabetConverter alphabetConverter = AlphabetConverter.createConverterFromChars(
                duplicatePlusAlphabet,
                duplicatePlusAlphabet,
                duplicatePlusAlphabet);

        assertFalse(alphabetConverter.equals(plusCharacter));
    }
}
