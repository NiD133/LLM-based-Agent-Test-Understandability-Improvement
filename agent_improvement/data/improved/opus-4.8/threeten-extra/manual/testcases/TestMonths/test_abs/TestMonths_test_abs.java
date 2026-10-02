package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link Months#abs()}, which returns a copy of the amount with a
 * non-negative number of months.
 */
public class TestMonths_test_abs {

    @Test
    public void abs_returnsNonNegativeMonths() {
        // Zero stays zero.
        assertEquals(Months.of(0), Months.of(0).abs());

        // A positive amount is returned unchanged.
        assertEquals(Months.of(12), Months.of(12).abs());

        // A negative amount has its sign removed.
        assertEquals(Months.of(12), Months.of(-12).abs());

        // The largest positive amount is returned unchanged.
        assertEquals(Months.of(Integer.MAX_VALUE), Months.of(Integer.MAX_VALUE).abs());

        // The most negative amount that can be negated without overflow becomes positive.
        assertEquals(Months.of(Integer.MAX_VALUE), Months.of(-Integer.MAX_VALUE).abs());
    }
}
