package org.apache.commons.compress.harmony.unpack200;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class SegmentUtilsTest_testMatches {

    // Matches values divisible by a given divisor
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

    private static final IMatcher EVEN_MATCHER = new MultipleMatches(2);
    private static final IMatcher MULTIPLES_OF_FIVE_MATCHER = new MultipleMatches(5);

    // Contains 5 even numbers (2,4,6,8,10) and 2 multiples of five (5,10)
    private static final long[] ONE_TO_TEN = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };

    // Adds 1 more even number (6) and 1 more multiple of five (5) to any combined count
    private static final long[] FIVE_SIX_SEVEN = { 5, 6, 7 };

    @Test
    void countMatches_flatArray_evenMatcher_returnsFiveEvenNumbers() {
        assertEquals(5, SegmentUtils.countMatches(ONE_TO_TEN, EVEN_MATCHER));
    }

    @Test
    void countMatches_flatArray_multiplesOfFiveMatcher_returnsTwoMultiples() {
        assertEquals(2, SegmentUtils.countMatches(ONE_TO_TEN, MULTIPLES_OF_FIVE_MATCHER));
    }

    @Test
    void countMatches_2DArraySingleRow_evenMatcher_yieldsTheSameResultAsFlatArray() {
        assertEquals(5, SegmentUtils.countMatches(new long[][] { ONE_TO_TEN }, EVEN_MATCHER));
    }

    @Test
    void countMatches_2DArraySingleRow_multiplesOfFiveMatcher_yieldsTheSameResultAsFlatArray() {
        assertEquals(2, SegmentUtils.countMatches(new long[][] { ONE_TO_TEN }, MULTIPLES_OF_FIVE_MATCHER));
    }

    @Test
    void countMatches_2DArrayMultipleRows_evenMatcher_aggregatesAcrossAllRows() {
        // ONE_TO_TEN contributes 5; FIVE_SIX_SEVEN contributes 1 (the value 6) → total 6
        assertEquals(6, SegmentUtils.countMatches(new long[][] { ONE_TO_TEN, FIVE_SIX_SEVEN }, EVEN_MATCHER));
    }

    @Test
    void countMatches_2DArrayMultipleRows_multiplesOfFiveMatcher_aggregatesAcrossAllRows() {
        // ONE_TO_TEN contributes 2; FIVE_SIX_SEVEN contributes 1 (the value 5) → total 3
        assertEquals(3, SegmentUtils.countMatches(new long[][] { ONE_TO_TEN, FIVE_SIX_SEVEN }, MULTIPLES_OF_FIVE_MATCHER));
    }
}
