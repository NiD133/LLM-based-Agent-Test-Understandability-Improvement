package org.apache.commons.lang3;

import static org.apache.commons.lang3.LangAssertions.assertIllegalArgumentException;

import org.junit.jupiter.api.Test;

public class EnumUtilsTest_testGenerateBitVector_nullArrayElement extends AbstractLangTest {

    private enum Traffic {
        RED
    }

    @Test
    void testGenerateBitVector_nullArrayElement() {
        assertIllegalArgumentException(() -> EnumUtils.generateBitVector(Traffic.class, Traffic.RED, null));
    }
}
