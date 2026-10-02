package org.apache.commons.lang3;

import static org.apache.commons.lang3.LangAssertions.assertNullPointerException;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

public class EnumUtilsTest_testGenerateBitVector_nullElement extends AbstractLangTest {

    private enum Traffic {
        RED
    }

    @Test
    void testGenerateBitVector_nullElement() {
        final Iterable<Traffic> valuesWithNullElement = Arrays.asList(Traffic.RED, null);

        assertNullPointerException(() -> EnumUtils.generateBitVector(Traffic.class, valuesWithNullElement));
    }
}
