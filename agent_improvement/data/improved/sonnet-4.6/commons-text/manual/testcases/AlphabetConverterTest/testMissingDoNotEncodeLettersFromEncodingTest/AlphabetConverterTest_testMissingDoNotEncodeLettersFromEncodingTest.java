package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class AlphabetConverterTest_testMissingDoNotEncodeLettersFromEncodingTest {

    // Original alphabet: digits 0-9, lowercase a-z, uppercase A-Z, and space
    private static final Character[] ENGLISH_AND_NUMBERS = {
        '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
        'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
        'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
        'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
        'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
        ' '
    };

    // Encoding alphabet: lowercase a-z and space only (digits are absent)
    private static final Character[] LOWER_CASE_ENGLISH = {
        ' ', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
        'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'
    };

    // Do-not-encode list: digits 0-9, which are NOT present in the encoding alphabet
    private static final Character[] NUMBERS = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };

    @Test
    void testMissingDoNotEncodeLettersFromEncodingTest() {
        // '0' is in the do-not-encode list but absent from the encoding alphabet (LOWER_CASE_ENGLISH),
        // so the converter must reject this configuration with an IllegalArgumentException.
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> AlphabetConverter.createConverterFromChars(ENGLISH_AND_NUMBERS, LOWER_CASE_ENGLISH, NUMBERS)
        );

        String expectedMessage = "Can not use 'do not encode' list because encoding alphabet does not contain '0'";
        assertEquals(expectedMessage, exception.getMessage());
    }
}
