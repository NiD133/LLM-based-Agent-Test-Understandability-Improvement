package org.apache.commons.lang3;

import static org.apache.commons.lang3.LangAssertions.assertIllegalArgumentException;
import org.junit.jupiter.api.Test;

public class EnumUtilsTest_testGenerateBitVector_nonEnumClassWithArray extends AbstractLangTest {

    /**
     * Verifies that generateBitVector throws IllegalArgumentException when the supplied
     * class is not an enum type (Object.class is used as the representative non-enum class).
     * A raw Class reference is required here to bypass the compile-time generic constraint
     * <E extends Enum<E>>, which would otherwise prevent passing a non-enum class.
     */
    @SuppressWarnings("unchecked")
    @Test
    void testGenerateBitVector_nonEnumClassWithArray() {
        @SuppressWarnings("rawtypes")
        final Class nonEnumClass = Object.class;
        assertIllegalArgumentException(() -> EnumUtils.generateBitVector(nonEnumClass));
    }
}
