package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

public class EnumUtilsTest_testGetEnumMap extends AbstractLangTest {

    @Test
    void testGetEnumMap() {
        final Map<String, Traffic> actualMap = EnumUtils.getEnumMap(Traffic.class);

        // The map should contain exactly one entry per enum constant, keyed by its name.
        final Map<String, Traffic> expectedMap = new HashMap<>();
        expectedMap.put("RED", Traffic.RED);
        expectedMap.put("AMBER", Traffic.AMBER);
        expectedMap.put("GREEN", Traffic.GREEN);
        assertEquals(expectedMap, actualMap, "getEnumMap not created correctly");

        // Verify the size and each name-to-constant mapping individually.
        assertEquals(3, actualMap.size());
        assertTrue(actualMap.containsKey("RED"));
        assertEquals(Traffic.RED, actualMap.get("RED"));
        assertTrue(actualMap.containsKey("AMBER"));
        assertEquals(Traffic.AMBER, actualMap.get("AMBER"));
        assertTrue(actualMap.containsKey("GREEN"));
        assertEquals(Traffic.GREEN, actualMap.get("GREEN"));

        // A name that is not an enum constant must be absent.
        assertFalse(actualMap.containsKey("PURPLE"));
    }
}
