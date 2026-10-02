package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link Months#dividedBy(int)}.
 * <p>
 * Division uses integer (truncating) arithmetic, so any remainder is discarded.
 */
public class TestMonths_test_dividedBy {

    @Test
    public void dividedBy_usesTruncatingIntegerDivision() {
        Months twelveMonths = Months.of(12);

        // Exact divisions: 12 is evenly divisible by the divisor.
        assertEquals(Months.of(12), twelveMonths.dividedBy(1));
        assertEquals(Months.of(6), twelveMonths.dividedBy(2));
        assertEquals(Months.of(4), twelveMonths.dividedBy(3));
        assertEquals(Months.of(3), twelveMonths.dividedBy(4));

        // Inexact divisions: the remainder is truncated toward zero (12/5 = 2, 12/6 = 2).
        assertEquals(Months.of(2), twelveMonths.dividedBy(5));
        assertEquals(Months.of(2), twelveMonths.dividedBy(6));

        // Negative divisor yields a negative result (12 / -3 = -4).
        assertEquals(Months.of(-4), twelveMonths.dividedBy(-3));
    }
}
