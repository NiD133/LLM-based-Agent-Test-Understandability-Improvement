package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link EnumUtils#getEnum(Class, String)} when the supplied class is not
 * an enum type.
 */
public class EnumUtilsTest_testGetEnum_nonEnumClass extends AbstractLangTest {

    /**
     * Looking up an enum constant on a non-enum class (a raw {@link Object}
     * class) must return {@code null} rather than throwing.
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Test
    void testGetEnum_nonEnumClass() {
        final Class nonEnumClass = Object.class;

        assertNull(EnumUtils.getEnum(nonEnumClass, "rawType"));
    }
}
