package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.ZoneId;

import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.RetryingTest;

/**
 * Tests for {@link DayOfYear#isValidYear(int)} when the day-of-year is 366
 * (the leap day). Day 366 only exists in leap years, so {@code isValidYear}
 * must return {@code false} for standard years and {@code true} for leap years.
 */
public class TestDayOfYear_test_isValidYear_366 {

    /** Day 366 only exists in leap years. */
    private static final DayOfYear DAY_366 = DayOfYear.of(366);

    /** A standard (non-leap) year before the leap year under test. */
    private static final int STANDARD_YEAR_BEFORE = 2011;

    /** A leap year — day 366 is valid here. */
    private static final int LEAP_YEAR = 2012;

    /** A standard (non-leap) year after the leap year under test. */
    private static final int STANDARD_YEAR_AFTER = 2013;

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
    @Test
    public void test_isValidYear_366() {
        // Day 366 is invalid in the standard year before the leap year
        assertFalse(DAY_366.isValidYear(STANDARD_YEAR_BEFORE));
        // Day 366 is valid in the leap year itself
        assertTrue(DAY_366.isValidYear(LEAP_YEAR));
        // Day 366 is invalid again in the standard year after the leap year
        assertFalse(DAY_366.isValidYear(STANDARD_YEAR_AFTER));
    }
}
