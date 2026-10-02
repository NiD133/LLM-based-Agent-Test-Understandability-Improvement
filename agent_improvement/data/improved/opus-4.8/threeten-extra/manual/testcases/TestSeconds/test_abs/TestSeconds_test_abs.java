package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link Seconds#abs()}, which returns a copy of the amount with the
 * sign removed (negative values are negated, zero and positive values are
 * returned unchanged).
 */
public class TestSeconds_test_abs {

    @Test
    public void test_abs() {
        // Zero stays zero.
        assertEquals(Seconds.of(0), Seconds.of(0).abs());

        // A positive amount is returned unchanged.
        assertEquals(Seconds.of(12), Seconds.of(12).abs());

        // A negative amount becomes its positive counterpart.
        assertEquals(Seconds.of(12), Seconds.of(-12).abs());

        // The largest positive amount is returned unchanged.
        assertEquals(Seconds.of(Integer.MAX_VALUE), Seconds.of(Integer.MAX_VALUE).abs());

        // The most negative amount that can be safely negated flips to positive.
        assertEquals(Seconds.of(Integer.MAX_VALUE), Seconds.of(-Integer.MAX_VALUE).abs());
    }
}
