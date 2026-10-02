package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

/**
 * Verifies {@link AlphabetConverter#equals(Object)} for two converters that are
 * built differently and therefore must not be considered equal.
 */
public class AlphabetConverterTest_testEquals {

    @Test
    void testEquals() {
        // Converter built from the single-character alphabet {'R'}.
        // The same array is reused for the original, encoding and do-not-encode
        // alphabets, so it maps 'R' onto itself with an encoded length of 1.
        final Character[] singleLetterAlphabet = { 'R', 'R' };
        final AlphabetConverter converterFromChars =
                AlphabetConverter.createConverterFromChars(
                        singleLetterAlphabet, singleLetterAlphabet, singleLetterAlphabet);

        // Converter rebuilt from an empty mapping: it encodes nothing, but its
        // encoded length still defaults to 1.
        final Map<Integer, String> emptyMapping = new HashMap<>();
        final AlphabetConverter converterFromEmptyMap =
                AlphabetConverter.createConverterFromMap(emptyMapping);
        assertEquals(1, converterFromEmptyMap.getEncodedCharLength());

        // The two converters hold different mappings, so they are not equal.
        assertNotEquals(converterFromChars, converterFromEmptyMap);
    }
}
