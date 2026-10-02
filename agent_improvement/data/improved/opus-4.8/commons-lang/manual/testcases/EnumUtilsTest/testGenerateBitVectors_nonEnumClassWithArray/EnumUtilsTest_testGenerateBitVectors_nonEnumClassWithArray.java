package org.apache.commons.lang3;

import static org.apache.commons.lang3.LangAssertions.assertIllegalArgumentException;

import org.junit.jupiter.api.Test;

public class EnumUtilsTest_testGenerateBitVectors_nonEnumClassWithArray extends AbstractLangTest {

    /**
     * {@code generateBitVectors} requires an enum class. Calling it with a plain,
     * non-enum class (here {@code Object.class}) must raise an
     * {@link IllegalArgumentException}.
     */
    @SuppressWarnings("unchecked")
    @Test
    void testGenerateBitVectors_nonEnumClassWithArray() {
        @SuppressWarnings("rawtypes")
        final Class nonEnumClass = Object.class;
        assertIllegalArgumentException(() -> EnumUtils.generateBitVectors(nonEnumClass));
    }
}
