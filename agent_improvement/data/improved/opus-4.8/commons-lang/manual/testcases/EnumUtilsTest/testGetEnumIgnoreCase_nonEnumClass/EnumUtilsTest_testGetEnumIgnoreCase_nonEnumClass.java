package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

public class EnumUtilsTest_testGetEnumIgnoreCase_nonEnumClass extends AbstractLangTest {

    /**
     * Verifies that {@link EnumUtils#getEnumIgnoreCase(Class, String)} returns {@code null}
     * when given a raw (non-enum) class such as {@link Object} instead of throwing.
     */
    @SuppressWarnings("unchecked")
    @Test
    void testGetEnumIgnoreCase_nonEnumClass() {
        @SuppressWarnings("rawtypes")
        final Class nonEnumClass = Object.class;

        assertNull(EnumUtils.getEnumIgnoreCase(nonEnumClass, "rawType"));
    }
}
