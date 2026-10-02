package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class WordUtilsTest_testAbbreviateForLowerThanMinusOneValues {

    private static final String TEXT_TO_ABBREVIATE = "01 23 45 67 89";
    private static final int LOWER_LIMIT = 9;
    private static final int UPPER_LIMIT_BELOW_ALLOWED_MINIMUM = -10;

    @Test
    void testAbbreviateForLowerThanMinusOneValues() {
        assertThrows(IllegalArgumentException.class,
                () -> WordUtils.abbreviate(TEXT_TO_ABBREVIATE, LOWER_LIMIT, UPPER_LIMIT_BELOW_ALLOWED_MINIMUM, null));
    }
}
