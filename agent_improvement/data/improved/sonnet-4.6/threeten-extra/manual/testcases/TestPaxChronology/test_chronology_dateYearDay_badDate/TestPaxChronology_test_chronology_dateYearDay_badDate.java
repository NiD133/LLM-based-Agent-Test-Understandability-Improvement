package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link PaxChronology#dateYearDay(int, int)} rejects an invalid day-of-year.
 * <p>
 * In the Pax calendar, year 2001 is not a leap year (its last two digits, 01,
 * are not divisible by 6, not 99, and not 00), so it has only 364 days.
 * Requesting day 365 must therefore throw {@link DateTimeException}.
 */
@SuppressWarnings("static-method")
public class TestPaxChronology_test_chronology_dateYearDay_badDate {

    @Test
    public void test_chronology_dateYearDay_badDate() {
        // Year 2001 has 364 days; day 365 is out of range
        assertThrows(DateTimeException.class,
                () -> PaxChronology.INSTANCE.dateYearDay(2001, 365));
    }
}
