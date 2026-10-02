package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Period;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link Weeks#from(java.time.temporal.TemporalAmount)} for a day-based period.
 */
public class TestWeeks_test_from_P14D {

    /**
     * A 14-day period is an exact multiple of 7 days, so it converts to 2 whole weeks.
     */
    @Test
    public void from_periodOf14Days_returnsTwoWeeks() {
        Weeks result = Weeks.from(Period.ofDays(14));

        assertEquals(Weeks.of(2), result);
    }
}
