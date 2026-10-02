package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.ZoneId;

import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.RetryingTest;

public class TestDayOfYear_test_from_TemporalAccessor_notLeapYear {

    private static final int STANDARD_YEAR_LENGTH = 365;

    //-----------------------------------------------------------------------
    @RetryingTest(100)
    public void test_now() {
        assertEquals(LocalDate.now().getDayOfYear(), DayOfYear.now().getValue());
    }

    //-----------------------------------------------------------------------
    @RetryingTest(100)
    public void test_now_ZoneId() {
        ZoneId zone = ZoneId.of("Asia/Tokyo");
        assertEquals(LocalDate.now(zone).getDayOfYear(), DayOfYear.now(zone).getValue());
    }

    //-----------------------------------------------------------------------
    @Test
    public void test_from_TemporalAccessor_notLeapYear() {
        // 2007 is a standard (non-leap) year; iterate all 365 days and verify each day-of-year value
        LocalDate date = LocalDate.of(2007, 1, 1);
        for (int i = 1; i <= STANDARD_YEAR_LENGTH; i++) {
            DayOfYear dayOfYear = DayOfYear.from(date);
            assertEquals(i, dayOfYear.getValue());
            date = date.plusDays(1);
        }
        // After exhausting 2007, date is Jan 1 2008; the day-of-year counter resets to 1
        DayOfYear firstDayOfNextYear = DayOfYear.from(date);
        assertEquals(1, firstDayOfNextYear.getValue());
    }
}
