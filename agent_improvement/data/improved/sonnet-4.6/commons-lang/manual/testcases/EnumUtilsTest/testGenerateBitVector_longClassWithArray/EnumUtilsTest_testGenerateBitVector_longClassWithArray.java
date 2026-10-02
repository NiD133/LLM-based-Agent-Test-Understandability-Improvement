package org.apache.commons.lang3;

import static org.apache.commons.lang3.LangAssertions.assertIllegalArgumentException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class EnumUtilsTest_testGenerateBitVector_longClassWithArray extends AbstractLangTest {

    @Test
    @DisplayName("generateBitVector throws IllegalArgumentException when enum has more than 64 constants")
    void testGenerateBitVector_longClassWithArray() {
        // TooMany has more than 64 enum constants, which exceeds the capacity of a long bit vector
        assertIllegalArgumentException(() -> EnumUtils.generateBitVector(TooMany.class, TooMany.A1));
    }
}
