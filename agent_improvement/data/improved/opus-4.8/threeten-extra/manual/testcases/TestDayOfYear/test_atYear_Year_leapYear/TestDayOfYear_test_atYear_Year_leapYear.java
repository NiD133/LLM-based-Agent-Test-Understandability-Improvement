package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.Year;
import java.time.ZoneId;

import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.RetryingTest;

/**
 * Tests {@link DayOfYear#atYear(Year)} (and the two {@code now} factories) for a leap year.
 */
public class TestDayOfYear_test_atYear_Year_leapYear {

    /** 2008 is a leap year, so it has 366 days. */
    private static final Year LEAP_YEAR = Year.of(2008);
    private static final int LEAP_YEAR_LENGTH = 366;

    //-----------------------------------------------------------------------
    // now()
    //-----------------------------------------------------------------------
    @RetryingTest(100)
    public void test_now() {
        assertEquals(LocalDate.now().getDayOfYear(), DayOfYear.now().getValue());
    }

    @RetryingTest(100)
    public void test_now_ZoneId() {
        ZoneId tokyo = ZoneId.of("Asia/Tokyo");
        assertEquals(LocalDate.now(tokyo).getDayOfYear(), DayOfYear.now(tokyo).getValue());
    }

    //-----------------------------------------------------------------------
    // atYear(Year) across a full leap year
    //-----------------------------------------------------------------------
    @Test
    public void test_atYear_Year_leapYear() {
        // Walk every day of the leap year 2008: day-of-year i must map to the
        // i-th calendar date, starting at 2008-01-01 and running through 2008-12-31.
        LocalDate expectedDate = LocalDate.of(2008, 1, 1);
        for (int dayOfYear = 1; dayOfYear <= LEAP_YEAR_LENGTH; dayOfYear++) {
            DayOfYear test = DayOfYear.of(dayOfYear);
            assertEquals(expectedDate, test.atYear(LEAP_YEAR));
            expectedDate = expectedDate.plusDays(1);
        }
    }
}
