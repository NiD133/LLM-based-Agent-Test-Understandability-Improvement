package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link Half#length(boolean)}, the day-count of each half-year.
 * <p>
 * H1 (January–June) spans 181 days in a standard year and 182 in a leap year,
 * because February gains a day. H2 (July–December) always spans 184 days,
 * regardless of leap year.
 */
public class TestHalf_test_length_boolean {

    @Test
    public void length_firstHalf_dependsOnLeapYear() {
        assertEquals(182, Half.H1.length(true), "H1 in a leap year");
        assertEquals(181, Half.H1.length(false), "H1 in a standard year");
    }

    @Test
    public void length_secondHalf_isAlways184() {
        assertEquals(184, Half.H2.length(true), "H2 in a leap year");
        assertEquals(184, Half.H2.length(false), "H2 in a standard year");
    }
}
