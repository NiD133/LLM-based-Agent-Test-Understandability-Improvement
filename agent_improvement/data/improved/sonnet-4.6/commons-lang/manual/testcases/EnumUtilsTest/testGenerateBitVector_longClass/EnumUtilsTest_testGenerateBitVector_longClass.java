package org.apache.commons.lang3;

import static org.apache.commons.lang3.LangAssertions.assertIllegalArgumentException;

import java.util.EnumSet;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class EnumUtilsTest_testGenerateBitVector_longClass extends AbstractLangTest {

    @Test
    @DisplayName("generateBitVector throws IllegalArgumentException when enum has more than 64 values")
    void testGenerateBitVector_longClass() {
        // TooMany has more than 64 constants, which exceeds the capacity of a single long
        assertIllegalArgumentException(() -> EnumUtils.generateBitVector(TooMany.class, EnumSet.of(TooMany.A1)));
    }
}
