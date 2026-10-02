package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

public class EnumUtilsTest_testGetEnumMap extends AbstractLangTest {

    private enum Traffic {
        RED,
        AMBER,
        GREEN
    }

    @Test
    void testGetEnumMap() {
        final Map<String, Traffic> enumMapByName = EnumUtils.getEnumMap(Traffic.class);
        final Map<String, Traffic> expectedEnumMapByName = new HashMap<>();
        expectedEnumMapByName.put("RED", Traffic.RED);
        expectedEnumMapByName.put("AMBER", Traffic.AMBER);
        expectedEnumMapByName.put("GREEN", Traffic.GREEN);

        assertEquals(expectedEnumMapByName, enumMapByName, "getEnumMap not created correctly");
        assertEquals(3, enumMapByName.size());
        assertTrue(enumMapByName.containsKey("RED"));
        assertEquals(Traffic.RED, enumMapByName.get("RED"));
        assertTrue(enumMapByName.containsKey("AMBER"));
        assertEquals(Traffic.AMBER, enumMapByName.get("AMBER"));
        assertTrue(enumMapByName.containsKey("GREEN"));
        assertEquals(Traffic.GREEN, enumMapByName.get("GREEN"));
        assertFalse(enumMapByName.containsKey("PURPLE"));
    }
}
