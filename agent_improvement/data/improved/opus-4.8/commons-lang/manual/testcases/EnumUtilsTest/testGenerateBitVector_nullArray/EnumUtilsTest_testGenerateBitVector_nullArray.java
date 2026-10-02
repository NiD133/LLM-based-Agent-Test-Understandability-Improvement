package org.apache.commons.lang3;

import static org.apache.commons.lang3.LangAssertions.assertNullPointerException;

import org.junit.jupiter.api.Test;

public class EnumUtilsTest_testGenerateBitVector_nullArray extends AbstractLangTest {

    /**
     * Passing a {@code null} varargs array as the {@code values} argument must be rejected:
     * {@link EnumUtils#generateBitVector(Class, Enum[])} is expected to throw a
     * {@link NullPointerException} rather than silently treating it as an empty selection.
     */
    @Test
    void testGenerateBitVector_nullArray() {
        final Traffic[] nullValues = null;
        assertNullPointerException(() -> EnumUtils.generateBitVector(Traffic.class, nullValues));
    }
}
