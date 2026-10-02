package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

public class EnumUtilsTest_testGetEnumIgnoreCase_defaultEnum extends AbstractLangTest {

    private enum Traffic {
        RED,
        AMBER,
        GREEN
    }

    @Test
    void testGetEnumIgnoreCase_defaultEnum() {
        assertEquals(Traffic.RED, EnumUtils.getEnumIgnoreCase(Traffic.class, "red", Traffic.AMBER));
        assertEquals(Traffic.AMBER, EnumUtils.getEnumIgnoreCase(Traffic.class, "Amber", Traffic.GREEN));
        assertEquals(Traffic.GREEN, EnumUtils.getEnumIgnoreCase(Traffic.class, "grEEn", Traffic.RED));

        assertEquals(Traffic.AMBER, EnumUtils.getEnumIgnoreCase(Traffic.class, "PURPLE", Traffic.AMBER));
        assertEquals(Traffic.GREEN, EnumUtils.getEnumIgnoreCase(Traffic.class, "purple", Traffic.GREEN));
        assertEquals(Traffic.RED, EnumUtils.getEnumIgnoreCase(Traffic.class, "pUrPlE", Traffic.RED));

        assertEquals(Traffic.AMBER, EnumUtils.getEnumIgnoreCase(Traffic.class, null, Traffic.AMBER));
        assertEquals(Traffic.GREEN, EnumUtils.getEnumIgnoreCase(Traffic.class, null, Traffic.GREEN));
        assertEquals(Traffic.RED, EnumUtils.getEnumIgnoreCase(Traffic.class, null, Traffic.RED));

        assertNull(EnumUtils.getEnumIgnoreCase(Traffic.class, "PURPLE", null));
        assertNull(EnumUtils.getEnumIgnoreCase(null, "PURPLE", null));
    }
}
