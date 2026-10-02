package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.ZoneId;

import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.RetryingTest;

/**
 * Tests for {@link DayOfYear}, focusing on {@code adjustInto} when applied to the
 * first day of a non-leap year.
 */
public class TestDayOfYear_test_adjustInto_fromStartOfYear_notLeapYear {

    /** Number of days in a standard (non-leap) year. */
    private static final int DAYS_IN_STANDARD_YEAR = 365;

    //-----------------------------------------------------------------------
    @RetryingTest(100)
    public void now_returnsCurrentDayOfYear_forSystemDefaultZone() {
        assertEquals(LocalDate.now().getDayOfYear(), DayOfYear.now().getValue());
    }

    //-----------------------------------------------------------------------
    @RetryingTest(100)
    public void now_returnsCurrentDayOfYear_forGivenZone() {
        ZoneId tokyo = ZoneId.of("Asia/Tokyo");
        assertEquals(LocalDate.now(tokyo).getDayOfYear(), DayOfYear.now(tokyo).getValue());
    }

    //-----------------------------------------------------------------------
    /**
     * Starting from January 1st of a non-leap year, adjusting that base date with
     * {@code DayOfYear.of(i)} must yield the i-th day of the year for every day
     * from 1 to 365.
     */
    @Test
    public void adjustInto_setsEachDayOfStandardYear() {
        LocalDate startOfYear = LocalDate.of(2007, 1, 1);

        LocalDate expectedDate = startOfYear;
        for (int dayOfYear = 1; dayOfYear <= DAYS_IN_STANDARD_YEAR; dayOfYear++) {
            DayOfYear dayUnderTest = DayOfYear.of(dayOfYear);

            assertEquals(expectedDate, dayUnderTest.adjustInto(startOfYear));

            expectedDate = expectedDate.plusDays(1);
        }
    }
}
