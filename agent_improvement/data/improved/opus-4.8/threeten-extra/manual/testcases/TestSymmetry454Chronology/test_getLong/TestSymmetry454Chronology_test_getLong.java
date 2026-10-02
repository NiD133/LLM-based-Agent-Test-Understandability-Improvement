package org.threeten.extra.chrono;

import static java.time.temporal.ChronoField.ALIGNED_DAY_OF_WEEK_IN_MONTH;
import static java.time.temporal.ChronoField.ALIGNED_DAY_OF_WEEK_IN_YEAR;
import static java.time.temporal.ChronoField.ALIGNED_WEEK_OF_MONTH;
import static java.time.temporal.ChronoField.ALIGNED_WEEK_OF_YEAR;
import static java.time.temporal.ChronoField.DAY_OF_MONTH;
import static java.time.temporal.ChronoField.DAY_OF_WEEK;
import static java.time.temporal.ChronoField.DAY_OF_YEAR;
import static java.time.temporal.ChronoField.ERA;
import static java.time.temporal.ChronoField.MONTH_OF_YEAR;
import static java.time.temporal.ChronoField.PROLEPTIC_MONTH;
import static java.time.temporal.ChronoField.YEAR;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.temporal.TemporalField;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link Symmetry454Date#getLong(TemporalField)}.
 *
 * <p>Background on the Symmetry454 calendar, which the expected values rely on:
 * <ul>
 *   <li>A regular year has 12 months grouped into four quarters.</li>
 *   <li>Within every quarter the months follow a 4-5-4 week pattern, i.e.
 *       28, 35 and 28 days. A regular year therefore has 364 days (52 weeks).</li>
 *   <li>A leap year appends an extra week to December, making it 35 days long
 *       and giving the year 371 days (53 weeks).</li>
 * </ul>
 */
@SuppressWarnings({ "static-method" })
public class TestSymmetry454Chronology_test_getLong {

    /**
     * Verifies that querying a field on a {@link Symmetry454Date} returns the
     * expected value. Each date is given as a (year, month, dayOfMonth) triple.
     */
    @Test
    public void test_getLong() {
        // --- Mid-year date in a regular year: 2014-05-26 ---
        // May is the second month of quarter 2, so it is a 35-day (5-week) month.
        assertGetLong(2014, 5, 26, DAY_OF_WEEK, 5);
        assertGetLong(2014, 5, 26, DAY_OF_MONTH, 26);
        // Day-of-year = days in months 1..4 (28+35+28+28) plus 26 days into May.
        assertGetLong(2014, 5, 26, DAY_OF_YEAR, 28 + 35 + 28 + 28 + 26);
        assertGetLong(2014, 5, 26, ALIGNED_DAY_OF_WEEK_IN_MONTH, 5);
        assertGetLong(2014, 5, 26, ALIGNED_WEEK_OF_MONTH, 4);
        assertGetLong(2014, 5, 26, ALIGNED_DAY_OF_WEEK_IN_YEAR, 5);
        // Aligned-week-of-year = weeks in months 1..4 (4+5+4+4) plus 4 weeks into May.
        assertGetLong(2014, 5, 26, ALIGNED_WEEK_OF_YEAR, 4 + 5 + 4 + 4 + 4);
        assertGetLong(2014, 5, 26, MONTH_OF_YEAR, 5);
        // Proleptic-month counts months since the proleptic year 0 (zero-based).
        assertGetLong(2014, 5, 26, PROLEPTIC_MONTH, 2014 * 12 + 5 - 1);
        assertGetLong(2014, 5, 26, YEAR, 2014);
        assertGetLong(2014, 5, 26, ERA, 1);

        // Era is CE (1) regardless of the year.
        assertGetLong(1, 5, 8, ERA, 1);

        // --- Date in the third quarter: 2012-09-26 ---
        // September is the last month of quarter 3, so two full quarters precede it.
        assertGetLong(2012, 9, 26, DAY_OF_WEEK, 5);
        // Day-of-year = 3 quarters of (4+5+4) weeks * 7 days, minus the last 2 days.
        assertGetLong(2012, 9, 26, DAY_OF_YEAR, 3 * (4 + 5 + 4) * 7 - 2);
        assertGetLong(2012, 9, 26, ALIGNED_DAY_OF_WEEK_IN_MONTH, 5);
        assertGetLong(2012, 9, 26, ALIGNED_WEEK_OF_MONTH, 4);
        assertGetLong(2012, 9, 26, ALIGNED_DAY_OF_WEEK_IN_YEAR, 5);
        // Aligned-week-of-year = 3 quarters of (4+5+4) weeks.
        assertGetLong(2012, 9, 26, ALIGNED_WEEK_OF_YEAR, 3 * (4 + 5 + 4));

        // --- Last day of a leap year: 2015-12-35 (leap December has 35 days) ---
        assertGetLong(2015, 12, 35, DAY_OF_WEEK, 7);
        assertGetLong(2015, 12, 35, DAY_OF_MONTH, 35);
        // Day-of-year = 4 quarters of (4+5+4) weeks * 7 days, plus the extra leap week.
        assertGetLong(2015, 12, 35, DAY_OF_YEAR, 4 * (4 + 5 + 4) * 7 + 7);
        assertGetLong(2015, 12, 35, ALIGNED_DAY_OF_WEEK_IN_MONTH, 7);
        assertGetLong(2015, 12, 35, ALIGNED_WEEK_OF_MONTH, 5);
        assertGetLong(2015, 12, 35, ALIGNED_DAY_OF_WEEK_IN_YEAR, 7);
        // 52 regular weeks plus the leap week makes week 53.
        assertGetLong(2015, 12, 35, ALIGNED_WEEK_OF_YEAR, 53);
        assertGetLong(2015, 12, 35, MONTH_OF_YEAR, 12);
        // Proleptic-month for the last month of 2015 = (months through 2016) - 1.
        assertGetLong(2015, 12, 35, PROLEPTIC_MONTH, 2016 * 12 - 1);
    }

    private static void assertGetLong(int year, int month, int dom, TemporalField field, long expected) {
        assertEquals(expected, Symmetry454Date.of(year, month, dom).getLong(field));
    }
}
