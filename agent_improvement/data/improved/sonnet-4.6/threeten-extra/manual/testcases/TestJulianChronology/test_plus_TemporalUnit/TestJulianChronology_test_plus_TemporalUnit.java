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

public class TestJulianChronology_test_plus_TemporalUnit {

    public static Object[][] data_plus() {
        return new Object[][] {
            // Adding days
            { 2014, 5, 26,  0, DAYS,     2014,  5, 26 },
            { 2014, 5, 26,  8, DAYS,     2014,  6,  3 },
            { 2014, 5, 26, -3, DAYS,     2014,  5, 23 },

            // Adding weeks
            { 2014, 5, 26,  0, WEEKS,    2014,  5, 26 },
            { 2014, 5, 26,  3, WEEKS,    2014,  6, 16 },
            { 2014, 5, 26, -5, WEEKS,    2014,  4, 21 },

            // Adding months
            { 2014, 5, 26,  0, MONTHS,   2014,  5, 26 },
            { 2014, 5, 26,  3, MONTHS,   2014,  8, 26 },
            { 2014, 5, 26, -5, MONTHS,   2013, 12, 26 },

            // Adding years
            { 2014, 5, 26,  0, YEARS,    2014,  5, 26 },
            { 2014, 5, 26,  3, YEARS,    2017,  5, 26 },
            { 2014, 5, 26, -5, YEARS,    2009,  5, 26 },

            // Adding decades
            { 2014, 5, 26,  0, DECADES,  2014,  5, 26 },
            { 2014, 5, 26,  3, DECADES,  2044,  5, 26 },
            { 2014, 5, 26, -5, DECADES,  1964,  5, 26 },

            // Adding centuries
            { 2014, 5, 26,  0, CENTURIES, 2014,  5, 26 },
            { 2014, 5, 26,  3, CENTURIES, 2314,  5, 26 },
            { 2014, 5, 26, -5, CENTURIES, 1514,  5, 26 },

            // Adding millennia
            { 2014, 5, 26,  0, MILLENNIA, 2014,       5, 26 },
            { 2014, 5, 26,  3, MILLENNIA, 5014,       5, 26 },
            { 2014, 5, 26, -5, MILLENNIA, 2014 - 5000, 5, 26 },

            // Adding eras (crossing BC/AD boundary)
            { 2014, 5, 26, -1, ERAS,     -2013,  5, 26 },
        };
    }

    @ParameterizedTest
    @MethodSource("data_plus")
    public void test_plus_TemporalUnit(
            int year, int month, int dom,
            long amount, TemporalUnit unit,
            int expectedYear, int expectedMonth, int expectedDom) {
        assertEquals(
            JulianDate.of(expectedYear, expectedMonth, expectedDom),
            JulianDate.of(year, month, dom).plus(amount, unit));
    }
}
