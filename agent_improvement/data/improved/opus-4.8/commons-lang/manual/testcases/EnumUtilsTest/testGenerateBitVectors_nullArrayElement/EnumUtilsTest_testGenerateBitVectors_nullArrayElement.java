package org.apache.commons.lang3;

import static org.apache.commons.lang3.LangAssertions.assertIllegalArgumentException;

import org.junit.jupiter.api.Test;

public class EnumUtilsTest_testGenerateBitVectors_nullArrayElement extends AbstractLangTest {

    /**
     * {@link EnumUtils#generateBitVectors(Class, Enum...)} must reject a varargs
     * array that contains a {@code null} element. Here the array is
     * {@code [Traffic.RED, null]}, so the call is expected to fail with an
     * {@link IllegalArgumentException}.
     */
    @Test
    void testGenerateBitVectors_nullArrayElement() {
        assertIllegalArgumentException(
            () -> EnumUtils.generateBitVectors(Traffic.class, Traffic.RED, null));
    }
}
