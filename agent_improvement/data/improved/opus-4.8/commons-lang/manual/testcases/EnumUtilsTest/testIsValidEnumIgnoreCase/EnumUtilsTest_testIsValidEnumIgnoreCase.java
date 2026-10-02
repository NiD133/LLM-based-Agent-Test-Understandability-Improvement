package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link EnumUtils#isValidEnumIgnoreCase(Class, String)}.
 *
 * <p>The {@code Traffic} enum used here declares the constants
 * {@code RED}, {@code AMBER} and {@code GREEN}.</p>
 */
public class EnumUtilsTest_testIsValidEnumIgnoreCase extends AbstractLangTest {

    @Test
    void testIsValidEnumIgnoreCase() {
        // A name that matches a constant is valid regardless of letter case.
        assertTrue(EnumUtils.isValidEnumIgnoreCase(Traffic.class, "red"));    // lower case
        assertTrue(EnumUtils.isValidEnumIgnoreCase(Traffic.class, "Amber"));  // mixed case
        assertTrue(EnumUtils.isValidEnumIgnoreCase(Traffic.class, "grEEn"));  // mixed case

        // A name that matches no constant is not valid.
        assertFalse(EnumUtils.isValidEnumIgnoreCase(Traffic.class, "purple"));

        // A null name is not valid.
        assertFalse(EnumUtils.isValidEnumIgnoreCase(Traffic.class, null));
    }
}
