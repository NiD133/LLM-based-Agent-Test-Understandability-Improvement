package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class EnumUtilsTest_testIsValidEnumIgnoreCase_nullClass extends AbstractLangTest {

    @Test
    @DisplayName("isValidEnumIgnoreCase returns false when enumClass is null")
    void testIsValidEnumIgnoreCase_nullClass() {
        assertFalse(EnumUtils.isValidEnumIgnoreCase(null, "PURPLE"));
    }
}
