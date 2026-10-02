package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

public class EnumUtilsTest_testIsValidEnum_nullClass extends AbstractLangTest {

    /**
     * Verifies that {@link EnumUtils#isValidEnum(Class, String)} returns {@code false}
     * when the enum class is {@code null}, regardless of the supplied name.
     */
    @Test
    void testIsValidEnum_nullClass() {
        final String anyName = "PURPLE";

        assertFalse(EnumUtils.isValidEnum(null, anyName),
                "A null enum class should never produce a valid enum");
    }
}
