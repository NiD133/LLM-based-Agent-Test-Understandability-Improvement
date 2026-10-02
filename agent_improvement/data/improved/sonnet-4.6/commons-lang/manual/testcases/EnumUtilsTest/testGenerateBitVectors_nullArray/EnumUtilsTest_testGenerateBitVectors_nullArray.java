package org.apache.commons.lang3;

import static org.apache.commons.lang3.LangAssertions.assertNullPointerException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class EnumUtilsTest_testGenerateBitVectors_nullArray extends AbstractLangTest {

    @Test
    @DisplayName("generateBitVectors throws NullPointerException when the values array is null")
    void testGenerateBitVectors_nullArray() {
        assertNullPointerException(() -> EnumUtils.generateBitVectors(Traffic.class, (Traffic[]) null));
    }
}
