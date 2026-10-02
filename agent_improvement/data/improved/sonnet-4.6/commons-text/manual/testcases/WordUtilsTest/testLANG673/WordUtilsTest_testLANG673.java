package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class WordUtilsTest_testLANG673 {

    @Test
    void testLANG673() {
        // Regression test: abbreviate should cut at the first space on or after `lower`,
        // not at exactly `lower` characters, and must handle lower > string length.
        final String input = "01 23 45 67 89";
        final int upper = 40;
        final String noAppend = "";

        // lower=0: first space is at index 2, so only the first word is kept
        assertEquals("01", WordUtils.abbreviate(input, 0, upper, noAppend));

        // lower=10: first space at or after index 10 is at index 11, so four words are kept
        assertEquals("01 23 45 67", WordUtils.abbreviate(input, 10, upper, noAppend));

        // lower >= length: nothing to abbreviate, full string is returned
        assertEquals("01 23 45 67 89", WordUtils.abbreviate(input, 40, upper, noAppend));
    }
}
