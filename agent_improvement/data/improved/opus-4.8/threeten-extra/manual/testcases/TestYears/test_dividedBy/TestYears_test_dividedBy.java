package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link Years#dividedBy(int)}.
 * <p>
 * The method uses integer division, so any remainder is truncated towards zero
 * (for example, 12 / 5 = 2). Dividing by a negative divisor flips the sign.
 */
public class TestYears_test_dividedBy {

    @Test
    public void test_dividedBy() {
        Years twelveYears = Years.of(12);

        // Exact divisions: the divisor evenly divides 12.
        assertEquals(Years.of(12), twelveYears.dividedBy(1), "12 / 1 = 12");
        assertEquals(Years.of(6), twelveYears.dividedBy(2), "12 / 2 = 6");
        assertEquals(Years.of(4), twelveYears.dividedBy(3), "12 / 3 = 4");
        assertEquals(Years.of(2), twelveYears.dividedBy(6), "12 / 6 = 2");

        // Inexact divisions: the remainder is truncated towards zero.
        assertEquals(Years.of(3), twelveYears.dividedBy(4), "12 / 4 = 3");
        assertEquals(Years.of(2), twelveYears.dividedBy(5), "12 / 5 = 2 (remainder dropped)");

        // Negative divisor: the result is negative.
        assertEquals(Years.of(-4), twelveYears.dividedBy(-3), "12 / -3 = -4");
    }
}
