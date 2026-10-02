package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link AlphabetConverter#createConverterFromChars} rejects a
 * "do not encode" character that is absent from the original alphabet.
 */
public class AlphabetConverterTest_testMissingDoNotEncodeLettersFromOriginalTest {

    /** Original alphabet: space plus the lower-case English letters (no digits). */
    private static final Character[] LOWER_CASE_ENGLISH = {
        ' ', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
        'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'
    };

    /** Encoding alphabet: digits, both letter cases and a space. */
    private static final Character[] ENGLISH_AND_NUMBERS = {
        '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
        'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
        'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
        'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
        'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', ' '
    };

    /** "Do not encode" alphabet: the digits 0-9, none of which appear in the original alphabet. */
    private static final Character[] NUMBERS = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };

    @Test
    void testMissingDoNotEncodeLettersFromOriginalTest() {
        final IllegalArgumentException thrown = assertThrows(
            IllegalArgumentException.class,
            () -> AlphabetConverter.createConverterFromChars(
                LOWER_CASE_ENGLISH, ENGLISH_AND_NUMBERS, NUMBERS));

        assertEquals(
            "Can not use 'do not encode' list because original alphabet does not contain '0'",
            thrown.getMessage());
    }
}
