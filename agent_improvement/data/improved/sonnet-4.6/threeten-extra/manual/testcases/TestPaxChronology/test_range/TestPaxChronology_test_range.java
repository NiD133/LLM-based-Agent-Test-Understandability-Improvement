package org.threeten.extra.chrono;

import static java.time.temporal.ChronoField.ALIGNED_WEEK_OF_MONTH;
import static java.time.temporal.ChronoField.DAY_OF_MONTH;
import static java.time.temporal.ChronoField.DAY_OF_YEAR;
import static java.time.temporal.ChronoField.MONTH_OF_YEAR;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.temporal.TemporalField;
import java.time.temporal.ValueRange;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Tests that {@link PaxDate#range(TemporalField)} returns the correct {@link ValueRange}
 * for each temporal field, taking into account whether the date falls in a leap year,
 * and whether the month is the short Pax intercalary month.
 *
 * <p>In the Pax calendar:
 * <ul>
 *   <li>A leap year (e.g. 2012, whose last two digits 12 are divisible by 6) has 14 months
 *       and 371 days. Month 13 is the one-week "Pax" intercalary month (7 days); month 14
 *       is the regular December-equivalent (28 days).</li>
 *   <li>A non-leap year (e.g. 2011) has 13 months and 364 days, each of 28 days.</li>
 * </ul>
 */
@SuppressWarnings({"static-method"})
public class TestPaxChronology_test_range {

    /**
     * Test data for {@link #test_range}.
     *
     * <p>Columns: year, month, dayOfMonth, temporalField, expectedRangeMin, expectedRangeMax
     */
    public static Object[][] data_ranges() {
        return new Object[][] {
            // --- DAY_OF_MONTH in a leap year (2012) ---
            // Regular months always have 28 days (months 1–12 and month 14)
            { 2012,  1, 23, DAY_OF_MONTH,          1, 28 },
            { 2012,  2, 23, DAY_OF_MONTH,          1, 28 },
            { 2012,  3, 23, DAY_OF_MONTH,          1, 28 },
            { 2012,  4, 23, DAY_OF_MONTH,          1, 28 },
            { 2012,  5, 23, DAY_OF_MONTH,          1, 28 },
            { 2012,  6, 23, DAY_OF_MONTH,          1, 28 },
            { 2012,  7, 23, DAY_OF_MONTH,          1, 28 },
            { 2012,  8, 23, DAY_OF_MONTH,          1, 28 },
            { 2012,  9, 23, DAY_OF_MONTH,          1, 28 },
            { 2012, 10, 23, DAY_OF_MONTH,          1, 28 },
            { 2012, 11, 23, DAY_OF_MONTH,          1, 28 },
            { 2012, 12, 23, DAY_OF_MONTH,          1, 28 },
            // Month 13 in a leap year is the short Pax intercalary month: only 7 days
            { 2012, 13,  3, DAY_OF_MONTH,          1,  7 },
            // Month 14 in a leap year (the shifted December) has the standard 28 days
            { 2012, 14, 23, DAY_OF_MONTH,          1, 28 },

            // --- DAY_OF_MONTH in a non-leap year (2011) ---
            // Month 13 in a non-leap year is a full 28-day month (no short intercalary month exists)
            { 2011, 13, 23, DAY_OF_MONTH,          1, 28 },

            // --- MONTH_OF_YEAR range ---
            // Leap year: 14 months total (month 13 = Pax intercalary, month 14 = December)
            { 2012,  1, 23, MONTH_OF_YEAR,         1, 14 },
            // Non-leap year: 13 months total
            { 2011,  1, 23, MONTH_OF_YEAR,         1, 13 },

            // --- DAY_OF_YEAR range ---
            // Leap year: 364 regular days + 7 Pax intercalary days = 371
            { 2012,  1, 23, DAY_OF_YEAR,           1, 371 },
            // Non-leap year: 13 months × 28 days = 364
            { 2011, 13, 23, DAY_OF_YEAR,           1, 364 },

            // --- ALIGNED_WEEK_OF_MONTH range ---
            // Regular months contain exactly 4 aligned weeks
            { 2012,  1, 23, ALIGNED_WEEK_OF_MONTH, 1,  4 },
            // The Pax intercalary month (month 13 in a leap year) is only 1 week long
            { 2012, 13,  3, ALIGNED_WEEK_OF_MONTH, 1,  1 },
            // Month 14 (post-intercalary December in a leap year) has 4 aligned weeks
            { 2012, 14, 23, ALIGNED_WEEK_OF_MONTH, 1,  4 },
            // Month 13 in a non-leap year is a normal 4-week month
            { 2011, 13, 23, ALIGNED_WEEK_OF_MONTH, 1,  4 },
        };
    }

    @ParameterizedTest
    @MethodSource("data_ranges")
    public void test_range(int year, int month, int dom, TemporalField field, int expectedMin, int expectedMax) {
        assertEquals(ValueRange.of(expectedMin, expectedMax), PaxDate.of(year, month, dom).range(field));
    }
}
