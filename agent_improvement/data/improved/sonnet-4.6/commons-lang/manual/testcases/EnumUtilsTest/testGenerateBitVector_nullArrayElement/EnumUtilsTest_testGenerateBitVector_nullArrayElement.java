package org.apache.commons.lang3;

import static org.apache.commons.lang3.LangAssertions.assertIllegalArgumentException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class EnumUtilsTest_testGenerateBitVector_nullArrayElement extends AbstractLangTest {

    @Test
    @DisplayName("generateBitVector throws IllegalArgumentException when a null element is present in the varargs array")
    void testGenerateBitVector_nullArrayElement() {
        // Traffic.RED is a valid enum constant, but the trailing null should be rejected
        assertIllegalArgumentException(() -> EnumUtils.generateBitVector(Traffic.class, Traffic.RED, null));
    }
}
