package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;
import java.time.Period;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link Weeks#plus(java.time.temporal.TemporalAmount)} when the amount
 * to add cannot be expressed as a whole number of weeks.
 */
public class TestWeeks_test_plus_TemporalAmount_PeriodMonths {

    /**
     * Adding a month-based {@link Period} must fail, because months cannot be
     * converted to a whole number of weeks.
     */
    @Test
    public void plus_periodOfMonths_throwsDateTimeException() {
        Weeks oneWeek = Weeks.of(1);
        Period twoMonths = Period.ofMonths(2);

        assertThrows(DateTimeException.class, () -> oneWeek.plus(twoMonths));
    }
}
