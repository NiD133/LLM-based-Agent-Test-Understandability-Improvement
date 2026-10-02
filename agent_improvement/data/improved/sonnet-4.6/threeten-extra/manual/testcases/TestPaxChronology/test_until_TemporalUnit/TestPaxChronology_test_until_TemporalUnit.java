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

@SuppressWarnings({ "static-method" })
public class TestPaxChronology_test_until_TemporalUnit {

    // Columns: year1, month1, dom1, year2, month2, dom2, unit, expected
    public static Object[][] data_until() {
        return new Object[][] {
            // Same date always yields 0
            { 2014, 5, 26,  2014,  5, 26,  DAYS,     0 },
            { 2014, 5, 26,  2014,  5, 26,  WEEKS,    0 },
            { 2014, 5, 26,  2014,  5, 26,  MONTHS,   0 },
            { 2014, 5, 26,  2014,  5, 26,  YEARS,    0 },
            { 2014, 5, 26,  2014,  5, 26,  DECADES,  0 },
            { 2014, 5, 26,  2014,  5, 26,  CENTURIES, 0 },
            { 2014, 5, 26,  2014,  5, 26,  MILLENNIA, 0 },

            // Days: positive and negative
            { 2014, 5, 26,  2014,  6,  4,  DAYS,     6 },
            { 2014, 5, 26,  2014,  5, 20,  DAYS,    -6 },

            // Weeks: boundary just before/at exactly one week
            { 2014, 5, 26,  2014,  6,  4,  WEEKS,    0 },
            { 2014, 5, 26,  2014,  6,  5,  WEEKS,    1 },

            // Months: boundary just before/at exactly one month
            { 2014, 5, 26,  2014,  6, 25,  MONTHS,   0 },
            { 2014, 5, 26,  2014,  6, 26,  MONTHS,   1 },

            // Years: boundary just before/at exactly one year
            { 2014, 5, 26,  2015,  5, 25,  YEARS,    0 },
            { 2014, 5, 26,  2015,  5, 26,  YEARS,    1 },

            // Decades: boundary just before/at exactly one decade
            { 2014, 5, 26,  2024,  5, 25,  DECADES,  0 },
            { 2014, 5, 26,  2024,  5, 26,  DECADES,  1 },

            // Centuries: boundary just before/at exactly one century
            { 2014, 5, 26,  2114,  5, 25,  CENTURIES, 0 },
            { 2014, 5, 26,  2114,  5, 26,  CENTURIES, 1 },

            // Millennia: boundary just before/at exactly one millennium
            { 2014, 5, 26,  3014,  5, 25,  MILLENNIA, 0 },
            { 2014, 5, 26,  3014,  5, 26,  MILLENNIA, 1 },

            // Eras: same era yields 0, different eras yields 1
            { -2013, 5, 26,     0,  5, 26,  ERAS,     0 },
            { -2013, 5, 26,  2014,  5, 26,  ERAS,     1 },

            // Leap-year edge cases: month 13 (Pax) vs month 14 boundaries
            { 2011, 13, 26,  2013, 13, 26,  YEARS,    2 },
            { 2011, 13, 26,  2012, 14, 26,  YEARS,    1 },
            { 2012, 14, 26,  2011, 13, 26,  YEARS,   -1 },
            { 2012, 14, 26,  2013, 13, 26,  YEARS,    1 },

            // Leap-month day comparisons that do NOT cross a full year boundary
            { 2011, 13,  6,  2012, 13,  6,  YEARS,    0 },
            { 2012, 13,  6,  2011, 13,  6,  YEARS,    0 },
            { 2011, 13,  1,  2012, 13,  7,  YEARS,    0 },
            { 2012, 13,  7,  2011, 13,  1,  YEARS,    0 },

            // Full year crossings involving leap-month boundaries
            { 2011, 12, 28,  2012, 13,  1,  YEARS,    1 },
            { 2012, 13,  1,  2011, 12, 28,  YEARS,   -1 },
            { 2013, 13,  6,  2012, 13,  6,  YEARS,   -1 },
            { 2012, 13,  6,  2013, 13,  6,  YEARS,    1 },
        };
    }

    @ParameterizedTest
    @MethodSource("data_until")
    public void test_until_TemporalUnit(
            int year1, int month1, int dom1,
            int year2, int month2, int dom2,
            TemporalUnit unit, long expected) {
        PaxDate start = PaxDate.of(year1, month1, dom1);
        PaxDate end   = PaxDate.of(year2, month2, dom2);
        assertEquals(expected, start.until(end, unit));
    }
}
