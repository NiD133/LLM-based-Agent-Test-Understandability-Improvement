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
 * Tests {@link Symmetry454Date#until(java.time.temporal.Temporal, TemporalUnit)},
 * i.e. the amount of time between two Symmetry454 dates measured in a given unit.
 */
@SuppressWarnings({ "static-method" })
public class TestSymmetry454Chronology_test_until_TemporalUnit {

    /**
     * Each row is: start date (year/month/day), end date (year/month/day),
     * the unit to measure in, and the expected amount returned by {@code until}.
     */
    public static Object[][] data_until() {
        return new Object[][] {
            // DAYS: plain day count, including a negative (end before start)
            { 2014, 5, 26, 2014, 5, 26, DAYS, 0 },
            { 2014, 5, 26, 2014, 6, 4, DAYS, 13 },
            { 2014, 5, 26, 2014, 5, 20, DAYS, -6 },
            // WEEKS: whole weeks only, partial weeks truncate toward zero
            { 2014, 5, 26, 2014, 5, 26, WEEKS, 0 },
            { 2014, 5, 26, 2014, 6, 4, WEEKS, 0 },
            { 2014, 5, 26, 2014, 6, 5, WEEKS, 1 },
            // MONTHS: whole months only
            { 2014, 5, 26, 2014, 5, 26, MONTHS, 0 },
            { 2014, 5, 26, 2014, 6, 25, MONTHS, 0 },
            { 2014, 5, 26, 2014, 6, 26, MONTHS, 1 },
            // YEARS: whole years only
            { 2014, 5, 26, 2014, 5, 26, YEARS, 0 },
            { 2014, 5, 26, 2015, 5, 25, YEARS, 0 },
            { 2014, 5, 26, 2015, 5, 26, YEARS, 1 },
            // DECADES: whole decades only
            { 2014, 5, 26, 2014, 5, 26, DECADES, 0 },
            { 2014, 5, 26, 2024, 5, 25, DECADES, 0 },
            { 2014, 5, 26, 2024, 5, 26, DECADES, 1 },
            // CENTURIES: whole centuries only
            { 2014, 5, 26, 2014, 5, 26, CENTURIES, 0 },
            { 2014, 5, 26, 2114, 5, 25, CENTURIES, 0 },
            { 2014, 5, 26, 2114, 5, 26, CENTURIES, 1 },
            // MILLENNIA: whole millennia only
            { 2014, 5, 26, 2014, 5, 26, MILLENNIA, 0 },
            { 2014, 5, 26, 3014, 5, 25, MILLENNIA, 0 },
            { 2014, 5, 26, 3014, 5, 26, MILLENNIA, 1 },
            // ERAS: both dates share the single CE era, so the distance is 0
            { 2014, 5, 26, 3014, 5, 26, ERAS, 0 },
        };
    }

    @ParameterizedTest
    @MethodSource("data_until")
    public void test_until_TemporalUnit(int year1, int month1, int dom1, int year2, int month2, int dom2, TemporalUnit unit, long expected) {
        Symmetry454Date start = Symmetry454Date.of(year1, month1, dom1);
        Symmetry454Date end = Symmetry454Date.of(year2, month2, dom2);
        assertEquals(expected, start.until(end, unit));
    }
}
