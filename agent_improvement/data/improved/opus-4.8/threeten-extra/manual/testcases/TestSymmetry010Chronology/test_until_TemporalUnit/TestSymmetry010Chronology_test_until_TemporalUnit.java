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

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Tests {@link Symmetry010Date#until(java.time.temporal.Temporal, TemporalUnit)}.
 * <p>
 * Each case measures the amount of time, expressed in a given {@link TemporalUnit},
 * between a {@code start} date and an {@code end} date in the Symmetry010 calendar.
 */
@SuppressWarnings({ "static-method" })
public class TestSymmetry010Chronology_test_until_TemporalUnit {

    /**
     * Cases for {@link #test_until_TemporalUnit}, one per row.
     * <p>
     * Columns: start (year, month, day), end (year, month, day), unit, expected amount.
     * The expected amount is truncated towards zero, so partial units count as 0.
     */
    public static Object[][] data_until() {
        return new Object[][] {
            // Measured in DAYS: exact day difference, can be zero or negative.
            { 2014, 5, 26, 2014, 5, 26, DAYS, 0 },
            { 2014, 5, 26, 2014, 6, 4, DAYS, 9 },
            { 2014, 5, 26, 2014, 5, 20, DAYS, -6 },

            // Measured in WEEKS: whole 7-day weeks only.
            { 2014, 5, 26, 2014, 5, 26, WEEKS, 0 },
            { 2014, 5, 26, 2014, 6, 1, WEEKS, 1 },
            { 2014, 5, 26, 2014, 6, 5, WEEKS, 1 },

            // Measured in MONTHS: whole months only; same day-of-month one month later is 1.
            { 2014, 5, 26, 2014, 5, 26, MONTHS, 0 },
            { 2014, 5, 26, 2014, 6, 25, MONTHS, 0 },
            { 2014, 5, 26, 2014, 6, 26, MONTHS, 1 },

            // Measured in YEARS: whole years only.
            { 2014, 5, 26, 2014, 5, 26, YEARS, 0 },
            { 2014, 5, 26, 2015, 5, 25, YEARS, 0 },
            { 2014, 5, 26, 2015, 5, 26, YEARS, 1 },

            // Measured in DECADES: whole 10-year spans only.
            { 2014, 5, 26, 2014, 5, 26, DECADES, 0 },
            { 2014, 5, 26, 2024, 5, 25, DECADES, 0 },
            { 2014, 5, 26, 2024, 5, 26, DECADES, 1 },

            // Measured in CENTURIES: whole 100-year spans only.
            { 2014, 5, 26, 2014, 5, 26, CENTURIES, 0 },
            { 2014, 5, 26, 2114, 5, 25, CENTURIES, 0 },
            { 2014, 5, 26, 2114, 5, 26, CENTURIES, 1 },

            // Measured in MILLENNIA: whole 1000-year spans only.
            { 2014, 5, 26, 2014, 5, 26, MILLENNIA, 0 },
            { 2014, 5, 26, 3014, 5, 25, MILLENNIA, 0 },
            { 2014, 5, 26, 3014, 5, 26, MILLENNIA, 1 },

            // Measured in ERAS: both dates share the same (CE) era, so the distance is 0.
            { 2014, 5, 26, 3014, 5, 26, ERAS, 0 },
        };
    }

    @ParameterizedTest
    @MethodSource("data_until")
    public void test_until_TemporalUnit(
            int startYear, int startMonth, int startDay,
            int endYear, int endMonth, int endDay,
            TemporalUnit unit, long expectedAmount) {
        Symmetry010Date start = Symmetry010Date.of(startYear, startMonth, startDay);
        Symmetry010Date end = Symmetry010Date.of(endYear, endMonth, endDay);

        assertEquals(expectedAmount, start.until(end, unit));
    }
}
