package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.ZoneId;

import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.RetryingTest;

/**
 * Tests for {@link DayOfYear#isValidYear(int)}: day 365 is always valid
 * because only day 366 requires a leap year.
 */
public class TestDayOfYear_test_isValidYear_365 {

    @RetryingTest(100)
    public void test_now() {
        assertEquals(LocalDate.now().getDayOfYear(), DayOfYear.now().getValue());
    }

    @RetryingTest(100)
    public void test_now_ZoneId() {
        ZoneId zone = ZoneId.of("Asia/Tokyo");
        assertEquals(LocalDate.now(zone).getDayOfYear(), DayOfYear.now(zone).getValue());
    }

    /**
     * Day 365 is valid for any year: standard years (2011, 2013) and leap years (2012),
     * because {@code isValidYear} only returns false when the day is 366 in a non-leap year.
     */
    @Test
    public void test_isValidYear_365() {
        DayOfYear test = DayOfYear.of(365);
        assertTrue(test.isValidYear(2011)); // standard year
        assertTrue(test.isValidYear(2012)); // leap year
        assertTrue(test.isValidYear(2013)); // standard year
    }
}
