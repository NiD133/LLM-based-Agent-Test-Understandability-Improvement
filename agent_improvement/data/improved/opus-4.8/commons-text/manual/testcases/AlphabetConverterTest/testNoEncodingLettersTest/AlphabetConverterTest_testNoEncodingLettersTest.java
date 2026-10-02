package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link AlphabetConverter#createConverterFromChars} rejects an
 * encoding alphabet that has no usable encoding characters once the
 * 'do not encode' characters are excluded.
 */
public class AlphabetConverterTest_testNoEncodingLettersTest {

    /** Original alphabet: digits plus the full English alphabet (upper and lower case) and a space. */
    private static final Character[] ORIGINAL_ALPHABET = {
        '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
        'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
        'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
        'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
        'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', ' '
    };

    /** Digits only, used as both the encoding alphabet and the 'do not encode' list. */
    private static final Character[] DIGITS = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };

    @Test
    void testNoEncodingLettersTest() {
        // Using the digits as both the encoding alphabet and the 'do not encode' list
        // leaves zero characters available for encoding, which is not allowed.
        final IllegalArgumentException thrown = assertThrows(
            IllegalArgumentException.class,
            () -> AlphabetConverter.createConverterFromChars(ORIGINAL_ALPHABET, DIGITS, DIGITS));

        assertEquals(
            "Must have at least two encoding characters (excluding those in the 'do not encode' list), but has 0",
            thrown.getMessage());
    }
}
