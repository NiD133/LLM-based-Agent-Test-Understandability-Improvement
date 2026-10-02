package org.apache.commons.lang3;

import static org.apache.commons.lang3.LangAssertions.assertIllegalArgumentException;

import org.junit.jupiter.api.Test;

public class EnumUtilsTest_testGenerateBitVector_nonEnumClassWithArray extends AbstractLangTest {

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Test
    void testGenerateBitVector_nonEnumClassWithArray() {
        final Class nonEnumClass = Object.class;

        assertIllegalArgumentException(() -> EnumUtils.generateBitVector(nonEnumClass));
    }
}
