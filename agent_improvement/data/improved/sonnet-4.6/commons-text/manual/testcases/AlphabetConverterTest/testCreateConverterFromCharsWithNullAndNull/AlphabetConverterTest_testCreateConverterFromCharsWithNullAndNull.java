package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class AlphabetConverterTest_testCreateConverterFromCharsWithNullAndNull {

    @Test
    void testCreateConverterFromCharsWithNullAndNull() {
        // Both '$' entries are the same character, making the original alphabet effectively a singleton.
        // Passing null for encoding and doNotEncode should trigger IllegalArgumentException.
        final Character[] original = {'$', '$'};
        assertThrows(IllegalArgumentException.class,
                () -> AlphabetConverter.createConverterFromChars(original, null, null));
    }
}
