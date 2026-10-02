package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.Year;
import java.time.ZoneId;

import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.RetryingTest;

/**
 * Tests {@link DayOfYear#atYear(Year)} for a non-leap (standard) year, together with
 * the {@code now()} factory methods that the original suite exercised alongside it.
 */
public class TestDayOfYear_test_atYear_Year_notLeapYear {

    /** 2007 is a standard (non-leap) year. */
    private static final Year STANDARD_YEAR = Year.of(2007);

    /** Number of days in a standard (non-leap) year. */
    private static final int STANDARD_YEAR_LENGTH = 365;

    //-----------------------------------------------------------------------
    // now()
    //-----------------------------------------------------------------------
    @RetryingTest(100)
    public void now_matchesLocalDateDayOfYear() {
        assertEquals(LocalDate.now().getDayOfYear(), DayOfYear.now().getValue());
    }

    @RetryingTest(100)
    public void now_withZone_matchesLocalDateDayOfYear() {
        ZoneId tokyo = ZoneId.of("Asia/Tokyo");
        assertEquals(LocalDate.now(tokyo).getDayOfYear(), DayOfYear.now(tokyo).getValue());
    }

    //-----------------------------------------------------------------------
    // atYear(Year) in a standard (non-leap) year
    //-----------------------------------------------------------------------
    @Test
    public void atYear_inStandardYear_mapsEachDayToCorrectDate() {
        // Walk every day of 2007 (1..365) and verify atYear produces the matching calendar date.
        LocalDate expectedDate = LocalDate.of(2007, 1, 1);
        for (int dayOfYear = 1; dayOfYear <= STANDARD_YEAR_LENGTH; dayOfYear++) {
            DayOfYear day = DayOfYear.of(dayOfYear);
            assertEquals(expectedDate, day.atYear(STANDARD_YEAR));
            expectedDate = expectedDate.plusDays(1);
        }
    }
}
