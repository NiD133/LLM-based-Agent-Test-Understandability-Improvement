package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.ZoneId;

import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.RetryingTest;

public class TestDayOfMonth_test_from_TemporalAccessor_leapYear {

    //-----------------------------------------------------------------------
    // now()
    //-----------------------------------------------------------------------
    @RetryingTest(100)
    public void test_now() {
        assertEquals(LocalDate.now().getDayOfMonth(), DayOfMonth.now().getValue());
    }

    //-----------------------------------------------------------------------
    // now(ZoneId)
    //-----------------------------------------------------------------------
    @RetryingTest(100)
    public void test_now_ZoneId() {
        ZoneId tokyo = ZoneId.of("Asia/Tokyo");
        assertEquals(LocalDate.now(tokyo).getDayOfMonth(), DayOfMonth.now(tokyo).getValue());
    }

    //-----------------------------------------------------------------------
    // from(TemporalAccessor)
    //-----------------------------------------------------------------------
    @Test
    public void test_from_TemporalAccessor_leapYear() {
        // Walk day-by-day from 1 Jan 2008 (a leap year) and verify that
        // DayOfMonth.from(date) always reports the calendar day-of-month.
        // February has 29 days because 2008 is a leap year.
        LocalDate date = LocalDate.of(2008, 1, 1);

        date = assertDaysOfMonth(date, 31); // January
        date = assertDaysOfMonth(date, 29); // February (leap year)
        date = assertDaysOfMonth(date, 31); // March
    }

    /**
     * Asserts that {@code DayOfMonth.from(date)} returns 1, 2, ..., daysInMonth
     * on consecutive days, then returns the date immediately after the last
     * asserted day.
     */
    private static LocalDate assertDaysOfMonth(LocalDate date, int daysInMonth) {
        for (int expectedDay = 1; expectedDay <= daysInMonth; expectedDay++) {
            assertEquals(expectedDay, DayOfMonth.from(date).getValue());
            date = date.plusDays(1);
        }
        return date;
    }
}
