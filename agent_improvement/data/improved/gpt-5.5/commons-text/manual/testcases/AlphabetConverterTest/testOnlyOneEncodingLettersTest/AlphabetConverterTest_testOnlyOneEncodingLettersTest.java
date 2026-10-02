package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

public class AlphabetConverterTest_testOnlyOneEncodingLettersTest {

    private static final Character[] NUMBERS = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };

    private static final Character[] ENGLISH_LETTERS_NUMBERS_AND_SPACE = {
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
            'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't',
            'u', 'v', 'w', 'x', 'y', 'z',
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
            'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T',
            'U', 'V', 'W', 'X', 'Y', 'Z', ' '
    };

    @Test
    void testOnlyOneEncodingLettersTest() {
        final IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            final Character[] numbersPlusUnderscore = Arrays.copyOf(NUMBERS, NUMBERS.length + 1);
            numbersPlusUnderscore[numbersPlusUnderscore.length - 1] = '_';
            AlphabetConverter.createConverterFromChars(ENGLISH_LETTERS_NUMBERS_AND_SPACE, numbersPlusUnderscore, NUMBERS);
        });

        assertEquals(
                "Must have at least two encoding characters (excluding those in the 'do not encode' list), but has 1",
                exception.getMessage());
    }
}
