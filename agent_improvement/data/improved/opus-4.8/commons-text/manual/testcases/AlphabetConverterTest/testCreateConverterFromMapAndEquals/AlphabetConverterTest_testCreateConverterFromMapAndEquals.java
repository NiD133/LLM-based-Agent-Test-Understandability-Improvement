package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link AlphabetConverter#createConverterFromMap(Map)} together with
 * {@link AlphabetConverter#equals(Object)}.
 */
public class AlphabetConverterTest_testCreateConverterFromMapAndEquals {

    @Test
    void testCreateConverterFromMapAndEquals() {
        // createConverterFromMap stores the SAME map instance (wrapped as
        // unmodifiable), so later mutations of the map are visible to the
        // converter. We use this to build two converters that differ.
        final Map<Integer, String> originalToEncoded = new HashMap<>();

        // First converter is created while the map is still empty.
        final AlphabetConverter converterFromEmptyMap =
                AlphabetConverter.createConverterFromMap(originalToEncoded);

        // Mutate the backing map, then create a second converter from it.
        originalToEncoded.put(0, "CtDs");
        final AlphabetConverter converterFromPopulatedMap =
                AlphabetConverter.createConverterFromMap(originalToEncoded);

        // The two converters are not equal: they were built from different
        // map snapshots, so their internal encoding state differs.
        assertFalse(converterFromEmptyMap.equals(converterFromPopulatedMap));

        // The first converter was built from an empty map, so its encoded
        // char length stayed at the default of 1.
        assertEquals(1, converterFromEmptyMap.getEncodedCharLength());
    }
}
