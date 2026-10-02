package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.ZoneId;

import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.RetryingTest;

/**
 * Tests for {@link DayOfYear#adjustInto(java.time.temporal.Temporal)} and the
 * {@code now(...)} factory methods.
 */
public class TestDayOfYear_test_adjustInto_fromEndOfYear_notLeapYear {

    /** Number of days in a standard (non-leap) year. */
    private static final int STANDARD_YEAR_LENGTH = 365;

    //-----------------------------------------------------------------------
    @RetryingTest(100)
    public void test_now() {
        assertEquals(LocalDate.now().getDayOfYear(), DayOfYear.now().getValue());
    }

    //-----------------------------------------------------------------------
    @RetryingTest(100)
    public void test_now_ZoneId() {
        ZoneId tokyo = ZoneId.of("Asia/Tokyo");
        assertEquals(LocalDate.now(tokyo).getDayOfYear(), DayOfYear.now(tokyo).getValue());
    }

    //-----------------------------------------------------------------------
    /**
     * Adjusting a date that sits on the last day of a non-leap year must move it
     * to the requested day-of-year within that same year. Starting from
     * 2007-12-31, applying {@code DayOfYear.of(i)} should yield the i-th day of
     * 2007 for every day from 1 to 365.
     */
    @Test
    public void test_adjustInto_fromEndOfYear_notLeapYear() {
        LocalDate endOfYear = LocalDate.of(2007, 12, 31);

        LocalDate expectedDay = LocalDate.of(2007, 1, 1);
        for (int dayOfYear = 1; dayOfYear <= STANDARD_YEAR_LENGTH; dayOfYear++) {
            DayOfYear adjuster = DayOfYear.of(dayOfYear);

            assertEquals(expectedDay, adjuster.adjustInto(endOfYear));

            expectedDay = expectedDay.plusDays(1);
        }
    }
}
