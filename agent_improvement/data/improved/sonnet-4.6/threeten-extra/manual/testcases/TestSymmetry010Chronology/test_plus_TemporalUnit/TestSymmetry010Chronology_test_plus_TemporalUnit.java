package org.threeten.extra.chrono;

import static java.time.temporal.ChronoUnit.CENTURIES;
import static java.time.temporal.ChronoUnit.DAYS;
import static java.time.temporal.ChronoUnit.DECADES;
import static java.time.temporal.ChronoUnit.MILLENNIA;
import static java.time.temporal.ChronoUnit.MONTHS;
import static java.time.temporal.ChronoUnit.WEEKS;
import static java.time.temporal.ChronoUnit.YEARS;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.temporal.TemporalUnit;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

@SuppressWarnings({ "static-method" })
public class TestSymmetry010Chronology_test_plus_TemporalUnit {

    // Each row: startYear, startMonth, startDay, amount, unit, expectedYear, expectedMonth, expectedDay
    public static Object[][] data_plus() {
        return new Object[][] {
            // --- DAYS ---
            { 2014, 5, 26,  0, DAYS, 2014, 5, 26 },  // zero: no change
            { 2014, 5, 26,  8, DAYS, 2014, 6,  3 },  // positive: crosses month boundary
            { 2014, 5, 26, -3, DAYS, 2014, 5, 23 },  // negative: stays within month

            // --- WEEKS ---
            { 2014, 5, 26,  0, WEEKS, 2014, 5, 26 },  // zero: no change
            { 2014, 5, 26,  3, WEEKS, 2014, 6, 16 },  // positive: crosses month boundary
            { 2014, 5, 26, -5, WEEKS, 2014, 4, 21 },  // negative: crosses month boundary

            // --- MONTHS ---
            { 2014, 5, 26,  0, MONTHS, 2014,  5, 26 },  // zero: no change
            { 2014, 5, 26,  3, MONTHS, 2014,  8, 26 },  // positive: stays within year
            { 2014, 5, 26, -5, MONTHS, 2013, 12, 26 },  // negative: crosses year boundary

            // --- YEARS ---
            { 2014, 5, 26,  0, YEARS, 2014, 5, 26 },  // zero: no change
            { 2014, 5, 26,  3, YEARS, 2017, 5, 26 },  // positive
            { 2014, 5, 26, -5, YEARS, 2009, 5, 26 },  // negative

            // --- DECADES ---
            { 2014, 5, 26,  0, DECADES, 2014, 5, 26 },  // zero: no change
            { 2014, 5, 26,  3, DECADES, 2044, 5, 26 },  // positive
            { 2014, 5, 26, -5, DECADES, 1964, 5, 26 },  // negative

            // --- CENTURIES ---
            { 2014, 5, 26,  0, CENTURIES, 2014, 5, 26 },  // zero: no change
            { 2014, 5, 26,  3, CENTURIES, 2314, 5, 26 },  // positive
            { 2014, 5, 26, -5, CENTURIES, 1514, 5, 26 },  // negative

            // --- MILLENNIA ---
            { 2014, 5, 26,  0, MILLENNIA,       2014, 5, 26 },  // zero: no change
            { 2014, 5, 26,  3, MILLENNIA,       5014, 5, 26 },  // positive
            { 2014, 5, 26, -1, MILLENNIA, 2014 - 1000, 5, 26 },  // negative

            // --- WEEKS crossing year boundary ---
            { 2014, 12, 26,  3, WEEKS, 2015,  1, 17 },  // forward into next year
            { 2014,  1, 26, -5, WEEKS, 2013, 12, 21 },  // backward into previous year

            // --- WEEKS spanning a leap-week year (2012 has a leap week in December) ---
            { 2012, 6, 26,  3, WEEKS, 2012,  7, 17 },  // forward past leap-week month boundary
            { 2012, 7, 26, -5, WEEKS, 2012,  6, 21 },  // backward past leap-week month boundary
            { 2012, 6, 21, 52 + 1, WEEKS, 2013,  6, 28 },  // 53 weeks spanning the leap year
            { 2013, 6, 21, 6 * 52 + 1, WEEKS, 2019, 6, 21 },  // 313 weeks across multiple years
        };
    }

    @ParameterizedTest
    @MethodSource("data_plus")
    public void test_plus_TemporalUnit(int year, int month, int dom, long amount, TemporalUnit unit,
            int expectedYear, int expectedMonth, int expectedDom) {
        assertEquals(
                Symmetry010Date.of(expectedYear, expectedMonth, expectedDom),
                Symmetry010Date.of(year, month, dom).plus(amount, unit));
    }
}
