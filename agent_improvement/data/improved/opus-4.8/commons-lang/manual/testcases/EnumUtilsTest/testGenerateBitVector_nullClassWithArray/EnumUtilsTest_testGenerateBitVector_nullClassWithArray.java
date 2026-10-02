package org.apache.commons.lang3;

import static org.apache.commons.lang3.LangAssertions.assertNullPointerException;

import org.junit.jupiter.api.Test;

public class EnumUtilsTest_testGenerateBitVector_nullClassWithArray extends AbstractLangTest {

    /**
     * {@link EnumUtils#generateBitVector(Class, Enum...)} must reject a {@code null}
     * enum class by throwing a {@link NullPointerException}, even when valid enum
     * values are supplied in the varargs array.
     */
    @Test
    void testGenerateBitVector_nullClassWithArray() {
        assertNullPointerException(() -> EnumUtils.generateBitVector(null, Traffic.RED));
    }
}
