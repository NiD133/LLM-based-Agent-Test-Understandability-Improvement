package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class AlphabetConverterTest_testMissingDoNotEncodeLettersFromOriginalTest {

    private static final Character[] LOWER_CASE_ENGLISH = {
        ' ', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i',
        'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's',
        't', 'u', 'v', 'w', 'x', 'y', 'z'
    };

    private static final Character[] ENGLISH_AND_NUMBERS = {
        '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
        'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
        'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't',
        'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D',
        'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N',
        'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X',
        'Y', 'Z', ' '
    };

    private static final Character[] NUMBERS = {
        '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'
    };

    @Test
    void testMissingDoNotEncodeLettersFromOriginalTest() {
        final IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> AlphabetConverter.createConverterFromChars(
                LOWER_CASE_ENGLISH,
                ENGLISH_AND_NUMBERS,
                NUMBERS));

        assertEquals(
            "Can not use 'do not encode' list because original alphabet does not contain '0'",
            exception.getMessage());
    }
}
