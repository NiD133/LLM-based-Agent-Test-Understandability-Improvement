package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

public class EnumUtilsTest_testGetEnumIgnoreCase_nullClass extends AbstractLangTest {

    private enum Traffic {
    }

    @Test
    void getEnumIgnoreCaseReturnsNullWhenEnumClassIsNull() {
        assertNull(EnumUtils.getEnumIgnoreCase((Class<Traffic>) null, "PURPLE"));
    }
}
