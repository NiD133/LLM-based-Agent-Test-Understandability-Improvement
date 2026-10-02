package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

public class EnumUtilsTest_testIsValidEnumIgnoreCase_nullClass extends AbstractLangTest {

    /**
     * Verifies that {@link EnumUtils#isValidEnumIgnoreCase(Class, String)} returns
     * {@code false} when the enum class argument is {@code null}, as documented by
     * the method contract ("null returns false").
     */
    @Test
    void testIsValidEnumIgnoreCase_nullClass() {
        assertFalse(EnumUtils.isValidEnumIgnoreCase(null, "PURPLE"));
    }
}
