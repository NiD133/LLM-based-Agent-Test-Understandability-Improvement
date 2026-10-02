package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.ZoneId;

import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.RetryingTest;

/**
 * Tests for {@link DayOfYear}, focusing on {@link DayOfYear#isValidYear(int)}.
 */
public class TestDayOfYear_test_isValidYear_365 {

    //-----------------------------------------------------------------------
    // now()
    //-----------------------------------------------------------------------
    @RetryingTest(100)
    public void now_matchesCurrentDayOfYearInDefaultZone() {
        int expectedDayOfYear = LocalDate.now().getDayOfYear();

        assertEquals(expectedDayOfYear, DayOfYear.now().getValue());
    }

    @RetryingTest(100)
    public void now_withZone_matchesCurrentDayOfYearInThatZone() {
        ZoneId tokyo = ZoneId.of("Asia/Tokyo");
        int expectedDayOfYear = LocalDate.now(tokyo).getDayOfYear();

        assertEquals(expectedDayOfYear, DayOfYear.now(tokyo).getValue());
    }

    //-----------------------------------------------------------------------
    // isValidYear(int)
    //-----------------------------------------------------------------------
    @Test
    public void isValidYear_day365_isValidForEveryYear() {
        // Day 365 exists in every year (leap or not), so isValidYear is always true.
        DayOfYear day365 = DayOfYear.of(365);

        assertEquals(true, day365.isValidYear(2011)); // standard year
        assertEquals(true, day365.isValidYear(2012)); // leap year
        assertEquals(true, day365.isValidYear(2013)); // standard year
    }
}
