package org.apache.commons.lang3;

import static org.apache.commons.lang3.LangAssertions.assertNullPointerException;

import org.junit.jupiter.api.Test;

public class EnumUtilsTest_testGenerateBitVector_nullIterable extends AbstractLangTest {

    private enum Traffic {
        RED
    }

    @Test
    void testGenerateBitVector_nullIterable() {
        assertNullPointerException(() -> EnumUtils.generateBitVector(Traffic.class, (Iterable<Traffic>) null));
    }
}
