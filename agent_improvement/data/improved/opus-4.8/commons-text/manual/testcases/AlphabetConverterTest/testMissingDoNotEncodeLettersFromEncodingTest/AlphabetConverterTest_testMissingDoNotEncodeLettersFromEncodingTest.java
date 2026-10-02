package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class AlphabetConverterTest_testMissingDoNotEncodeLettersFromEncodingTest {

    /** Full original alphabet: digits, upper- and lower-case letters, plus a space. */
    private static final Character[] ENGLISH_AND_NUMBERS = {
        '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
        'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
        'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
        'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
        'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', ' '
    };

    /** Encoding alphabet: only lower-case letters and a space (no digits). */
    private static final Character[] LOWER_CASE_ENGLISH = {
        ' ', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
        'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'
    };

    /** Characters requested to stay unencoded: the digits 0-9. */
    private static final Character[] NUMBERS = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };

    /**
     * A 'do not encode' character must appear in the encoding alphabet. Here the digits
     * are asked to be left unencoded, but the encoding alphabet contains no digits, so
     * construction must fail for the first missing digit ('0').
     */
    @Test
    void testMissingDoNotEncodeLettersFromEncodingTest() {
        final IllegalArgumentException thrown = assertThrows(
            IllegalArgumentException.class,
            () -> AlphabetConverter.createConverterFromChars(ENGLISH_AND_NUMBERS, LOWER_CASE_ENGLISH, NUMBERS));

        assertEquals(
            "Can not use 'do not encode' list because encoding alphabet does not contain '0'",
            thrown.getMessage());
    }
}
