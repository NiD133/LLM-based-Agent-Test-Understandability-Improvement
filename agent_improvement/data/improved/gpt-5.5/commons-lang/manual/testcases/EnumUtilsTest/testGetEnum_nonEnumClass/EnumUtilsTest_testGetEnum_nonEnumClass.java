package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

public class EnumUtilsTest_testGetEnum_nonEnumClass extends AbstractLangTest {

    /**
     * Verifies that getEnum returns null when called through a raw Class reference
     * that does not actually represent an enum type.
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Test
    void testGetEnum_nonEnumClass() {
        final Class nonEnumClass = Object.class;

        assertNull(EnumUtils.getEnum(nonEnumClass, "rawType"));
    }
}
