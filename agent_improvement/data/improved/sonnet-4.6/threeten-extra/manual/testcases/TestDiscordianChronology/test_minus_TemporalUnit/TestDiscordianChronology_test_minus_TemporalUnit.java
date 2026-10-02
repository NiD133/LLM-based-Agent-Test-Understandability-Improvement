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

public class TestDiscordianChronology_test_minus_TemporalUnit {

    // Each row: fromYear, fromMonth, fromDom, amount, unit, expectedYear, expectedMonth, expectedDom
    // Asserts: DiscordianDate.of(fromYear, fromMonth, fromDom).minus(amount, unit)
    //          == DiscordianDate.of(expectedYear, expectedMonth, expectedDom)
    // Data is the inverse of the plus operation: start from the result of plus, subtract the same
    // amount, and expect to arrive back at the original start date.
    public static Object[][] data_minus_TemporalUnit() {
        return new Object[][] {
            { 2014, 5, 26,  0, DAYS,     2014, 5, 26 },
            { 2014, 5, 34,  8, DAYS,     2014, 5, 26 },
            { 2014, 5, 23, -3, DAYS,     2014, 5, 26 },
            { 2014, 5, 26,  0, WEEKS,    2014, 5, 26 },
            { 2014, 5, 41,  3, WEEKS,    2014, 5, 26 },
            { 2014, 5,  1, -5, WEEKS,    2014, 5, 26 },
            { 2014, 5, 26,  0, MONTHS,   2014, 5, 26 },
            { 2015, 3, 26,  3, MONTHS,   2014, 5, 26 },
            { 2013, 5, 26, -5, MONTHS,   2014, 5, 26 },
            { 2014, 5, 26,  0, YEARS,    2014, 5, 26 },
            { 2017, 5, 26,  3, YEARS,    2014, 5, 26 },
            { 2009, 5, 26, -5, YEARS,    2014, 5, 26 },
            { 2014, 5, 26,  0, DECADES,  2014, 5, 26 },
            { 2044, 5, 26,  3, DECADES,  2014, 5, 26 },
            { 1964, 5, 26, -5, DECADES,  2014, 5, 26 },
            { 2014, 5, 26,  0, CENTURIES, 2014, 5, 26 },
            { 2314, 5, 26,  3, CENTURIES, 2014, 5, 26 },
            { 1514, 5, 26, -5, CENTURIES, 2014, 5, 26 },
            { 2014, 5, 26,  0, MILLENNIA, 2014, 5, 26 },
            { 5014, 5, 26,  3, MILLENNIA, 2014, 5, 26 },
            { 2014 - 1000, 5, 26, -1, MILLENNIA, 2014, 5, 26 },
        };
    }

    @ParameterizedTest
    @MethodSource("data_minus_TemporalUnit")
    public void test_minus_TemporalUnit(
            int fromYear, int fromMonth, int fromDom,
            long amount, TemporalUnit unit,
            int expectedYear, int expectedMonth, int expectedDom) {
        assertEquals(
            DiscordianDate.of(expectedYear, expectedMonth, expectedDom),
            DiscordianDate.of(fromYear, fromMonth, fromDom).minus(amount, unit));
    }
}
