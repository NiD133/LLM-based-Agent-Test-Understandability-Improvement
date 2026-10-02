package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link EnumUtils#getEnum(Class, String)}.
 *
 * <p>The two-argument overload looks up an enum constant by its exact name and,
 * unlike {@link Enum#valueOf(Class, String)}, returns {@code null} instead of
 * throwing when no matching constant exists. The lookups below use the
 * package-level {@code Traffic} enum, whose constants are {@code RED},
 * {@code AMBER} and {@code GREEN}.</p>
 */
public class EnumUtilsTest_testGetEnum extends AbstractLangTest {

    @Test
    void testGetEnum() {
        // A name that matches a constant resolves to that constant.
        assertEquals(Traffic.RED, EnumUtils.getEnum(Traffic.class, "RED"));
        assertEquals(Traffic.AMBER, EnumUtils.getEnum(Traffic.class, "AMBER"));
        assertEquals(Traffic.GREEN, EnumUtils.getEnum(Traffic.class, "GREEN"));

        // An unknown name yields null rather than throwing.
        assertNull(EnumUtils.getEnum(Traffic.class, "PURPLE"));

        // A null name also yields null.
        assertNull(EnumUtils.getEnum(Traffic.class, null));
    }
}
