package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class WordUtilsTest_testAbbreviateForLowerValue {

    @Test
    void testAbbreviateForLowerValue() {
        assertAbbreviatesTo("012", "012 3456789", 0, 5);
        assertAbbreviatesTo("01234", "01234 56789", 5, 10);
        assertAbbreviatesTo("01 23 45 67", "01 23 45 67 89", 9, -1);
        assertAbbreviatesTo("01 23 45 6", "01 23 45 67 89", 9, 10);
        assertAbbreviatesTo("0123456789", "0123456789", 15, 20);
    }

    private static void assertAbbreviatesTo(final String expected, final String input, final int lower, final int upper) {
        assertEquals(expected, WordUtils.abbreviate(input, lower, upper, null));
    }
}
