package org.apache.commons.lang3;

import static org.apache.commons.lang3.LangAssertions.assertNullPointerException;

import java.util.EnumSet;

import org.junit.jupiter.api.Test;

public class EnumUtilsTest_testGenerateBitVectors_nullClass extends AbstractLangTest {

    @Test
    void testGenerateBitVectors_nullClass() {
        // A null enum class is invalid input, so generating bit vectors must fail fast.
        final EnumSet<Traffic> values = EnumSet.of(Traffic.RED);

        assertNullPointerException(() -> EnumUtils.generateBitVectors(null, values));
    }
}
