package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.ZoneId;

import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.RetryingTest;

public class TestDayOfYear_test_atYear_int_fromStartOfYear_notLeapYear_day366 {

    /** Day-of-year 366 only exists in a leap year. */
    private static final int LEAP_YEAR_ONLY_DAY = 366;

    /** A standard (non-leap) year, used as the invalid target for day 366. */
    private static final int NON_LEAP_YEAR = 2007;

    //-----------------------------------------------------------------------
    @RetryingTest(100)
    public void test_now() {
        int expectedDayOfYear = LocalDate.now().getDayOfYear();
        assertEquals(expectedDayOfYear, DayOfYear.now().getValue());
    }

    //-----------------------------------------------------------------------
    @RetryingTest(100)
    public void test_now_ZoneId() {
        ZoneId tokyo = ZoneId.of("Asia/Tokyo");
        int expectedDayOfYear = LocalDate.now(tokyo).getDayOfYear();
        assertEquals(expectedDayOfYear, DayOfYear.now(tokyo).getValue());
    }

    //-----------------------------------------------------------------------
    @Test
    public void test_atYear_int_fromStartOfYear_notLeapYear_day366() {
        // Day 366 cannot be placed in a non-leap year, so atYear must reject it.
        DayOfYear day366 = DayOfYear.of(LEAP_YEAR_ONLY_DAY);
        assertThrows(DateTimeException.class, () -> day366.atYear(NON_LEAP_YEAR));
    }
}
