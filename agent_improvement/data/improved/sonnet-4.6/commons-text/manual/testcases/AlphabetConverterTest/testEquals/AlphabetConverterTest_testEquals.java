package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

public class AlphabetConverterTest_testEquals {

    @Test
    void testEquals() {
        // Build a converter where the single character 'R' maps to itself
        final Character[] singleCharAlphabet = { 'R', 'R' };
        final AlphabetConverter converterFromChars =
                AlphabetConverter.createConverterFromChars(singleCharAlphabet, singleCharAlphabet, singleCharAlphabet);

        // Build a converter from an empty map — produces a trivially empty converter
        final Map<Integer, String> emptyMap = new HashMap<>();
        final AlphabetConverter converterFromEmptyMap = AlphabetConverter.createConverterFromMap(emptyMap);

        // An empty-map converter defaults to an encoded character length of 1
        assertEquals(1, converterFromEmptyMap.getEncodedCharLength());

        // The two converters have different internal mappings, so they must not be equal
        assertFalse(converterFromChars.equals(converterFromEmptyMap));
    }
}
