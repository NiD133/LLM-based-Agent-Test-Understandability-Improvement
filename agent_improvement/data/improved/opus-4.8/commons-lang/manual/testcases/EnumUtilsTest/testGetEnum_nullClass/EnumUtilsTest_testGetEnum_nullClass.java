package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link EnumUtils#getEnum(Class, String)} when the enum class argument is {@code null}.
 */
public class EnumUtilsTest_testGetEnum_nullClass extends AbstractLangTest {

    /**
     * When the enum class is {@code null}, {@code getEnum} should return {@code null}
     * rather than throwing an exception, regardless of the supplied enum name.
     */
    @Test
    void testGetEnum_nullClass() {
        final Class<Traffic> nullEnumClass = null;

        assertNull(EnumUtils.getEnum(nullEnumClass, "PURPLE"));
    }
}
