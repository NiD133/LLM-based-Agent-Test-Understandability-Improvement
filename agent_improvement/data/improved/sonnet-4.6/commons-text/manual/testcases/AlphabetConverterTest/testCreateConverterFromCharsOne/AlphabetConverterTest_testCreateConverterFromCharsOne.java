package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class AlphabetConverterTest_testCreateConverterFromCharsOne {

    @Test
    void testCreateConverterFromCharsOne() {
        // When original and encoding alphabets contain only one unique character
        // (duplicates are ignored), encodedCharLength should be 1 because
        // encoding alphabet size (1) >= original alphabet size (1).
        final Character[] duplicateCharArray = { '5', '5' };
        final AlphabetConverter alphabetConverter = AlphabetConverter.createConverterFromChars(
                duplicateCharArray, duplicateCharArray, duplicateCharArray);
        assertEquals(1, alphabetConverter.getEncodedCharLength());
    }
}
