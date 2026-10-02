package org.apache.commons.lang3;

import static org.apache.commons.lang3.LangAssertions.assertNullPointerException;

import java.util.EnumSet;

import org.junit.jupiter.api.Test;

public class EnumUtilsTest_testGenerateBitVector_nullClass extends AbstractLangTest {

    private enum Traffic {
        RED
    }

    @Test
    void testGenerateBitVector_nullClass() {
        assertNullPointerException(() -> EnumUtils.generateBitVector(null, EnumSet.of(Traffic.RED)));
    }
}
