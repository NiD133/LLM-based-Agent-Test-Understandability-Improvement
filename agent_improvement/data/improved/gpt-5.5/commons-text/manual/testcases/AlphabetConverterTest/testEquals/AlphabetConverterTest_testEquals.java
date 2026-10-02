package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

public class AlphabetConverterTest_testEquals {

    @Test
    void testEquals() {
        final Character[] repeatedOriginalAndEncodingCharacter = new Character[2];
        final char repeatedCharacter = 'R';
        repeatedOriginalAndEncodingCharacter[0] = repeatedCharacter;
        repeatedOriginalAndEncodingCharacter[1] = repeatedCharacter;

        final AlphabetConverter converterWithRepeatedCharacter = AlphabetConverter.createConverterFromChars(
                repeatedOriginalAndEncodingCharacter,
                repeatedOriginalAndEncodingCharacter,
                repeatedOriginalAndEncodingCharacter);

        final Map<Integer, String> emptyOriginalToEncodedMap = new HashMap<>();
        final AlphabetConverter converterFromEmptyMap = AlphabetConverter.createConverterFromMap(emptyOriginalToEncodedMap);

        assertEquals(1, converterFromEmptyMap.getEncodedCharLength());
        assertFalse(converterWithRepeatedCharacter.equals(converterFromEmptyMap));
    }
}
