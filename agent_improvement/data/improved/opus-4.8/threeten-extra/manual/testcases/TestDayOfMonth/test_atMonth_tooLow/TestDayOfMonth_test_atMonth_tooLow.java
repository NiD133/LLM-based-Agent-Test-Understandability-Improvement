package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link DayOfMonth#atMonth(int)} rejects a month value below the
 * valid range of 1 (January) to 12 (December).
 */
public class TestDayOfMonth_test_atMonth_tooLow {

    /** An arbitrary valid day-of-month used as the receiver under test. */
    private static final DayOfMonth DAY_12 = DayOfMonth.of(12);

    @Test
    public void atMonth_withMonthBelowMinimum_throwsDateTimeException() {
        int monthBelowMinimum = 0;

        assertThrows(DateTimeException.class, () -> DAY_12.atMonth(monthBelowMinimum));
    }
}
