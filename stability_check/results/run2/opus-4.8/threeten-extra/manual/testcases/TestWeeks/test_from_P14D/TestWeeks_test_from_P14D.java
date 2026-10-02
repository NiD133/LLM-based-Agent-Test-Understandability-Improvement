package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Period;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link Weeks#from(java.time.temporal.TemporalAmount)} converts a
 * {@link Period} into the equivalent number of whole weeks.
 */
public class TestWeeks_test_from_P14D {

    @Test
    public void from_period_of_14_days_yields_2_weeks() {
        // A period of 14 days is exactly 2 weeks, so the conversion must succeed.
        Weeks expected = Weeks.of(2);
        Weeks actual = Weeks.from(Period.ofDays(14));

        assertEquals(expected, actual);
    }
}
