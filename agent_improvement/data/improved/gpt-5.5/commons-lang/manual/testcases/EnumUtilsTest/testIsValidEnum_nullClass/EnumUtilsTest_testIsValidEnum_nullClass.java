package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

public class EnumUtilsTest_testIsValidEnum_nullClass extends AbstractLangTest {

    @Test
    void testIsValidEnum_nullClass() {
        assertFalse(EnumUtils.isValidEnum(null, "PURPLE"));
    }
}
