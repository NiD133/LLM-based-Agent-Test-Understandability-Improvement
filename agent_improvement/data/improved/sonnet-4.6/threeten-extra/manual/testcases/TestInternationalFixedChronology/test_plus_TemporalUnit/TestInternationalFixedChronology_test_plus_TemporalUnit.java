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
public class TestInternationalFixedChronology_test_plus_TemporalUnit {

    // Each row: startYear, startMonth, startDay, amount, unit, expectedYear, expectedMonth, expectedDay
    public static Object[][] data_plus() {
        return new Object[][] {
            // --- DAYS ---
            { 2014, 5, 26,  0, DAYS, 2014,  5, 26 },   // zero delta stays the same
            { 2014, 5, 26,  8, DAYS, 2014,  6,  6 },   // forward into next month
            { 2014, 5, 26, -3, DAYS, 2014,  5, 23 },   // backward within same month

            // --- WEEKS ---
            { 2014, 5, 26,  0, WEEKS, 2014,  5, 26 },  // zero delta stays the same
            { 2014, 5, 26,  3, WEEKS, 2014,  6, 19 },  // forward into next month
            { 2014, 5, 26, -5, WEEKS, 2014,  4, 19 },  // backward into previous month

            // --- MONTHS ---
            { 2014, 5, 26,  0, MONTHS, 2014,  5, 26 }, // zero delta stays the same
            { 2014, 5, 26,  3, MONTHS, 2014,  8, 26 }, // forward within same year
            { 2014, 5, 26, -5, MONTHS, 2013, 13, 26 }, // backward across year boundary

            // --- YEARS ---
            { 2014, 5, 26,  0, YEARS, 2014,  5, 26 },  // zero delta stays the same
            { 2014, 5, 26,  3, YEARS, 2017,  5, 26 },  // forward several years
            { 2014, 5, 26, -5, YEARS, 2009,  5, 26 },  // backward several years

            // --- DECADES ---
            { 2014, 5, 26,  0, DECADES, 2014,  5, 26 }, // zero delta stays the same
            { 2014, 5, 26,  3, DECADES, 2044,  5, 26 }, // forward 30 years
            { 2014, 5, 26, -5, DECADES, 1964,  5, 26 }, // backward 50 years

            // --- CENTURIES ---
            { 2014, 5, 26,  0, CENTURIES, 2014,  5, 26 }, // zero delta stays the same
            { 2014, 5, 26,  3, CENTURIES, 2314,  5, 26 }, // forward 300 years
            { 2014, 5, 26, -5, CENTURIES, 1514,  5, 26 }, // backward 500 years

            // --- MILLENNIA ---
            { 2014, 5, 26,  0, MILLENNIA, 2014,  5, 26 }, // zero delta stays the same
            { 2014, 5, 26,  3, MILLENNIA, 5014,  5, 26 }, // forward 3000 years
            { 2014, 5, 26, -1, MILLENNIA, 1014,  5, 26 }, // backward 1000 years

            // --- WEEKS crossing month-13 (Year Day) boundary ---
            { 2014, 13, 26,  3, WEEKS, 2015,  1, 19 }, // forward out of month 13 into next year
            { 2014,  1, 26, -5, WEEKS, 2013, 13, 19 }, // backward into month 13 of previous year

            // --- WEEKS crossing leap-day (month-6 day-29) boundary ---
            { 2012, 6, 26,  3, WEEKS, 2012,  7, 19 }, // forward past Leap Day
            { 2012, 7, 26, -5, WEEKS, 2012,  6, 19 }, // backward past Leap Day

            // --- WEEKS spanning multiple years through leap/year-day ---
            { 2012, 6, 21, 52 + 1, WEEKS, 2013,  6, 28 }, // one full year + 1 week
            { 2013, 6, 21, 6 * 52 + 1, WEEKS, 2019,  6, 28 }, // six full years + 1 week
        };
    }

    @ParameterizedTest
    @MethodSource("data_plus")
    public void test_plus_TemporalUnit(
            int year, int month, int dom,
            long amount, TemporalUnit unit,
            int expectedYear, int expectedMonth, int expectedDom) {
        assertEquals(
                InternationalFixedDate.of(expectedYear, expectedMonth, expectedDom),
                InternationalFixedDate.of(year, month, dom).plus(amount, unit));
    }
}
