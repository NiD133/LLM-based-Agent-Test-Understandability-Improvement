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

@SuppressWarnings("static-method")
public class TestSymmetry454Chronology_test_minus_TemporalUnit {

    public static Object[][] data_plus() {
        return new Object[][] {
                { 2014, 5, 26, 0, DAYS, 2014, 5, 26 },
                { 2014, 5, 26, 8, DAYS, 2014, 5, 34 },
                { 2014, 5, 26, -3, DAYS, 2014, 5, 23 },
                { 2014, 5, 26, 0, WEEKS, 2014, 5, 26 },
                { 2014, 5, 26, 3, WEEKS, 2014, 6, 12 },
                { 2014, 5, 26, -5, WEEKS, 2014, 4, 19 },
                { 2014, 5, 26, 0, MONTHS, 2014, 5, 26 },
                { 2014, 5, 26, 3, MONTHS, 2014, 8, 26 },
                { 2014, 5, 26, -5, MONTHS, 2013, 12, 26 },
                { 2014, 5, 26, 0, YEARS, 2014, 5, 26 },
                { 2014, 5, 26, 3, YEARS, 2017, 5, 26 },
                { 2014, 5, 26, -5, YEARS, 2009, 5, 26 },
                { 2014, 5, 26, 0, DECADES, 2014, 5, 26 },
                { 2014, 5, 26, 3, DECADES, 2044, 5, 26 },
                { 2014, 5, 26, -5, DECADES, 1964, 5, 26 },
                { 2014, 5, 26, 0, CENTURIES, 2014, 5, 26 },
                { 2014, 5, 26, 3, CENTURIES, 2314, 5, 26 },
                { 2014, 5, 26, -5, CENTURIES, 1514, 5, 26 },
                { 2014, 5, 26, 0, MILLENNIA, 2014, 5, 26 },
                { 2014, 5, 26, 3, MILLENNIA, 5014, 5, 26 },
                { 2014, 5, 26, -1, MILLENNIA, 2014 - 1000, 5, 26 },
                { 2014, 12, 26, 3, WEEKS, 2015, 1, 19 },
                { 2014, 1, 26, -5, WEEKS, 2013, 12, 19 },
                { 2012, 6, 26, 3, WEEKS, 2012, 7, 19 },
                { 2012, 7, 26, -5, WEEKS, 2012, 6, 19 },
                { 2012, 6, 21, 52 + 1, WEEKS, 2013, 6, 28 },
                { 2013, 6, 21, 6 * 52 + 1, WEEKS, 2019, 6, 21 },
        };
    }

    @ParameterizedTest
    @MethodSource("data_plus")
    public void test_minus_TemporalUnit(
            int expectedYear,
            int expectedMonth,
            int expectedDayOfMonth,
            long amount,
            TemporalUnit unit,
            int baseYear,
            int baseMonth,
            int baseDayOfMonth) {

        Symmetry454Date expected = Symmetry454Date.of(expectedYear, expectedMonth, expectedDayOfMonth);
        Symmetry454Date baseDate = Symmetry454Date.of(baseYear, baseMonth, baseDayOfMonth);

        assertEquals(expected, baseDate.minus(amount, unit));
    }
}
