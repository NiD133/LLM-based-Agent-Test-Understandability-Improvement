package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class EnumUtilsTest_testIsValidEnum extends AbstractLangTest {

    /**
     * Verifies {@link EnumUtils#isValidEnum(Class, String)}, which reports whether a
     * given name corresponds to a constant of the {@link Traffic} enum
     * (constants: RED, AMBER, GREEN).
     */
    @Test
    void testIsValidEnum() {
        // Names that match an actual Traffic constant are valid.
        assertTrue(EnumUtils.isValidEnum(Traffic.class, "RED"));
        assertTrue(EnumUtils.isValidEnum(Traffic.class, "AMBER"));
        assertTrue(EnumUtils.isValidEnum(Traffic.class, "GREEN"));

        // A name with no matching constant is not valid.
        assertFalse(EnumUtils.isValidEnum(Traffic.class, "PURPLE"));

        // A null name is not valid (rather than throwing).
        assertFalse(EnumUtils.isValidEnum(Traffic.class, null));
    }
}
