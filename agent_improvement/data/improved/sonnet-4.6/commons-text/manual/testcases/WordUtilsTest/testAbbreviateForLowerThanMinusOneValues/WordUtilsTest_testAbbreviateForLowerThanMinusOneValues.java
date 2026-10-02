package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class WordUtilsTest_testAbbreviateForLowerThanMinusOneValues {

    @Test
    void testAbbreviateForLowerThanMinusOneValues() {
        // upper < -1 is illegal; -1 means "no limit" but any lower value is invalid
        assertThrows(IllegalArgumentException.class,
                () -> WordUtils.abbreviate("01 23 45 67 89", 9, -10, null));
    }
}
