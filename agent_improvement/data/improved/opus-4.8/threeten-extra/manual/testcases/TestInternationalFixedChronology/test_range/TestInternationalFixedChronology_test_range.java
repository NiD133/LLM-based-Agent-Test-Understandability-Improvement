package org.threeten.extra.chrono;

import static java.time.temporal.ChronoField.ALIGNED_DAY_OF_WEEK_IN_MONTH;
import static java.time.temporal.ChronoField.ALIGNED_DAY_OF_WEEK_IN_YEAR;
import static java.time.temporal.ChronoField.ALIGNED_WEEK_OF_MONTH;
import static java.time.temporal.ChronoField.ALIGNED_WEEK_OF_YEAR;
import static java.time.temporal.ChronoField.DAY_OF_MONTH;
import static java.time.temporal.ChronoField.DAY_OF_WEEK;
import static java.time.temporal.ChronoField.DAY_OF_YEAR;
import static java.time.temporal.ChronoField.MONTH_OF_YEAR;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.temporal.TemporalField;
import java.time.temporal.ValueRange;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link InternationalFixedDate#range(TemporalField)}.
 * <p>
 * In the International Fixed calendar the Leap Day (month 6, day 29 of a leap year)
 * and the Year Day (month 13, day 29) stand outside the normal week structure.
 * As a result, every "week-based" field reports the empty range {@code [0, 0]} for
 * those two days, while ordinary days report their usual ranges. These tests pin
 * down that behaviour field by field.
 */
public class TestInternationalFixedChronology_test_range {

    /**
     * Asserts that the given date reports the expected range for the given field.
     */
    private static void assertRange(InternationalFixedDate date, TemporalField field, ValueRange expected) {
        assertEquals(expected, date.range(field));
    }

    //-----------------------------------------------------------------------
    // DAY_OF_MONTH: 1..28 normally, 1..29 for months that carry an extra day
    // (month 6 in a leap year, and month 13 every year).
    //-----------------------------------------------------------------------
    @Test
    public void test_range_dayOfMonth() {
        // Leap Day and Year Day belong to a 29-day month.
        assertRange(InternationalFixedDate.of(2012, 6, 29), DAY_OF_MONTH, ValueRange.of(1, 29));
        assertRange(InternationalFixedDate.of(2012, 13, 29), DAY_OF_MONTH, ValueRange.of(1, 29));

        // Every ordinary month of a leap year reports 1..28, except months 6 and 13.
        assertRange(InternationalFixedDate.of(2012, 1, 23), DAY_OF_MONTH, ValueRange.of(1, 28));
        assertRange(InternationalFixedDate.of(2012, 2, 23), DAY_OF_MONTH, ValueRange.of(1, 28));
        assertRange(InternationalFixedDate.of(2012, 3, 23), DAY_OF_MONTH, ValueRange.of(1, 28));
        assertRange(InternationalFixedDate.of(2012, 4, 23), DAY_OF_MONTH, ValueRange.of(1, 28));
        assertRange(InternationalFixedDate.of(2012, 5, 23), DAY_OF_MONTH, ValueRange.of(1, 28));
        assertRange(InternationalFixedDate.of(2012, 6, 23), DAY_OF_MONTH, ValueRange.of(1, 29));
        assertRange(InternationalFixedDate.of(2012, 7, 23), DAY_OF_MONTH, ValueRange.of(1, 28));
        assertRange(InternationalFixedDate.of(2012, 8, 23), DAY_OF_MONTH, ValueRange.of(1, 28));
        assertRange(InternationalFixedDate.of(2012, 9, 23), DAY_OF_MONTH, ValueRange.of(1, 28));
        assertRange(InternationalFixedDate.of(2012, 10, 23), DAY_OF_MONTH, ValueRange.of(1, 28));
        assertRange(InternationalFixedDate.of(2012, 11, 23), DAY_OF_MONTH, ValueRange.of(1, 28));
        assertRange(InternationalFixedDate.of(2012, 12, 23), DAY_OF_MONTH, ValueRange.of(1, 28));
        assertRange(InternationalFixedDate.of(2012, 13, 23), DAY_OF_MONTH, ValueRange.of(1, 29));

        // In a non-leap year month 6 has no Leap Day, so it reports 1..28.
        assertRange(InternationalFixedDate.of(2011, 6, 23), DAY_OF_MONTH, ValueRange.of(1, 28));
    }

    //-----------------------------------------------------------------------
    // DAY_OF_YEAR: 1..366 in a leap year, 1..365 otherwise.
    //-----------------------------------------------------------------------
    @Test
    public void test_range_dayOfYear() {
        assertRange(InternationalFixedDate.of(2012, 1, 23), DAY_OF_YEAR, ValueRange.of(1, 366));
        assertRange(InternationalFixedDate.of(2011, 13, 23), DAY_OF_YEAR, ValueRange.of(1, 365));
    }

    //-----------------------------------------------------------------------
    // MONTH_OF_YEAR: always 1..13.
    //-----------------------------------------------------------------------
    @Test
    public void test_range_monthOfYear() {
        assertRange(InternationalFixedDate.of(2012, 1, 23), MONTH_OF_YEAR, ValueRange.of(1, 13));
        assertRange(InternationalFixedDate.of(2011, 13, 23), MONTH_OF_YEAR, ValueRange.of(1, 13));
    }

    //-----------------------------------------------------------------------
    // ALIGNED_DAY_OF_WEEK_IN_MONTH: 1..7 normally, but [0, 0] for the
    // Leap Day and Year Day, which lie outside any week.
    //-----------------------------------------------------------------------
    @Test
    public void test_range_alignedDayOfWeekInMonth() {
        assertRange(InternationalFixedDate.of(2012, 6, 29), ALIGNED_DAY_OF_WEEK_IN_MONTH, ValueRange.of(0, 0));
        assertRange(InternationalFixedDate.of(2012, 13, 29), ALIGNED_DAY_OF_WEEK_IN_MONTH, ValueRange.of(0, 0));
        assertRange(InternationalFixedDate.of(2012, 1, 23), ALIGNED_DAY_OF_WEEK_IN_MONTH, ValueRange.of(1, 7));
        assertRange(InternationalFixedDate.of(2012, 6, 23), ALIGNED_DAY_OF_WEEK_IN_MONTH, ValueRange.of(1, 7));
        assertRange(InternationalFixedDate.of(2012, 12, 23), ALIGNED_DAY_OF_WEEK_IN_MONTH, ValueRange.of(1, 7));
    }

    //-----------------------------------------------------------------------
    // ALIGNED_WEEK_OF_MONTH: 1..4 normally, [0, 0] for Leap Day and Year Day.
    //-----------------------------------------------------------------------
    @Test
    public void test_range_alignedWeekOfMonth() {
        assertRange(InternationalFixedDate.of(2012, 6, 29), ALIGNED_WEEK_OF_MONTH, ValueRange.of(0, 0));
        assertRange(InternationalFixedDate.of(2012, 13, 29), ALIGNED_WEEK_OF_MONTH, ValueRange.of(0, 0));
        assertRange(InternationalFixedDate.of(2012, 1, 23), ALIGNED_WEEK_OF_MONTH, ValueRange.of(1, 4));
        assertRange(InternationalFixedDate.of(2012, 6, 23), ALIGNED_WEEK_OF_MONTH, ValueRange.of(1, 4));
        assertRange(InternationalFixedDate.of(2012, 12, 23), ALIGNED_WEEK_OF_MONTH, ValueRange.of(1, 4));
    }

    //-----------------------------------------------------------------------
    // ALIGNED_DAY_OF_WEEK_IN_YEAR: 1..7 normally, [0, 0] for Leap Day and Year Day.
    //-----------------------------------------------------------------------
    @Test
    public void test_range_alignedDayOfWeekInYear() {
        assertRange(InternationalFixedDate.of(2012, 6, 29), ALIGNED_DAY_OF_WEEK_IN_YEAR, ValueRange.of(0, 0));
        assertRange(InternationalFixedDate.of(2012, 13, 29), ALIGNED_DAY_OF_WEEK_IN_YEAR, ValueRange.of(0, 0));
        assertRange(InternationalFixedDate.of(2012, 1, 23), ALIGNED_DAY_OF_WEEK_IN_YEAR, ValueRange.of(1, 7));
        assertRange(InternationalFixedDate.of(2012, 6, 23), ALIGNED_DAY_OF_WEEK_IN_YEAR, ValueRange.of(1, 7));
        assertRange(InternationalFixedDate.of(2012, 12, 23), ALIGNED_DAY_OF_WEEK_IN_YEAR, ValueRange.of(1, 7));
    }

    //-----------------------------------------------------------------------
    // ALIGNED_WEEK_OF_YEAR: 1..52 normally, [0, 0] for Leap Day and Year Day.
    //-----------------------------------------------------------------------
    @Test
    public void test_range_alignedWeekOfYear() {
        assertRange(InternationalFixedDate.of(2012, 6, 29), ALIGNED_WEEK_OF_YEAR, ValueRange.of(0, 0));
        assertRange(InternationalFixedDate.of(2012, 13, 29), ALIGNED_WEEK_OF_YEAR, ValueRange.of(0, 0));
        assertRange(InternationalFixedDate.of(2012, 1, 23), ALIGNED_WEEK_OF_YEAR, ValueRange.of(1, 52));
        assertRange(InternationalFixedDate.of(2012, 6, 23), ALIGNED_WEEK_OF_YEAR, ValueRange.of(1, 52));
        assertRange(InternationalFixedDate.of(2012, 12, 23), ALIGNED_WEEK_OF_YEAR, ValueRange.of(1, 52));
    }

    //-----------------------------------------------------------------------
    // DAY_OF_WEEK: 1..7 normally, [0, 0] for Leap Day and Year Day.
    //-----------------------------------------------------------------------
    @Test
    public void test_range_dayOfWeek() {
        assertRange(InternationalFixedDate.of(2012, 6, 29), DAY_OF_WEEK, ValueRange.of(0, 0));
        assertRange(InternationalFixedDate.of(2012, 13, 29), DAY_OF_WEEK, ValueRange.of(0, 0));
        assertRange(InternationalFixedDate.of(2012, 1, 23), DAY_OF_WEEK, ValueRange.of(1, 7));
        assertRange(InternationalFixedDate.of(2012, 6, 23), DAY_OF_WEEK, ValueRange.of(1, 7));
        assertRange(InternationalFixedDate.of(2012, 12, 23), DAY_OF_WEEK, ValueRange.of(1, 7));
    }
}
