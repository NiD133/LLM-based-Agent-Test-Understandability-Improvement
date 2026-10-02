package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

public class EnumUtilsTest_testGetEnum_nullClass extends AbstractLangTest {

    private enum Traffic {
    }

    @Test
    void testGetEnumReturnsNullWhenEnumClassIsNull() {
        assertNull(EnumUtils.getEnum((Class<Traffic>) null, "PURPLE"));
    }
}
