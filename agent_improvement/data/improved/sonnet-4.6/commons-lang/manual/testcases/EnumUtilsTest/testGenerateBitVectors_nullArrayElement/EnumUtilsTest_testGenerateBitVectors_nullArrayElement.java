package org.apache.commons.lang3;

import static org.apache.commons.lang3.LangAssertions.assertIllegalArgumentException;

import org.junit.jupiter.api.Test;

public class EnumUtilsTest_testGenerateBitVectors_nullArrayElement extends AbstractLangTest {

    // generateBitVectors must reject a null element in a non-null varargs array
    @Test
    void testGenerateBitVectors_nullArrayElement() {
        assertIllegalArgumentException(() -> EnumUtils.generateBitVectors(Traffic.class, Traffic.RED, null));
    }
}
