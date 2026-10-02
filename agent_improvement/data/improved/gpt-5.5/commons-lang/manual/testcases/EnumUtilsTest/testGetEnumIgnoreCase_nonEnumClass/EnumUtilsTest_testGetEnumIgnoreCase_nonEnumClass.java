package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

public class EnumUtilsTest_testGetEnumIgnoreCase_nonEnumClass extends AbstractLangTest {

    /**
     * A non-enum raw class should not resolve to an enum constant.
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Test
    void testGetEnumIgnoreCase_nonEnumClass() {
        final Class rawType = Object.class;

        assertNull(EnumUtils.getEnumIgnoreCase(rawType, "rawType"));
    }
}
