package org.threeten.extra.chrono;

import static java.time.temporal.ChronoUnit.CENTURIES;
import static java.time.temporal.ChronoUnit.DAYS;
import static java.time.temporal.ChronoUnit.DECADES;
import static java.time.temporal.ChronoUnit.ERAS;
import static java.time.temporal.ChronoUnit.MILLENNIA;
import static java.time.temporal.ChronoUnit.MONTHS;
import static java.time.temporal.ChronoUnit.WEEKS;
import static java.time.temporal.ChronoUnit.YEARS;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.temporal.TemporalUnit;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Tests {@link BritishCutoverDate#until(java.time.temporal.Temporal, TemporalUnit)}.
 *
 * <p>The British cutover dropped 1752-09-03 through 1752-09-13 (the calendar
 * jumped straight from Wednesday 1752-09-02 to Thursday 1752-09-14). The cases
 * below therefore concentrate on date pairs that span that gap, plus a set of
 * ordinary modern dates that exercise every supported unit.
 */
public class TestBritishCutoverChronology_test_until_TemporalUnit {

    /**
     * Each row is: start (year, month, day), end (year, month, day), the unit to
     * measure in, and the expected whole-unit amount returned by {@code until}.
     */
    public static Object[][] data_until() {
        return new Object[][] {
            // ----- DAYS (the 11 dropped days are not counted across the cutover) -----
            { 1752, 9, 1, 1752, 9, 2, DAYS, 1 },
            { 1752, 9, 1, 1752, 9, 14, DAYS, 2 },
            { 1752, 9, 2, 1752, 9, 14, DAYS, 1 },
            { 1752, 9, 2, 1752, 9, 15, DAYS, 2 },
            { 1752, 9, 14, 1752, 9, 1, DAYS, -2 },
            { 1752, 9, 14, 1752, 9, 2, DAYS, -1 },
            { 2014, 5, 26, 2014, 5, 26, DAYS, 0 },
            { 2014, 5, 26, 2014, 6, 1, DAYS, 6 },
            { 2014, 5, 26, 2014, 5, 20, DAYS, -6 },

            // ----- WEEKS -----
            { 1752, 9, 1, 1752, 9, 14, WEEKS, 0 },
            { 1752, 9, 1, 1752, 9, 18, WEEKS, 0 },
            { 1752, 9, 1, 1752, 9, 19, WEEKS, 1 },
            { 1752, 9, 2, 1752, 9, 14, WEEKS, 0 },
            { 1752, 9, 2, 1752, 9, 19, WEEKS, 0 },
            { 1752, 9, 2, 1752, 9, 20, WEEKS, 1 },
            { 2014, 5, 26, 2014, 5, 26, WEEKS, 0 },
            { 2014, 5, 26, 2014, 6, 1, WEEKS, 0 },
            { 2014, 5, 26, 2014, 6, 2, WEEKS, 1 },

            // ----- MONTHS -----
            { 1752, 9, 1, 1752, 9, 14, MONTHS, 0 },
            { 1752, 9, 1, 1752, 9, 30, MONTHS, 0 },
            { 1752, 9, 1, 1752, 10, 1, MONTHS, 1 },
            { 1752, 9, 2, 1752, 9, 14, MONTHS, 0 },
            { 1752, 9, 2, 1752, 10, 1, MONTHS, 0 },
            { 1752, 9, 2, 1752, 10, 2, MONTHS, 1 },
            { 1752, 9, 14, 1752, 9, 15, MONTHS, 0 },
            { 1752, 9, 14, 1752, 10, 13, MONTHS, 0 },
            { 1752, 9, 14, 1752, 10, 14, MONTHS, 1 },
            { 2014, 5, 26, 2014, 5, 26, MONTHS, 0 },
            { 2014, 5, 26, 2014, 6, 25, MONTHS, 0 },
            { 2014, 5, 26, 2014, 6, 26, MONTHS, 1 },

            // ----- YEARS -----
            { 2014, 5, 26, 2014, 5, 26, YEARS, 0 },
            { 2014, 5, 26, 2015, 5, 25, YEARS, 0 },
            { 2014, 5, 26, 2015, 5, 26, YEARS, 1 },

            // ----- DECADES -----
            { 2014, 5, 26, 2014, 5, 26, DECADES, 0 },
            { 2014, 5, 26, 2024, 5, 25, DECADES, 0 },
            { 2014, 5, 26, 2024, 5, 26, DECADES, 1 },

            // ----- CENTURIES -----
            { 2014, 5, 26, 2014, 5, 26, CENTURIES, 0 },
            { 2014, 5, 26, 2114, 5, 25, CENTURIES, 0 },
            { 2014, 5, 26, 2114, 5, 26, CENTURIES, 1 },

            // ----- MILLENNIA -----
            { 2014, 5, 26, 2014, 5, 26, MILLENNIA, 0 },
            { 2014, 5, 26, 3014, 5, 25, MILLENNIA, 0 },
            { 2014, 5, 26, 3014, 5, 26, MILLENNIA, 1 },

            // ----- ERAS (year -2013 is era BC/0, year 2014 is era AD/1) -----
            { -2013, 5, 26, 0, 5, 26, ERAS, 0 },
            { -2013, 5, 26, 2014, 5, 26, ERAS, 1 },
        };
    }

    @ParameterizedTest
    @MethodSource("data_until")
    public void test_until_TemporalUnit(int year1, int month1, int dom1,
            int year2, int month2, int dom2, TemporalUnit unit, long expected) {
        BritishCutoverDate start = BritishCutoverDate.of(year1, month1, dom1);
        BritishCutoverDate end = BritishCutoverDate.of(year2, month2, dom2);

        assertEquals(expected, start.until(end, unit));
    }
}
