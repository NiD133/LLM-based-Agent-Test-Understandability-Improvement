package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class EnumUtilsTest_testIsValidEnum extends AbstractLangTest {

    private enum Traffic {
        RED,
        AMBER,
        GREEN
    }

    @Test
    void testIsValidEnum() {
        assertValidTrafficSignalNames();
        assertInvalidTrafficSignalNames();
    }

    private void assertValidTrafficSignalNames() {
        assertTrue(EnumUtils.isValidEnum(Traffic.class, "RED"));
        assertTrue(EnumUtils.isValidEnum(Traffic.class, "AMBER"));
        assertTrue(EnumUtils.isValidEnum(Traffic.class, "GREEN"));
    }

    private void assertInvalidTrafficSignalNames() {
        assertFalse(EnumUtils.isValidEnum(Traffic.class, "PURPLE"));
        assertFalse(EnumUtils.isValidEnum(Traffic.class, null));
    }
}
