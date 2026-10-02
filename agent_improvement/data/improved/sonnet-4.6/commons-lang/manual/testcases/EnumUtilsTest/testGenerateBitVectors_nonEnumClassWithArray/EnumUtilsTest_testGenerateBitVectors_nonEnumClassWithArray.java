package org.apache.commons.lang3;

import static org.apache.commons.lang3.LangAssertions.assertIllegalArgumentException;

import org.junit.jupiter.api.Test;

public class EnumUtilsTest_testGenerateBitVectors_nonEnumClassWithArray extends AbstractLangTest {

    @Test
    @SuppressWarnings({"unchecked", "rawtypes"})
    void testGenerateBitVectors_nonEnumClassWithArray() {
        // Object.class is not an enum type; generateBitVectors must reject it with IllegalArgumentException
        final Class nonEnumClass = Object.class;
        assertIllegalArgumentException(() -> EnumUtils.generateBitVectors(nonEnumClass));
    }
}
