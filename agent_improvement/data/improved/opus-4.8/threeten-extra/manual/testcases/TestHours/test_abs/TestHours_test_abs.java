package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link Hours#abs()}, which returns an {@code Hours} whose amount is the
 * absolute value of this amount.
 */
public class TestHours_test_abs {

    @Test
    public void abs_returnsAbsoluteValueOfHours() {
        // Zero stays zero.
        assertEquals(Hours.of(0), Hours.of(0).abs());

        // A positive amount is unchanged.
        assertEquals(Hours.of(12), Hours.of(12).abs());

        // A negative amount becomes its positive counterpart.
        assertEquals(Hours.of(12), Hours.of(-12).abs());

        // The largest positive amount is unchanged.
        assertEquals(Hours.of(Integer.MAX_VALUE), Hours.of(Integer.MAX_VALUE).abs());

        // The corresponding negative amount becomes positive.
        assertEquals(Hours.of(Integer.MAX_VALUE), Hours.of(-Integer.MAX_VALUE).abs());
    }
}
