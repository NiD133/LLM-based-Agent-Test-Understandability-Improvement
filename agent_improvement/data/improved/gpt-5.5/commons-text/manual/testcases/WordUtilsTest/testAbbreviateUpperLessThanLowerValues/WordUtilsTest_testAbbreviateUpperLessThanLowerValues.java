package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class WordUtilsTest_testAbbreviateUpperLessThanLowerValues {

    @Test
    void testAbbreviateUpperLessThanLowerValues() {
        final String text = "0123456789";
        final int lowerLimit = 5;
        final int upperLimitBelowLowerLimit = 2;
        final String appendToEnd = "";

        assertThrows(IllegalArgumentException.class,
                () -> WordUtils.abbreviate(text, lowerLimit, upperLimitBelowLowerLimit, appendToEnd));
    }
}
