package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link AlphabetConverter#createConverterFromChars} rejects an
 * encoding alphabet that, once the 'do not encode' characters are removed, has
 * fewer than the two characters required to build a multi-character encoding.
 */
public class AlphabetConverterTest_testOnlyOneEncodingLettersTest {

    /** Original alphabet to encode: upper- and lower-case English letters, digits and space. */
    private static final Character[] ORIGINAL_ALPHABET = {
        '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
        'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
        'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
        'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
        'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', ' '
    };

    /** The ten digits, used as the 'do not encode' characters. */
    private static final Character[] DIGITS = {
        '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'
    };

    @Test
    void rejectsEncodingAlphabetWithOnlyOneUsableCharacter() {
        // Encoding alphabet = the ten digits plus '_'. With every digit listed as
        // 'do not encode', only '_' remains usable for encoding - one character,
        // which is below the minimum of two.
        final Character[] encodingAlphabet = Arrays.copyOf(DIGITS, DIGITS.length + 1);
        encodingAlphabet[encodingAlphabet.length - 1] = '_';
        final Character[] doNotEncode = DIGITS;

        final IllegalArgumentException thrown = assertThrows(
            IllegalArgumentException.class,
            () -> AlphabetConverter.createConverterFromChars(
                ORIGINAL_ALPHABET, encodingAlphabet, doNotEncode));

        assertEquals(
            "Must have at least two encoding characters (excluding those in the "
                + "'do not encode' list), but has 1",
            thrown.getMessage());
    }
}
