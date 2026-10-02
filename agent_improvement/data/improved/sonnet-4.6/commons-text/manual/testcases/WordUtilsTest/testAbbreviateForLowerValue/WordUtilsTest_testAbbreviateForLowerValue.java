package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class WordUtilsTest_testAbbreviateForLowerValue {

    @Test
    void testAbbreviateForLowerValue() {
        // lower=0: abbreviates at first space found after position 0, within upper limit of 5
        assertEquals("012", WordUtils.abbreviate("012 3456789", 0, 5, null));

        // lower=5: abbreviates at first space found at or after position 5, within upper limit of 10
        assertEquals("01234", WordUtils.abbreviate("01234 56789", 5, 10, null));

        // upper=-1 (no upper limit): abbreviates at last space before end of string, lower=9
        assertEquals("01 23 45 67", WordUtils.abbreviate("01 23 45 67 89", 9, -1, null));

        // lower=9, upper=10: abbreviates at first space after position 9, capped at position 10
        assertEquals("01 23 45 6", WordUtils.abbreviate("01 23 45 67 89", 9, 10, null));

        // lower > string length: lower is clamped to string length, so no abbreviation occurs
        assertEquals("0123456789", WordUtils.abbreviate("0123456789", 15, 20, null));
    }
}
