package org.threeten.extra.chrono;

import static java.time.temporal.ChronoUnit.DAYS;
import static java.time.temporal.ChronoUnit.MONTHS;
import static java.time.temporal.ChronoUnit.WEEKS;
import static java.time.temporal.ChronoUnit.YEARS;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.temporal.TemporalUnit;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

@SuppressWarnings({ "static-method" })
public class TestSymmetry454Chronology_test_minus_leapWeek_TemporalUnit {

    /**
     * Test data for subtracting a temporal amount from a Symmetry454Date in a leap-week year.
     *
     * Each row: startYear, startMonth, startDay, amountToSubtract, unit, expectedYear, expectedMonth, expectedDay
     *
     * The "leap week" in question is the extra 7-day week appended to December in years where
     * {@link Symmetry454Chronology#isLeapYear} returns true (e.g. 2015).  Rows that cross the
     * leap-week boundary (days 29–35 of month 12 in year 2015) verify that subtraction handles
     * the extra week correctly.
     */
    public static Object[][] data_minus_leapWeek() {
        return new Object[][] {
            // start date          amount  unit     expected date
            { 2015, 12, 28,        0,     DAYS,    2015, 12, 28 },
            { 2016,  1,  1,        8,     DAYS,    2015, 12, 28 },  // crosses New Year boundary
            { 2015, 12, 25,       -3,     DAYS,    2015, 12, 28 },  // negative amount moves forward
            { 2015, 12, 28,        0,     WEEKS,   2015, 12, 28 },
            { 2016,  1, 14,        3,     WEEKS,   2015, 12, 28 },
            { 2015, 11, 28,       -5,     WEEKS,   2015, 12, 28 },  // negative amount moves forward
            { 2016, 12, 21,       52,     WEEKS,   2015, 12, 28 },  // one full year of weeks
            { 2015, 12, 28,        0,     MONTHS,  2015, 12, 28 },
            { 2016,  3, 28,        3,     MONTHS,  2015, 12, 28 },
            { 2015,  7, 28,       -5,     MONTHS,  2015, 12, 28 },  // negative amount moves forward
            { 2016, 12, 28,       12,     MONTHS,  2015, 12, 28 },  // exactly one year of months
            { 2015, 12, 28,        0,     YEARS,   2015, 12, 28 },
            { 2018, 12, 28,        3,     YEARS,   2015, 12, 28 },
            { 2010, 12, 28,       -5,     YEARS,   2015, 12, 28 },  // negative amount moves forward
        };
    }

    /**
     * Verifies that subtracting a temporal amount from a Symmetry454Date yields the expected date,
     * including cases where the subtraction crosses over a leap week at the end of the year.
     *
     * <p>The data source uses the same values as the corresponding plus-test so that
     * {@code start.minus(n, unit)} and {@code expected.plus(n, unit)} are inverse operations.
     *
     * @param startYear   proleptic year of the date being subtracted from
     * @param startMonth  month of the date being subtracted from (1–12)
     * @param startDay    day-of-month of the date being subtracted from
     * @param amount      number of units to subtract (negative value moves forward in time)
     * @param unit        the temporal unit (DAYS, WEEKS, MONTHS, or YEARS)
     * @param expectedYear   proleptic year of the expected result date
     * @param expectedMonth  month of the expected result date
     * @param expectedDay    day-of-month of the expected result date
     */
    @ParameterizedTest
    @MethodSource("data_minus_leapWeek")
    public void test_minus_leapWeek_TemporalUnit(
            int startYear, int startMonth, int startDay,
            long amount, TemporalUnit unit,
            int expectedYear, int expectedMonth, int expectedDay) {

        Symmetry454Date startDate = Symmetry454Date.of(startYear, startMonth, startDay);
        Symmetry454Date expectedDate = Symmetry454Date.of(expectedYear, expectedMonth, expectedDay);

        assertEquals(expectedDate, startDate.minus(amount, unit));
    }
}
