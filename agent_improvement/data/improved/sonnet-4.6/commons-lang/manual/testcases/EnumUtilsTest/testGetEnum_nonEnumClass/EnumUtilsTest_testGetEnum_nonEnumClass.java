package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

public class EnumUtilsTest_testGetEnum_nonEnumClass extends AbstractLangTest {

    /**
     * Verifies that {@link EnumUtils#getEnum} returns {@code null} rather than
     * throwing an exception when the supplied class is not an enum type.
     *
     * <p>A raw {@code Class} reference is used to bypass the generic type bound
     * and pass a plain {@code Object.class} as the enum class argument.</p>
     */
    @SuppressWarnings("unchecked")
    @Test
    void testGetEnum_nonEnumClass() {
        @SuppressWarnings("rawtypes")
        final Class nonEnumClass = Object.class;
        assertNull(EnumUtils.getEnum(nonEnumClass, "rawType"));
    }
}
