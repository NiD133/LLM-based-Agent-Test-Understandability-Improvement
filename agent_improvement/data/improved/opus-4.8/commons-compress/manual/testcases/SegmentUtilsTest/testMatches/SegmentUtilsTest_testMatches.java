package org.apache.commons.compress.harmony.unpack200;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link SegmentUtils#countMatches}, which counts how many flag values
 * satisfy a given {@link IMatcher}, for both the flat ({@code long[]}) and the
 * two-dimensional ({@code long[][]}) overloads.
 */
public class SegmentUtilsTest_testMatches {

    /**
     * Matches every value that is an exact multiple of the configured divisor.
     */
    private static final class MultipleMatches implements IMatcher {

        private final int divisor;

        MultipleMatches(final int divisor) {
            this.divisor = divisor;
        }

        @Override
        public boolean matches(final long value) {
            return value % divisor == 0;
        }
    }

    /** Matches multiples of 2 (i.e. even values). */
    private static final IMatcher multiplesOfTwo = new MultipleMatches(2);

    /** Matches multiples of 5. */
    private static final IMatcher multiplesOfFive = new MultipleMatches(5);

    @Test
    void testMatches() {
        final long[] oneToTen = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
        final long[] fiveSixSeven = { 5, 6, 7 };
        final long[][] bothRows = { oneToTen, fiveSixSeven };
        final long[][] singleRow = { oneToTen };

        // Multiples of 2: {2,4,6,8,10} in oneToTen plus {6} in fiveSixSeven.
        assertEquals(6, SegmentUtils.countMatches(bothRows, multiplesOfTwo));
        assertEquals(5, SegmentUtils.countMatches(singleRow, multiplesOfTwo));
        assertEquals(5, SegmentUtils.countMatches(oneToTen, multiplesOfTwo));

        // Multiples of 5: {5,10} in oneToTen plus {5} in fiveSixSeven.
        assertEquals(3, SegmentUtils.countMatches(bothRows, multiplesOfFive));
        assertEquals(2, SegmentUtils.countMatches(singleRow, multiplesOfFive));
        assertEquals(2, SegmentUtils.countMatches(oneToTen, multiplesOfFive));
    }
}
