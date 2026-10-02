package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.ZoneId;

import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.RetryingTest;

/**
 * Tests for {@link DayOfMonth} construction via {@code DayOfMonth.of(int)} and the
 * {@code now(...)} factory methods, focusing on rejection of an out-of-range day.
 */
public class TestDayOfMonth_test_of_int_tooLow {

    //-----------------------------------------------------------------------
    // now() / now(ZoneId) report the same day-of-month as the equivalent LocalDate
    //-----------------------------------------------------------------------
    @RetryingTest(100)
    public void now_matchesCurrentDayOfMonthInDefaultZone() {
        int expectedDayOfMonth = LocalDate.now().getDayOfMonth();

        assertEquals(expectedDayOfMonth, DayOfMonth.now().getValue());
    }

    @RetryingTest(100)
    public void now_matchesCurrentDayOfMonthInGivenZone() {
        ZoneId tokyo = ZoneId.of("Asia/Tokyo");
        int expectedDayOfMonth = LocalDate.now(tokyo).getDayOfMonth();

        assertEquals(expectedDayOfMonth, DayOfMonth.now(tokyo).getValue());
    }

    //-----------------------------------------------------------------------
    // of(int) rejects values below the valid range of 1..31
    //-----------------------------------------------------------------------
    @Test
    public void of_dayBelowMinimum_throwsDateTimeException() {
        int tooLowDayOfMonth = 0;

        assertThrows(DateTimeException.class, () -> DayOfMonth.of(tooLowDayOfMonth));
    }
}
