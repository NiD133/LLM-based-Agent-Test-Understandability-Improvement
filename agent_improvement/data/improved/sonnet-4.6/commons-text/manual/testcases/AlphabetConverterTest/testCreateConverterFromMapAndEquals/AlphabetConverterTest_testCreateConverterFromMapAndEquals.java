package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

public class AlphabetConverterTest_testCreateConverterFromMapAndEquals {

    @Test
    void testCreateConverterFromMapAndEquals() {
        // Build a converter from an empty map; encoded char length defaults to 1
        final Map<Integer, String> encodingMap = new HashMap<>();
        final AlphabetConverter converterFromEmptyMap = AlphabetConverter.createConverterFromMap(encodingMap);

        // Mutate the shared map and create a second converter with the added entry
        encodingMap.put(0, "CtDs");
        final AlphabetConverter converterFromPopulatedMap = AlphabetConverter.createConverterFromMap(encodingMap);

        // Converters built from different map states must not be equal
        assertNotEquals(converterFromEmptyMap, converterFromPopulatedMap);

        // An empty-map converter has the minimum encoded char length of 1
        assertEquals(1, converterFromEmptyMap.getEncodedCharLength());
    }
}
