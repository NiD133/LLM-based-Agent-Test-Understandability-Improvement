package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.Year;
import java.time.ZoneId;

import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.RetryingTest;

/**
 * Tests for {@link DayOfYear}, focusing on combining a day-of-year with a year
 * via {@link DayOfYear#atYear(Year)}.
 */
public class TestDayOfYear_test_atYear_fromStartOfYear_notLeapYear_day366 {

    /** Day-of-year 366, which only exists in a leap year. */
    private static final int LEAP_DAY_OF_YEAR = 366;

    /** A standard (non-leap) year, which therefore has no 366th day. */
    private static final Year NON_LEAP_YEAR = Year.of(2007);

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
    /**
     * Attaching day-of-year 366 to a non-leap year is invalid, because a
     * standard year only has 365 days, so {@code atYear} must reject it.
     */
    @Test
    public void test_atYear_fromStartOfYear_notLeapYear_day366() {
        DayOfYear day366 = DayOfYear.of(LEAP_DAY_OF_YEAR);

        assertThrows(DateTimeException.class, () -> day366.atYear(NON_LEAP_YEAR));
    }
}
