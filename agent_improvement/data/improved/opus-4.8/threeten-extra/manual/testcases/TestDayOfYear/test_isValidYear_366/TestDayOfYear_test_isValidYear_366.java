package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.ZoneId;

import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.RetryingTest;

/**
 * Tests for {@link DayOfYear}, focused on {@link DayOfYear#isValidYear(int)}.
 */
public class TestDayOfYear_test_isValidYear_366 {

    /**
     * The current day-of-year must match the day-of-year of the current local date.
     * Retried because the value can roll over between the two reads at midnight.
     */
    @RetryingTest(100)
    public void test_now() {
        assertEquals(LocalDate.now().getDayOfYear(), DayOfYear.now().getValue());
    }

    /**
     * Same as {@link #test_now()} but pinned to an explicit time-zone.
     */
    @RetryingTest(100)
    public void test_now_ZoneId() {
        ZoneId zone = ZoneId.of("Asia/Tokyo");
        assertEquals(LocalDate.now(zone).getDayOfYear(), DayOfYear.now(zone).getValue());
    }

    /**
     * Day-of-year 366 only forms a valid date in a leap year.
     * 2012 is a leap year; 2011 and 2013 are standard years.
     */
    @Test
    public void test_isValidYear_366() {
        DayOfYear day366 = DayOfYear.of(366);

        assertEquals(false, day366.isValidYear(2011)); // standard year
        assertEquals(true, day366.isValidYear(2012));  // leap year
        assertEquals(false, day366.isValidYear(2013)); // standard year
    }
}
