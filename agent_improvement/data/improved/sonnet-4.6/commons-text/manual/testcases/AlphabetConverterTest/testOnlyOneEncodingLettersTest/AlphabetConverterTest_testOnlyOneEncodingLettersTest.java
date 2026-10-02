package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

public class AlphabetConverterTest_testOnlyOneEncodingLettersTest {

    private static final Character[] ENGLISH_AND_NUMBERS = {
        '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
        'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
        'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
        'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
        'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', ' '
    };

    private static final Character[] NUMBERS = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };

    @Test
    void testOnlyOneEncodingLettersTest() {
        // NUMBERS are excluded via doNotEncode, leaving only '_' as a usable encoding
        // character — one fewer than the required minimum of two.
        Character[] encodingWithUnderscore = Arrays.copyOf(NUMBERS, NUMBERS.length + 1);
        encodingWithUnderscore[encodingWithUnderscore.length - 1] = '_';

        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> AlphabetConverter.createConverterFromChars(ENGLISH_AND_NUMBERS, encodingWithUnderscore, NUMBERS)
        );

        assertEquals(
            "Must have at least two encoding characters (excluding those in the 'do not encode' list), but has 1",
            exception.getMessage()
        );
    }
}
