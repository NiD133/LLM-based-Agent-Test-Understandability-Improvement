package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link Days#abs()}, which returns a copy of the amount with a
 * non-negative (positive or zero) number of days.
 */
public class TestDays_test_abs {

    @Test
    public void abs_returnsAbsoluteValueOfDays() {
        // Zero stays zero.
        assertEquals(Days.of(0), Days.of(0).abs());

        // A positive amount is returned unchanged.
        assertEquals(Days.of(12), Days.of(12).abs());

        // A negative amount has its sign removed.
        assertEquals(Days.of(12), Days.of(-12).abs());

        // The largest positive amount is returned unchanged.
        assertEquals(Days.of(Integer.MAX_VALUE), Days.of(Integer.MAX_VALUE).abs());

        // The most negative representable amount (-Integer.MAX_VALUE) is negated.
        assertEquals(Days.of(Integer.MAX_VALUE), Days.of(-Integer.MAX_VALUE).abs());
    }
}
