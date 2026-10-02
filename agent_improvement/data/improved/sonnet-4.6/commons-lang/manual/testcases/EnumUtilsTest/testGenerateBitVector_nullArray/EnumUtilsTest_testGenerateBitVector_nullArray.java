package org.apache.commons.lang3;

import static org.apache.commons.lang3.LangAssertions.assertNullPointerException;

import org.junit.jupiter.api.Test;

public class EnumUtilsTest_testGenerateBitVector_nullArray extends AbstractLangTest {

    @Test
    void testGenerateBitVector_nullArray() {
        assertNullPointerException(() -> EnumUtils.generateBitVector(Traffic.class, (Traffic[]) null));
    }
}
