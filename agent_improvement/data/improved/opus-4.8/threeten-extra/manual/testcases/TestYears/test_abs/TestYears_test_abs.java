package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests for {@link Years#abs()}.
 * <p>
 * {@code abs()} returns the amount with its sign removed, so a negative number
 * of years becomes positive while zero and positive amounts are returned unchanged.
 */
public class TestYears_test_abs {

    @Test
    public void abs_returnsAmountWithoutSign() {
        // Zero stays zero.
        assertEquals(Years.of(0), Years.of(0).abs());

        // A positive amount is returned unchanged.
        assertEquals(Years.of(12), Years.of(12).abs());

        // A negative amount becomes its positive counterpart.
        assertEquals(Years.of(12), Years.of(-12).abs());

        // The largest positive amount is returned unchanged.
        assertEquals(Years.of(Integer.MAX_VALUE), Years.of(Integer.MAX_VALUE).abs());

        // The largest negative amount (still in int range) becomes positive.
        assertEquals(Years.of(Integer.MAX_VALUE), Years.of(-Integer.MAX_VALUE).abs());
    }
}
