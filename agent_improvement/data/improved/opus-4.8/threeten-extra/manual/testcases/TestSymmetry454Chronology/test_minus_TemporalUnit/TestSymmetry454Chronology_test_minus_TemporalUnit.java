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
 * Tests {@link Symmetry454Date#minus(long, TemporalUnit)}.
 *
 * <p>Each case starts from a base date, subtracts {@code amount} of the given
 * {@link TemporalUnit}, and checks the result equals the expected date.
 */
@SuppressWarnings({ "static-method" })
public class TestSymmetry454Chronology_test_minus_TemporalUnit {

    /**
     * Cases for {@link #test_minus_TemporalUnit}.
     *
     * <p>Columns: {@code baseYear, baseMonth, baseDay, amount, unit,
     * expectedYear, expectedMonth, expectedDay}. Each row asserts that
     * subtracting {@code amount} {@code unit}s from the base date yields the
     * expected date.
     */
    public static Object[][] data_minus() {
        return new Object[][] {
            // Subtracting days.
            { 2014, 5, 26,  0, DAYS, 2014, 5, 26 },
            { 2014, 5, 34,  8, DAYS, 2014, 5, 26 },
            { 2014, 5, 23, -3, DAYS, 2014, 5, 26 },

            // Subtracting weeks within a single year.
            { 2014, 5, 26,  0, WEEKS, 2014, 5, 26 },
            { 2014, 6, 12,  3, WEEKS, 2014, 5, 26 },
            { 2014, 4, 19, -5, WEEKS, 2014, 5, 26 },

            // Subtracting months.
            { 2014,  5, 26,  0, MONTHS, 2014, 5, 26 },
            { 2014,  8, 26,  3, MONTHS, 2014, 5, 26 },
            { 2013, 12, 26, -5, MONTHS, 2014, 5, 26 },

            // Subtracting years.
            { 2014, 5, 26,  0, YEARS, 2014, 5, 26 },
            { 2017, 5, 26,  3, YEARS, 2014, 5, 26 },
            { 2009, 5, 26, -5, YEARS, 2014, 5, 26 },

            // Subtracting decades.
            { 2014, 5, 26,  0, DECADES, 2014, 5, 26 },
            { 2044, 5, 26,  3, DECADES, 2014, 5, 26 },
            { 1964, 5, 26, -5, DECADES, 2014, 5, 26 },

            // Subtracting centuries.
            { 2014, 5, 26,  0, CENTURIES, 2014, 5, 26 },
            { 2314, 5, 26,  3, CENTURIES, 2014, 5, 26 },
            { 1514, 5, 26, -5, CENTURIES, 2014, 5, 26 },

            // Subtracting millennia.
            { 2014, 5, 26,  0, MILLENNIA, 2014, 5, 26 },
            { 5014, 5, 26,  3, MILLENNIA, 2014, 5, 26 },
            { 2014 - 1000, 5, 26, -1, MILLENNIA, 2014, 5, 26 },

            // Subtracting weeks that cross year boundaries.
            { 2015,  1, 19,  3, WEEKS, 2014, 12, 26 },
            { 2013, 12, 19, -5, WEEKS, 2014,  1, 26 },
            { 2012,  7, 19,  3, WEEKS, 2012,  6, 26 },
            { 2012,  6, 19, -5, WEEKS, 2012,  7, 26 },

            // Subtracting whole years' worth of weeks.
            { 2013, 6, 28,     52 + 1, WEEKS, 2012, 6, 21 },
            { 2019, 6, 21, 6 * 52 + 1, WEEKS, 2013, 6, 21 },
        };
    }

    @ParameterizedTest
    @MethodSource("data_minus")
    public void test_minus_TemporalUnit(int baseYear, int baseMonth, int baseDay, long amount, TemporalUnit unit,
            int expectedYear, int expectedMonth, int expectedDay) {
        Symmetry454Date base = Symmetry454Date.of(baseYear, baseMonth, baseDay);
        Symmetry454Date expected = Symmetry454Date.of(expectedYear, expectedMonth, expectedDay);

        assertEquals(expected, base.minus(amount, unit));
    }
}
