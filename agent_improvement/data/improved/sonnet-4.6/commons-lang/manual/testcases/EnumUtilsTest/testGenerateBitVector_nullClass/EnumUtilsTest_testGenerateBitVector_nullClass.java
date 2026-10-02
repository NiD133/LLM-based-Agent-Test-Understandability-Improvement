package org.apache.commons.lang3;

import static org.apache.commons.lang3.LangAssertions.assertNullPointerException;

import java.util.EnumSet;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class EnumUtilsTest_testGenerateBitVector_nullClass extends AbstractLangTest {

    @Test
    @DisplayName("generateBitVector throws NullPointerException when enumClass is null")
    void testGenerateBitVector_nullClass() {
        assertNullPointerException(() -> EnumUtils.generateBitVector(null, EnumSet.of(Traffic.RED)));
    }
}
