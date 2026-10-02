package org.apache.commons.lang3;

import static org.apache.commons.lang3.LangAssertions.assertNullPointerException;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

public class EnumUtilsTest_testGenerateBitVectors_nullElement extends AbstractLangTest {

    private enum Traffic {
        RED
    }

    @Test
    void testGenerateBitVectors_nullElement() {
        assertNullPointerException(() -> EnumUtils.generateBitVectors(Traffic.class, Arrays.asList(Traffic.RED, null)));
    }
}
