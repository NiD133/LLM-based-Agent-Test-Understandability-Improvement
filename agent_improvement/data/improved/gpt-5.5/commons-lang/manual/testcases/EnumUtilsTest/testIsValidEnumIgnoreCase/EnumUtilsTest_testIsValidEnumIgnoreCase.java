package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class EnumUtilsTest_testIsValidEnumIgnoreCase extends AbstractLangTest {

    private enum Traffic {
        RED,
        AMBER,
        GREEN
    }

    @Test
    void testIsValidEnumIgnoreCase() {
        assertTrue(EnumUtils.isValidEnumIgnoreCase(Traffic.class, "red"));
        assertTrue(EnumUtils.isValidEnumIgnoreCase(Traffic.class, "Amber"));
        assertTrue(EnumUtils.isValidEnumIgnoreCase(Traffic.class, "grEEn"));

        assertFalse(EnumUtils.isValidEnumIgnoreCase(Traffic.class, "purple"));
        assertFalse(EnumUtils.isValidEnumIgnoreCase(Traffic.class, null));
    }
}
