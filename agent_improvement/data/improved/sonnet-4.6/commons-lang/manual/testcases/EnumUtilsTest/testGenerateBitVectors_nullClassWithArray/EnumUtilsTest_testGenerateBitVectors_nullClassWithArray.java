package org.apache.commons.lang3;

import static org.apache.commons.lang3.LangAssertions.assertNullPointerException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class EnumUtilsTest_testGenerateBitVectors_nullClassWithArray extends AbstractLangTest {

    @Test
    @DisplayName("generateBitVectors throws NullPointerException when enum class argument is null")
    void testGenerateBitVectors_nullClassWithArray() {
        assertNullPointerException(() -> EnumUtils.generateBitVectors(null, Traffic.RED));
    }
}
