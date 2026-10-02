package org.apache.commons.lang3;

import static org.apache.commons.lang3.LangAssertions.assertNullPointerException;

import java.lang.Iterable;

import org.junit.jupiter.api.Test;

public class EnumUtilsTest_testGenerateBitVectors_nullIterable extends AbstractLangTest {

    private enum Traffic {
        RED
    }

    @Test
    void testGenerateBitVectors_nullIterable() {
        assertNullPointerException(() -> EnumUtils.generateBitVectors(null, (Iterable<Traffic>) null));
    }
}
