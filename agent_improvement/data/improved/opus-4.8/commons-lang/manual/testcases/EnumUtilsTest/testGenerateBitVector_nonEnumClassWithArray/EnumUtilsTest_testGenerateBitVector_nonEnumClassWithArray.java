package org.apache.commons.lang3;

import static org.apache.commons.lang3.LangAssertions.assertIllegalArgumentException;
import org.junit.jupiter.api.Test;

public class EnumUtilsTest_testGenerateBitVector_nonEnumClassWithArray extends AbstractLangTest {

    /**
     * {@link EnumUtils#generateBitVector(Class, Enum...)} must reject a class that is
     * not an enum. Here we pass {@code Object.class} (with no values) and expect an
     * {@link IllegalArgumentException}.
     */
    @SuppressWarnings("unchecked")
    @Test
    void testGenerateBitVector_nonEnumClassWithArray() {
        @SuppressWarnings("rawtypes")
        final Class nonEnumClass = Object.class;
        assertIllegalArgumentException(() -> EnumUtils.generateBitVector(nonEnumClass));
    }
}
