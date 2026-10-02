package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link AlphabetConverter#createConverterFromChars} rejects an
 * attempt to build a converter when no encoding alphabet is supplied.
 */
public class AlphabetConverterTest_testCreateConverterFromCharsWithNullAndNull {

    @Test
    void testCreateConverterFromCharsWithNullAndNull() {
        // Original alphabet consisting of a single (duplicated) character.
        final Character[] originalAlphabet = { '$', '$' };
        final Character[] noEncodingAlphabet = null;
        final Character[] noDoNotEncodeChars = null;

        // Building a converter is impossible without at least two encoding
        // characters, so an IllegalArgumentException is expected.
        assertThrows(IllegalArgumentException.class, () ->
                AlphabetConverter.createConverterFromChars(
                        originalAlphabet, noEncodingAlphabet, noDoNotEncodeChars));
    }
}
