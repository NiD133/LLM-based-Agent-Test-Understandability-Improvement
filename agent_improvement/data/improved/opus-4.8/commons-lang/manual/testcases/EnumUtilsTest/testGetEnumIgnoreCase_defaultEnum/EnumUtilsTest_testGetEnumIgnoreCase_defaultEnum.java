package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link EnumUtils#getEnumIgnoreCase(Class, String, Enum)}, the three-argument
 * variant that falls back to a caller-supplied default when no enum constant matches.
 *
 * <p>The {@code Traffic} enum used here declares the constants {@code RED}, {@code AMBER}
 * and {@code GREEN}.</p>
 */
public class EnumUtilsTest_testGetEnumIgnoreCase_defaultEnum extends AbstractLangTest {

    @Test
    void testGetEnumIgnoreCase_defaultEnum() {
        // A name that matches a constant (ignoring case) resolves to that constant,
        // and the default is ignored.
        assertEquals(Traffic.RED, EnumUtils.getEnumIgnoreCase(Traffic.class, "red", Traffic.AMBER));
        assertEquals(Traffic.AMBER, EnumUtils.getEnumIgnoreCase(Traffic.class, "Amber", Traffic.GREEN));
        assertEquals(Traffic.GREEN, EnumUtils.getEnumIgnoreCase(Traffic.class, "grEEn", Traffic.RED));

        // A name with no matching constant ("PURPLE") falls back to the supplied default.
        assertEquals(Traffic.AMBER, EnumUtils.getEnumIgnoreCase(Traffic.class, "PURPLE", Traffic.AMBER));
        assertEquals(Traffic.GREEN, EnumUtils.getEnumIgnoreCase(Traffic.class, "purple", Traffic.GREEN));
        assertEquals(Traffic.RED, EnumUtils.getEnumIgnoreCase(Traffic.class, "pUrPlE", Traffic.RED));

        // A null name also falls back to the supplied default.
        assertEquals(Traffic.AMBER, EnumUtils.getEnumIgnoreCase(Traffic.class, null, Traffic.AMBER));
        assertEquals(Traffic.GREEN, EnumUtils.getEnumIgnoreCase(Traffic.class, null, Traffic.GREEN));
        assertEquals(Traffic.RED, EnumUtils.getEnumIgnoreCase(Traffic.class, null, Traffic.RED));

        // When the default itself is null, an unmatched name or null class returns null.
        assertNull(EnumUtils.getEnumIgnoreCase(Traffic.class, "PURPLE", null));
        assertNull(EnumUtils.getEnumIgnoreCase(null, "PURPLE", null));
    }
}
