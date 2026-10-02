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

    public static Object[][] data_plus() {
        return new Object[][] {
                { 2014, 5, 26, 0, DAYS, 2014, 5, 26 },
                { 2014, 5, 26, 8, DAYS, 2014, 5, 34 },
                { 2014, 5, 26, -3, DAYS, 2014, 5, 23 },
                { 2014, 5, 26, 0, WEEKS, 2014, 5, 26 },
                { 2014, 5, 26, 3, WEEKS, 2014, 5, 41 },
                { 2014, 5, 26, -5, WEEKS, 2014, 5, 1 },
                { 2014, 5, 26, 0, MONTHS, 2014, 5, 26 },
                { 2014, 5, 26, 3, MONTHS, 2015, 3, 26 },
                { 2014, 5, 26, -5, MONTHS, 2013, 5, 26 },
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
                { 2014, 5, 26, -1, MILLENNIA, 2014 - 1000, 5, 26 }
        };
    }

    @ParameterizedTest
    @MethodSource("data_plus")
    public void test_minus_TemporalUnit(
            int expectedYear,
            int expectedMonth,
            int expectedDayOfMonth,
            long amountToSubtract,
            TemporalUnit unit,
            int startYear,
            int startMonth,
            int startDayOfMonth) {

        DiscordianDate expectedDate = DiscordianDate.of(expectedYear, expectedMonth, expectedDayOfMonth);
        DiscordianDate startDate = DiscordianDate.of(startYear, startMonth, startDayOfMonth);

        assertEquals(expectedDate, startDate.minus(amountToSubtract, unit));
    }
}
