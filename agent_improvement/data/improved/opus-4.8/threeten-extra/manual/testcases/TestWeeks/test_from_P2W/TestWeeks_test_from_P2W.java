package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Period;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link Weeks#from(java.time.temporal.TemporalAmount)} converts a
 * {@link Period} expressed purely in weeks into the matching {@link Weeks} value.
 */
public class TestWeeks_test_from_P2W {

    @Test
    public void from_periodOfTwoWeeks_returnsWeeksOfTwo() {
        Period twoWeeksPeriod = Period.ofWeeks(2);

        Weeks result = Weeks.from(twoWeeksPeriod);

        assertEquals(Weeks.of(2), result);
    }
}
