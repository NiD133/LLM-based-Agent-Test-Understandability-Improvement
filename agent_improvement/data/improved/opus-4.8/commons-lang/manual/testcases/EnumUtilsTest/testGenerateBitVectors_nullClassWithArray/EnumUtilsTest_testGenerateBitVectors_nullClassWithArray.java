package org.apache.commons.lang3;

import static org.apache.commons.lang3.LangAssertions.assertNullPointerException;

import org.junit.jupiter.api.Test;

public class EnumUtilsTest_testGenerateBitVectors_nullClassWithArray extends AbstractLangTest {

    @Test
    void testGenerateBitVectors_nullClassWithArray() {
        // Passing a null enum class (with a varargs array of values) must be rejected
        // with a NullPointerException rather than silently producing a bit vector.
        assertNullPointerException(() -> EnumUtils.generateBitVectors(null, Traffic.RED));
    }
}
