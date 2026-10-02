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
        final Map<String, Traffic> enumMap = EnumUtils.getEnumMap(Traffic.class);

        // Verify the map contains exactly the expected entries
        final Map<String, Traffic> expectedMap = new HashMap<>();
        expectedMap.put("RED", Traffic.RED);
        expectedMap.put("AMBER", Traffic.AMBER);
        expectedMap.put("GREEN", Traffic.GREEN);
        assertEquals(expectedMap, enumMap, "getEnumMap not created correctly");
        assertEquals(3, enumMap.size());

        // Verify each valid enum constant is accessible by its name
        assertTrue(enumMap.containsKey("RED"));
        assertEquals(Traffic.RED, enumMap.get("RED"));
        assertTrue(enumMap.containsKey("AMBER"));
        assertEquals(Traffic.AMBER, enumMap.get("AMBER"));
        assertTrue(enumMap.containsKey("GREEN"));
        assertEquals(Traffic.GREEN, enumMap.get("GREEN"));

        // Verify non-existent keys are absent
        assertFalse(enumMap.containsKey("PURPLE"));
    }
}
