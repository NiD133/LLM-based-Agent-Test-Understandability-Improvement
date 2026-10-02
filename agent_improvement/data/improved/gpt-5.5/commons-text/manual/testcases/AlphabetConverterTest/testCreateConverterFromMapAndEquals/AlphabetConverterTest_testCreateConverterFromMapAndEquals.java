package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

public class AlphabetConverterTest_testCreateConverterFromMapAndEquals {

    @Test
    void testCreateConverterFromMapAndEquals() {
        final Map<Integer, String> originalToEncoded = new HashMap<>();

        final AlphabetConverter converterFromEmptyMap =
                AlphabetConverter.createConverterFromMap(originalToEncoded);

        originalToEncoded.put(0, "CtDs");
        final AlphabetConverter converterFromPopulatedMap =
                AlphabetConverter.createConverterFromMap(originalToEncoded);

        assertFalse(converterFromEmptyMap.equals(converterFromPopulatedMap));
        assertEquals(1, converterFromEmptyMap.getEncodedCharLength());
    }
}
