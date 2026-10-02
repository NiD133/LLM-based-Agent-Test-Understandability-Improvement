package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link Hours#dividedBy(int)}.
 * <p>
 * Division uses integer arithmetic, so any remainder is discarded
 * (for example, 12 hours divided by 5 yields 2 hours).
 */
public class TestHours_test_dividedBy {

    @Test
    public void test_dividedBy() {
        Hours twelveHours = Hours.of(12);

        // exact divisions
        assertEquals(Hours.of(12), twelveHours.dividedBy(1));
        assertEquals(Hours.of(6), twelveHours.dividedBy(2));
        assertEquals(Hours.of(4), twelveHours.dividedBy(3));
        assertEquals(Hours.of(3), twelveHours.dividedBy(4));

        // inexact divisions truncate the remainder towards zero
        assertEquals(Hours.of(2), twelveHours.dividedBy(5)); // 12 / 5 == 2
        assertEquals(Hours.of(2), twelveHours.dividedBy(6)); // 12 / 6 == 2

        // negative divisor yields a negative result
        assertEquals(Hours.of(-4), twelveHours.dividedBy(-3));
    }
}
