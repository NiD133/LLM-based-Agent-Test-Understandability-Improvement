package org.apache.commons.lang3;

import static org.apache.commons.lang3.LangAssertions.assertNullPointerException;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

public class EnumUtilsTest_testGenerateBitVector_nullElement extends AbstractLangTest {

    @Test
    void testGenerateBitVector_nullElement() {
        // generateBitVector must reject a list that contains a null element
        assertNullPointerException(() -> EnumUtils.generateBitVector(Traffic.class, Arrays.asList(Traffic.RED, null)));
    }
}
