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
public class TestSymmetry010Chronology_test_minus_leapWeek_TemporalUnit {

    /**
     * Each row: (year, month, dom) — the starting date in a leap year,
     *           amount — units to subtract (negative means add),
     *           unit — the temporal unit,
     *           (expectedYear, expectedMonth, expectedDom) — the expected result after subtraction.
     *
     * All starting dates are in or adjacent to a leap-week year (2015),
     * so these cases exercise the extra 7-day leap week appended to December.
     */
    public static Object[][] data_minus_leapWeek() {
        return new Object[][] {
            // DAYS
            { 2015, 12, 28,   0, DAYS,   2015, 12, 28 },  // subtract 0 days: no change
            { 2015, 12, 36,   8, DAYS,   2015, 12, 28 },  // back 8 days across leap-week boundary
            { 2015, 12, 25,  -3, DAYS,   2015, 12, 28 },  // subtract -3 days (= add 3) into leap week

            // WEEKS
            { 2015, 12, 28,   0, WEEKS,  2015, 12, 28 },  // subtract 0 weeks: no change
            { 2016,  1, 12,   3, WEEKS,  2015, 12, 28 },  // back 3 weeks from next year into leap week
            { 2015, 11, 24,  -5, WEEKS,  2015, 12, 28 },  // subtract -5 weeks (= add 5) from month 11 into leap week
            { 2016, 12, 21,  52, WEEKS,  2015, 12, 28 },  // back 52 weeks spans a full leap year

            // MONTHS
            { 2015, 12, 28,   0, MONTHS, 2015, 12, 28 },  // subtract 0 months: no change
            { 2016,  3, 28,   3, MONTHS, 2015, 12, 28 },  // back 3 months crosses year boundary
            { 2015,  7, 28,  -5, MONTHS, 2015, 12, 28 },  // subtract -5 months (= add 5) within same year
            { 2016, 12, 28,  12, MONTHS, 2015, 12, 28 },  // back exactly one year (12 months)

            // YEARS
            { 2015, 12, 28,   0, YEARS,  2015, 12, 28 },  // subtract 0 years: no change
            { 2018, 12, 28,   3, YEARS,  2015, 12, 28 },  // back 3 years
            { 2010, 12, 28,  -5, YEARS,  2015, 12, 28 },  // subtract -5 years (= add 5)
        };
    }

    @ParameterizedTest
    @MethodSource("data_minus_leapWeek")
    public void test_minus_leapWeek_TemporalUnit(
            int year, int month, int dom,
            long amount, TemporalUnit unit,
            int expectedYear, int expectedMonth, int expectedDom) {
        assertEquals(
                Symmetry010Date.of(expectedYear, expectedMonth, expectedDom),
                Symmetry010Date.of(year, month, dom).minus(amount, unit));
    }
}
