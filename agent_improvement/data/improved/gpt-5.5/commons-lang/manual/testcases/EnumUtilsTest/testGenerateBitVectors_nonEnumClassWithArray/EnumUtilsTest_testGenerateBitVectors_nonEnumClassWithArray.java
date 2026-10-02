package org.apache.commons.lang3;

import static org.apache.commons.lang3.LangAssertions.assertIllegalArgumentException;

import org.junit.jupiter.api.Test;

public class EnumUtilsTest_testGenerateBitVectors_nonEnumClassWithArray extends AbstractLangTest {

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Test
    void testGenerateBitVectors_nonEnumClassWithArray() {
        final Class nonEnumClass = Object.class;

        assertIllegalArgumentException(() -> EnumUtils.generateBitVectors(nonEnumClass));
    }
}
