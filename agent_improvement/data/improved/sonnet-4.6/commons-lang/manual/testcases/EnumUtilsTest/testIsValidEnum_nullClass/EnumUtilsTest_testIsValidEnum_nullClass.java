package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class EnumUtilsTest_testIsValidEnum_nullClass extends AbstractLangTest {

    @Test
    @DisplayName("isValidEnum returns false when enum class is null")
    void testIsValidEnum_nullClass() {
        assertFalse(EnumUtils.isValidEnum(null, "PURPLE"));
    }
}
