package org.apache.commons.lang3;

import static org.apache.commons.lang3.LangAssertions.assertNullPointerException;

import org.junit.jupiter.api.Test;

public class EnumUtilsTest_testGenerateBitVector_nullClassWithArray extends AbstractLangTest {

    private enum Traffic {
        RED
    }

    @Test
    void testGenerateBitVector_nullClassWithArray() {
        assertNullPointerException(() -> EnumUtils.generateBitVector(null, Traffic.RED));
    }
}
