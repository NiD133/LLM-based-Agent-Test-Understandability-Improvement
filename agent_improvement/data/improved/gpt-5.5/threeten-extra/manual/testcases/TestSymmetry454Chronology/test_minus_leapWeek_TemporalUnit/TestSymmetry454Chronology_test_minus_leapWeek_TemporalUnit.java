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
public class TestSymmetry454Chronology_test_minus_leapWeek_TemporalUnit {

    public static Object[][] data_plus_leapWeek() {
        return new Object[][] {
                { 2015, 12, 28, 0, DAYS, 2015, 12, 28 },
                { 2015, 12, 28, 8, DAYS, 2016, 1, 1 },
                { 2015, 12, 28, -3, DAYS, 2015, 12, 25 },
                { 2015, 12, 28, 0, WEEKS, 2015, 12, 28 },
                { 2015, 12, 28, 3, WEEKS, 2016, 1, 14 },
                { 2015, 12, 28, -5, WEEKS, 2015, 11, 28 },
                { 2015, 12, 28, 52, WEEKS, 2016, 12, 21 },
                { 2015, 12, 28, 0, MONTHS, 2015, 12, 28 },
                { 2015, 12, 28, 3, MONTHS, 2016, 3, 28 },
                { 2015, 12, 28, -5, MONTHS, 2015, 7, 28 },
                { 2015, 12, 28, 12, MONTHS, 2016, 12, 28 },
                { 2015, 12, 28, 0, YEARS, 2015, 12, 28 },
                { 2015, 12, 28, 3, YEARS, 2018, 12, 28 },
                { 2015, 12, 28, -5, YEARS, 2010, 12, 28 },
        };
    }

    @ParameterizedTest
    @MethodSource("data_plus_leapWeek")
    public void test_minus_leapWeek_TemporalUnit(
            int expectedYear,
            int expectedMonth,
            int expectedDom,
            long amount,
            TemporalUnit unit,
            int year,
            int month,
            int dom) {

        assertEquals(
                Symmetry454Date.of(expectedYear, expectedMonth, expectedDom),
                Symmetry454Date.of(year, month, dom).minus(amount, unit));
    }
}
