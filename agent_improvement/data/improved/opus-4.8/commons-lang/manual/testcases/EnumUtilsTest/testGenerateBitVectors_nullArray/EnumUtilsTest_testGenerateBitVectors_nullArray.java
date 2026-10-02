package org.apache.commons.lang3;

import static org.apache.commons.lang3.LangAssertions.assertNullPointerException;

import org.junit.jupiter.api.Test;

public class EnumUtilsTest_testGenerateBitVectors_nullArray extends AbstractLangTest {

    /**
     * {@link EnumUtils#generateBitVectors(Class, Enum[])} must reject a {@code null}
     * varargs array by throwing a {@link NullPointerException}.
     */
    @Test
    void testGenerateBitVectors_nullArray() {
        final Traffic[] nullValues = null;

        assertNullPointerException(() -> EnumUtils.generateBitVectors(Traffic.class, nullValues));
    }
}
