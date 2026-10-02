package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

public class EnumUtilsTest_testGetEnumIgnoreCase_nullClass extends AbstractLangTest {

    /**
     * When the enum class is {@code null}, {@link EnumUtils#getEnumIgnoreCase(Class, String)}
     * must return {@code null} instead of throwing, regardless of the supplied name.
     */
    @Test
    void testGetEnumIgnoreCase_nullClass() {
        final Class<Traffic> nullEnumClass = null;

        assertNull(EnumUtils.getEnumIgnoreCase(nullEnumClass, "PURPLE"));
    }
}
