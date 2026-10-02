package org.apache.commons.lang3;

import static org.apache.commons.lang3.LangAssertions.assertIllegalArgumentException;

import org.junit.jupiter.api.Test;

public class EnumUtilsTest_testGenerateBitVector_longClassWithArray extends AbstractLangTest {

    /**
     * {@link EnumUtils#generateBitVector(Class, Enum...)} encodes each enum constant as a bit in a
     * single {@code long}, so it only supports enums with at most 64 constants. {@code TooMany}
     * declares more than 64 constants, so passing it (via the varargs/array overload) must be
     * rejected with an {@link IllegalArgumentException}.
     */
    @Test
    void testGenerateBitVector_longClassWithArray() {
        assertIllegalArgumentException(() -> EnumUtils.generateBitVector(TooMany.class, TooMany.A1));
    }
}
