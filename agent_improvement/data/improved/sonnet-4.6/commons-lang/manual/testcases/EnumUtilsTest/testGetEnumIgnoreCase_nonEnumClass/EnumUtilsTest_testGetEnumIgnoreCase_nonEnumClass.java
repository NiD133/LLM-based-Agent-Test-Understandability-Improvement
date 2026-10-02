package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

public class EnumUtilsTest_testGetEnumIgnoreCase_nonEnumClass extends AbstractLangTest {

    /**
     * Verifies that {@link EnumUtils#getEnumIgnoreCase} returns {@code null}
     * when the supplied class is not an enum type (e.g. {@code Object.class}).
     * The method must not throw and must silently return {@code null} rather
     * than attempting to iterate enum constants on a plain class.
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    @Test
    void testGetEnumIgnoreCase_nonEnumClass() {
        // Object.class is an ordinary class, not an enum — use a raw Class to
        // bypass the generic bound and exercise the non-enum code path.
        final Class nonEnumClass = Object.class;

        final Object result = EnumUtils.getEnumIgnoreCase(nonEnumClass, "ANYTHING");

        assertNull(result, "getEnumIgnoreCase should return null for a non-enum class");
    }
}
