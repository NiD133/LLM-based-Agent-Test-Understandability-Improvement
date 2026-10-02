package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

public class EnumUtilsTest_testGetEnumIgnoreCase_nullClass extends AbstractLangTest {

    @Test
    void testGetEnumIgnoreCase_nullClass() {
        // A null enum class should return null rather than throw NullPointerException
        assertNull(EnumUtils.getEnumIgnoreCase((Class<Traffic>) null, "PURPLE"));
    }
}
