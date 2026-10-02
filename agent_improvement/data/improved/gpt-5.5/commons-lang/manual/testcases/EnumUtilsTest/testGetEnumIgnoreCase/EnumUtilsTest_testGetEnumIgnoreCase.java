package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

public class EnumUtilsTest_testGetEnumIgnoreCase extends AbstractLangTest {

    private enum Traffic {
        RED,
        AMBER,
        GREEN
    }

    @Test
    void testGetEnumIgnoreCase() {
        assertEquals(Traffic.RED, EnumUtils.getEnumIgnoreCase(Traffic.class, "red"));
        assertEquals(Traffic.AMBER, EnumUtils.getEnumIgnoreCase(Traffic.class, "Amber"));
        assertEquals(Traffic.GREEN, EnumUtils.getEnumIgnoreCase(Traffic.class, "grEEn"));

        assertNull(EnumUtils.getEnumIgnoreCase(Traffic.class, "purple"));
        assertNull(EnumUtils.getEnumIgnoreCase(Traffic.class, null));
    }
}
