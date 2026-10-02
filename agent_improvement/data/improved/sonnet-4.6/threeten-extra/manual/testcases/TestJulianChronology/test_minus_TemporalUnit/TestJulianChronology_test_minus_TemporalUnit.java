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

public class TestJulianChronology_test_minus_TemporalUnit {

    public static Object[][] data_minus() {
        return new Object[][] {
            // { startYear, startMonth, startDay, amount, unit, expectedYear, expectedMonth, expectedDay }
            { 2014, 5, 26,  0, DAYS,      2014,  5, 26 },
            { 2014, 6,  3,  8, DAYS,      2014,  5, 26 },
            { 2014, 5, 23, -3, DAYS,      2014,  5, 26 },
            { 2014, 5, 26,  0, WEEKS,     2014,  5, 26 },
            { 2014, 6, 16,  3, WEEKS,     2014,  5, 26 },
            { 2014, 4, 21, -5, WEEKS,     2014,  5, 26 },
            { 2014, 5, 26,  0, MONTHS,    2014,  5, 26 },
            { 2014, 8, 26,  3, MONTHS,    2014,  5, 26 },
            { 2013, 12, 26, -5, MONTHS,   2014,  5, 26 },
            { 2014, 5, 26,  0, YEARS,     2014,  5, 26 },
            { 2017, 5, 26,  3, YEARS,     2014,  5, 26 },
            { 2009, 5, 26, -5, YEARS,     2014,  5, 26 },
            { 2014, 5, 26,  0, DECADES,   2014,  5, 26 },
            { 2044, 5, 26,  3, DECADES,   2014,  5, 26 },
            { 1964, 5, 26, -5, DECADES,   2014,  5, 26 },
            { 2014, 5, 26,  0, CENTURIES, 2014,  5, 26 },
            { 2314, 5, 26,  3, CENTURIES, 2014,  5, 26 },
            { 1514, 5, 26, -5, CENTURIES, 2014,  5, 26 },
            { 2014, 5, 26,  0, MILLENNIA, 2014,  5, 26 },
            { 5014, 5, 26,  3, MILLENNIA, 2014,  5, 26 },
            { -2986, 5, 26, -5, MILLENNIA, 2014, 5, 26 },
            { -2013, 5, 26, -1, ERAS,     2014,  5, 26 },
        };
    }

    @ParameterizedTest
    @MethodSource("data_minus")
    public void test_minus_TemporalUnit(
            int startYear, int startMonth, int startDay,
            long amount, TemporalUnit unit,
            int expectedYear, int expectedMonth, int expectedDay) {
        assertEquals(
            JulianDate.of(expectedYear, expectedMonth, expectedDay),
            JulianDate.of(startYear, startMonth, startDay).minus(amount, unit));
    }
}
