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

/**
 * Tests {@link Symmetry010Date#plus(long, TemporalUnit)}.
 */
@SuppressWarnings({ "static-method" })
public class TestSymmetry010Chronology_test_plus_TemporalUnit {

    /**
     * Cases for {@link #test_plus_TemporalUnit}.
     * <p>
     * Each row is: {@code startYear, startMonth, startDom, amountToAdd, unit,
     * expectedYear, expectedMonth, expectedDom}. Adding {@code amountToAdd} of
     * {@code unit} to the start date must yield the expected date.
     */
    public static Object[][] data_plus() {
        return new Object[][] {
            // adding days, including roll-over into the next month and back into the previous range
            { 2014, 5, 26,  0, DAYS,  2014,  5, 26 },
            { 2014, 5, 26,  8, DAYS,  2014,  6,  3 },
            { 2014, 5, 26, -3, DAYS,  2014,  5, 23 },

            // adding weeks, including roll-over across month boundaries
            { 2014, 5, 26,  0, WEEKS, 2014,  5, 26 },
            { 2014, 5, 26,  3, WEEKS, 2014,  6, 16 },
            { 2014, 5, 26, -5, WEEKS, 2014,  4, 21 },

            // adding months, including roll-over into the next/previous year
            { 2014, 5, 26,  0, MONTHS, 2014, 5, 26 },
            { 2014, 5, 26,  3, MONTHS, 2014, 8, 26 },
            { 2014, 5, 26, -5, MONTHS, 2013, 12, 26 },

            // adding years
            { 2014, 5, 26,  0, YEARS, 2014, 5, 26 },
            { 2014, 5, 26,  3, YEARS, 2017, 5, 26 },
            { 2014, 5, 26, -5, YEARS, 2009, 5, 26 },

            // adding decades
            { 2014, 5, 26,  0, DECADES, 2014, 5, 26 },
            { 2014, 5, 26,  3, DECADES, 2044, 5, 26 },
            { 2014, 5, 26, -5, DECADES, 1964, 5, 26 },

            // adding centuries
            { 2014, 5, 26,  0, CENTURIES, 2014, 5, 26 },
            { 2014, 5, 26,  3, CENTURIES, 2314, 5, 26 },
            { 2014, 5, 26, -5, CENTURIES, 1514, 5, 26 },

            // adding millennia
            { 2014, 5, 26,  0, MILLENNIA, 2014, 5, 26 },
            { 2014, 5, 26,  3, MILLENNIA, 5014, 5, 26 },
            { 2014, 5, 26, -1, MILLENNIA, 2014 - 1000, 5, 26 },

            // adding weeks that cross a year boundary
            { 2014, 12, 26,  3, WEEKS, 2015, 1, 17 },
            { 2014,  1, 26, -5, WEEKS, 2013, 12, 21 },
            { 2012,  6, 26,  3, WEEKS, 2012, 7, 17 },
            { 2012,  7, 26, -5, WEEKS, 2012, 6, 21 },

            // adding more than a full year's worth of weeks
            { 2012, 6, 21,     52 + 1, WEEKS, 2013, 6, 28 },
            { 2013, 6, 21, 6 * 52 + 1, WEEKS, 2019, 6, 21 },
        };
    }

    @ParameterizedTest
    @MethodSource("data_plus")
    public void test_plus_TemporalUnit(int year, int month, int dom, long amount, TemporalUnit unit,
            int expectedYear, int expectedMonth, int expectedDom) {
        Symmetry010Date start = Symmetry010Date.of(year, month, dom);
        Symmetry010Date expected = Symmetry010Date.of(expectedYear, expectedMonth, expectedDom);
        assertEquals(expected, start.plus(amount, unit));
    }
}
