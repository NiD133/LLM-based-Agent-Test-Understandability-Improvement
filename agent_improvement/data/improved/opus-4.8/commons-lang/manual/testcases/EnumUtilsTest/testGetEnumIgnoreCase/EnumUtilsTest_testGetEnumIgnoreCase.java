package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link EnumUtils#getEnumIgnoreCase(Class, String)}.
 *
 * <p>The method looks up an enum constant by name while ignoring case, and
 * returns {@code null} when no constant matches (instead of throwing).</p>
 *
 * <p>The {@code Traffic} enum used here declares the constants
 * {@code RED}, {@code AMBER} and {@code GREEN}.</p>
 */
public class EnumUtilsTest_testGetEnumIgnoreCase extends AbstractLangTest {

    @Test
    void testGetEnumIgnoreCase() {
        // A name that matches a constant regardless of case is resolved.
        assertEquals(Traffic.RED, EnumUtils.getEnumIgnoreCase(Traffic.class, "red"));    // all lower case
        assertEquals(Traffic.AMBER, EnumUtils.getEnumIgnoreCase(Traffic.class, "Amber")); // capitalized
        assertEquals(Traffic.GREEN, EnumUtils.getEnumIgnoreCase(Traffic.class, "grEEn")); // mixed case

        // A name with no matching constant yields null rather than an exception.
        assertNull(EnumUtils.getEnumIgnoreCase(Traffic.class, "purple"));

        // A null name also yields null.
        assertNull(EnumUtils.getEnumIgnoreCase(Traffic.class, null));
    }
}
