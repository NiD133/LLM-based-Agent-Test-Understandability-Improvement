package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.ZoneId;

import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.RetryingTest;

public class TestDayOfMonth_test_from_TemporalAccessor_notLeapYear {

    private static final int NON_LEAP_YEAR = 2007;
    private static final int[] DAYS_IN_MONTH_FOR_NON_LEAP_YEAR = {
            31, 28, 31, 30, 31, 30,
            31, 31, 30, 31, 30, 31
    };

    //-----------------------------------------------------------------------
    @RetryingTest(100)
    public void test_now() {
        assertEquals(LocalDate.now().getDayOfMonth(), DayOfMonth.now().getValue());
    }

    //-----------------------------------------------------------------------
    @RetryingTest(100)
    public void test_now_ZoneId() {
        ZoneId zone = ZoneId.of("Asia/Tokyo");
        assertEquals(LocalDate.now(zone).getDayOfMonth(), DayOfMonth.now(zone).getValue());
    }

    //-----------------------------------------------------------------------
    @Test
    public void test_from_TemporalAccessor_notLeapYear() {
        LocalDate date = LocalDate.of(NON_LEAP_YEAR, 1, 1);
        for (int daysInMonth : DAYS_IN_MONTH_FOR_NON_LEAP_YEAR) {
            date = assertDayOfMonthValues(date, daysInMonth);
        }
    }

    private LocalDate assertDayOfMonthValues(LocalDate date, int daysInMonth) {
        for (int dayOfMonth = 1; dayOfMonth <= daysInMonth; dayOfMonth++) {
            assertEquals(dayOfMonth, DayOfMonth.from(date).getValue());
            date = date.plusDays(1);
        }
        return date;
    }
}
