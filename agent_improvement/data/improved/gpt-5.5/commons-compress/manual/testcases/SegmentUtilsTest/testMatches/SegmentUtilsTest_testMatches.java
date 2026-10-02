package org.apache.commons.compress.harmony.unpack200;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class SegmentUtilsTest_testMatches {

    private static final IMatcher EVEN_VALUES = value -> value % 2 == 0;
    private static final IMatcher MULTIPLES_OF_FIVE = value -> value % 5 == 0;

    @Test
    void testMatches() {
        final long[] oneToTen = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
        final long[][] oneToTenOnly = { oneToTen };
        final long[][] oneToTenWithFiveSixSeven = { oneToTen, new long[] { 5, 6, 7 } };

        assertEquals(6, SegmentUtils.countMatches(oneToTenWithFiveSixSeven, EVEN_VALUES));
        assertEquals(5, SegmentUtils.countMatches(oneToTenOnly, EVEN_VALUES));
        assertEquals(5, SegmentUtils.countMatches(oneToTen, EVEN_VALUES));

        assertEquals(3, SegmentUtils.countMatches(oneToTenWithFiveSixSeven, MULTIPLES_OF_FIVE));
        assertEquals(2, SegmentUtils.countMatches(oneToTenOnly, MULTIPLES_OF_FIVE));
        assertEquals(2, SegmentUtils.countMatches(oneToTen, MULTIPLES_OF_FIVE));
    }
}
