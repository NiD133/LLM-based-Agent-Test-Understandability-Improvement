package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class AlphabetConverterTest_testCreateConverterFromCharsOne {

    @Test
    void testCreateConverterFromCharsOne() {
        final Character[] duplicateOnlyAlphabet = new Character[2];
        duplicateOnlyAlphabet[0] = '5';
        duplicateOnlyAlphabet[1] = duplicateOnlyAlphabet[0];

        final AlphabetConverter alphabetConverter = AlphabetConverter.createConverterFromChars(
                duplicateOnlyAlphabet,
                duplicateOnlyAlphabet,
                duplicateOnlyAlphabet);

        assertEquals(1, alphabetConverter.getEncodedCharLength());
    }
}
