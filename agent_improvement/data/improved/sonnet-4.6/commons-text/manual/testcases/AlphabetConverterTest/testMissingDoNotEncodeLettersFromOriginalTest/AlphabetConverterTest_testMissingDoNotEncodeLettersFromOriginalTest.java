package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

public class AlphabetConverterTest_testMissingDoNotEncodeLettersFromOriginalTest {

    // Original alphabet: lowercase letters and space — digits are absent
    private static final Character[] LOWER_CASE_ENGLISH = {
        ' ', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
        'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'
    };

    // Encoding alphabet: digits + lower/uppercase letters + space
    private static final Character[] ENGLISH_AND_NUMBERS = {
        '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
        'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
        'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
        'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
        'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', ' '
    };

    // "Do not encode" list: digits — none of these appear in LOWER_CASE_ENGLISH
    private static final Character[] NUMBERS = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };

    @Test
    void testMissingDoNotEncodeLettersFromOriginalTest() {
        // The "do not encode" list contains '0'–'9', but LOWER_CASE_ENGLISH has no digits,
        // so createConverterFromChars must reject the request with a clear error message.
        String expectedMessage =
            "Can not use 'do not encode' list because original alphabet does not contain '0'";

        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> AlphabetConverter.createConverterFromChars(LOWER_CASE_ENGLISH, ENGLISH_AND_NUMBERS, NUMBERS)
        );

        assertEquals(expectedMessage, exception.getMessage());
    }
}
