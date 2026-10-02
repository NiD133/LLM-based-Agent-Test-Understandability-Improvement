package org.apache.commons.lang3;

import static org.apache.commons.lang3.LangAssertions.assertNullPointerException;

import org.junit.jupiter.api.Test;

public class EnumUtilsTest_testGenerateBitVectors_nullIterable extends AbstractLangTest {

    /**
     * Passing a {@code null} enum class to the {@code Iterable}-based overload of
     * {@code generateBitVectors} must raise a {@link NullPointerException}.
     */
    @Test
    void testGenerateBitVectors_nullIterable() {
        final Class<Traffic> nullEnumClass = null;
        final Iterable<Traffic> nullValues = null;

        assertNullPointerException(() -> EnumUtils.generateBitVectors(nullEnumClass, nullValues));
    }
}
