package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Month;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link Half#from(java.time.temporal.TemporalAccessor)}.
 * <p>
 * Half-of-year mapping under test:
 * <ul>
 *   <li>January to June belong to the first half ({@link Half#H1}).</li>
 *   <li>July to December belong to the second half ({@link Half#H2}).</li>
 *   <li>A {@code Half} passed in is returned unchanged.</li>
 * </ul>
 */
public class TestHalf_test_from_TemporalAccessor_Month {

    @Test
    public void from_month_returns_matching_half() {
        // First half of the year: January through June.
        assertEquals(Half.H1, Half.from(Month.JANUARY));
        assertEquals(Half.H1, Half.from(Month.FEBRUARY));
        assertEquals(Half.H1, Half.from(Month.MARCH));
        assertEquals(Half.H1, Half.from(Month.APRIL));
        assertEquals(Half.H1, Half.from(Month.MAY));
        assertEquals(Half.H1, Half.from(Month.JUNE));

        // Second half of the year: July through December.
        assertEquals(Half.H2, Half.from(Month.JULY));
        assertEquals(Half.H2, Half.from(Month.AUGUST));
        assertEquals(Half.H2, Half.from(Month.SEPTEMBER));
        assertEquals(Half.H2, Half.from(Month.OCTOBER));
        assertEquals(Half.H2, Half.from(Month.NOVEMBER));
        assertEquals(Half.H2, Half.from(Month.DECEMBER));
    }

    @Test
    public void from_half_returns_same_half() {
        // A Half is itself a TemporalAccessor and is returned unchanged.
        assertEquals(Half.H1, Half.from(Half.H1));
        assertEquals(Half.H2, Half.from(Half.H2));
    }
}
