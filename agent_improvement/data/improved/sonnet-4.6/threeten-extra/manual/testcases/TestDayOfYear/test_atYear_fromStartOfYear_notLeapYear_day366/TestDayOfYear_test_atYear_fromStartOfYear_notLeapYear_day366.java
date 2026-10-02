package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.Year;
import java.time.ZoneId;

import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.RetryingTest;

public class TestDayOfYear_test_atYear_fromStartOfYear_notLeapYear_day366 {

    // A non-leap year (365 days)
    private static final Year YEAR_STANDARD = Year.of(2007);

    // Day 366 only exists in a leap year
    private static final int LEAP_YEAR_LENGTH = 366;

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

    // Day 366 does not exist in a non-leap year, so atYear must throw DateTimeException
    @Test
    public void test_atYear_fromStartOfYear_notLeapYear_day366() {
        DayOfYear day366 = DayOfYear.of(LEAP_YEAR_LENGTH);
        assertThrows(DateTimeException.class, () -> day366.atYear(YEAR_STANDARD));
    }
}
