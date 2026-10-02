package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.ZoneId;

import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.RetryingTest;

/**
 * Tests {@link DayOfYear#atYear(int)} for a non-leap (standard) year, plus the
 * two {@code now(...)} factory methods.
 */
public class TestDayOfYear_test_atYear_int_notLeapYear {

    /** Number of days in a standard (non-leap) year. */
    private static final int STANDARD_YEAR_LENGTH = 365;

    /** A non-leap year used to combine with each day-of-year. */
    private static final int STANDARD_YEAR = 2007;

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
    @Test
    public void test_atYear_int_notLeapYear() {
        // Walking day-of-year 1..365 through a standard year must map to
        // consecutive calendar dates starting at January 1st.
        LocalDate expectedDate = LocalDate.of(STANDARD_YEAR, 1, 1);
        for (int dayOfYear = 1; dayOfYear <= STANDARD_YEAR_LENGTH; dayOfYear++) {
            DayOfYear test = DayOfYear.of(dayOfYear);
            assertEquals(expectedDate, test.atYear(STANDARD_YEAR));
            expectedDate = expectedDate.plusDays(1);
        }
    }
}
