package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link Weeks#abs()}, which returns a copy of the amount with a
 * non-negative number of weeks.
 */
public class TestWeeks_test_abs {

    @Test
    public void test_abs() {
        // Zero stays zero.
        assertEquals(Weeks.of(0), Weeks.of(0).abs());

        // A positive amount is returned unchanged.
        assertEquals(Weeks.of(12), Weeks.of(12).abs());

        // A negative amount has its sign removed.
        assertEquals(Weeks.of(12), Weeks.of(-12).abs());

        // The largest positive amount is returned unchanged.
        assertEquals(Weeks.of(Integer.MAX_VALUE), Weeks.of(Integer.MAX_VALUE).abs());

        // The largest (in magnitude) negative amount becomes positive.
        assertEquals(Weeks.of(Integer.MAX_VALUE), Weeks.of(-Integer.MAX_VALUE).abs());
    }
}
