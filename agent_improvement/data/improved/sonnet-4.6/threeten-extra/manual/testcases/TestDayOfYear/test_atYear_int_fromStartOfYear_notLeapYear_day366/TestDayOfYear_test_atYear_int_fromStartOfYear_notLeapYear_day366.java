package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.ZoneId;

import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.RetryingTest;

public class TestDayOfYear_test_atYear_int_fromStartOfYear_notLeapYear_day366 {

    // Day 366 only exists in leap years
    private static final int LEAP_YEAR_ONLY_DAY = 366;

    // 2007 is a non-leap year (365 days), so day 366 is invalid for it
    private static final int NON_LEAP_YEAR = 2007;

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

    @Test
    public void test_atYear_int_fromStartOfYear_notLeapYear_day366() {
        // Day 366 cannot be placed in a non-leap year — atYear must reject it
        DayOfYear test = DayOfYear.of(LEAP_YEAR_ONLY_DAY);
        assertThrows(DateTimeException.class, () -> test.atYear(NON_LEAP_YEAR));
    }
}
