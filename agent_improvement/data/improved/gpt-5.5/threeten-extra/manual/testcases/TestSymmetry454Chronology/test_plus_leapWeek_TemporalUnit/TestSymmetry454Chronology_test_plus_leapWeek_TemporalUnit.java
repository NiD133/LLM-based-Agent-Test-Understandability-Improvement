package org.threeten.extra.chrono;

import static java.time.temporal.ChronoUnit.DAYS;
import static java.time.temporal.ChronoUnit.MONTHS;
import static java.time.temporal.ChronoUnit.WEEKS;
import static java.time.temporal.ChronoUnit.YEARS;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.temporal.TemporalUnit;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

@SuppressWarnings({ "static-method" })
public class TestSymmetry454Chronology_test_plus_leapWeek_TemporalUnit {

    public static Object[][] data_plus_leapWeek() {
        return new Object[][] {
                // Adding days across the extra week at the end of leap year 2015.
                { 2015, 12, 28, 0, DAYS, 2015, 12, 28 },
                { 2015, 12, 28, 8, DAYS, 2016, 1, 1 },
                { 2015, 12, 28, -3, DAYS, 2015, 12, 25 },

                // Adding weeks from the same leap-week date.
                { 2015, 12, 28, 0, WEEKS, 2015, 12, 28 },
                { 2015, 12, 28, 3, WEEKS, 2016, 1, 14 },
                { 2015, 12, 28, -5, WEEKS, 2015, 11, 28 },
                { 2015, 12, 28, 52, WEEKS, 2016, 12, 21 },

                // Adding calendar months preserves the day-of-month when valid.
                { 2015, 12, 28, 0, MONTHS, 2015, 12, 28 },
                { 2015, 12, 28, 3, MONTHS, 2016, 3, 28 },
                { 2015, 12, 28, -5, MONTHS, 2015, 7, 28 },
                { 2015, 12, 28, 12, MONTHS, 2016, 12, 28 },

                // Adding years from a leap week date.
                { 2015, 12, 28, 0, YEARS, 2015, 12, 28 },
                { 2015, 12, 28, 3, YEARS, 2018, 12, 28 },
                { 2015, 12, 28, -5, YEARS, 2010, 12, 28 },
        };
    }

    @ParameterizedTest
    @MethodSource("data_plus_leapWeek")
    public void test_plus_leapWeek_TemporalUnit(
            int year,
            int month,
            int dom,
            long amount,
            TemporalUnit unit,
            int expectedYear,
            int expectedMonth,
            int expectedDom) {

        assertEquals(
                Symmetry454Date.of(expectedYear, expectedMonth, expectedDom),
                Symmetry454Date.of(year, month, dom).plus(amount, unit));
    }
}
