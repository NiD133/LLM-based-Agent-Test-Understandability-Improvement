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

public class TestDiscordianChronology_test_minus_leap_TemporalUnit {

    public static Object[][] data_minus_leap() {
        return new Object[][] {
                { 2014, 0, 0, 0, DAYS, 2014, 0, 0 },
                { 2014, 1, 52, 8, DAYS, 2014, 0, 0 },
                { 2014, 1, 62, -3, DAYS, 2014, 0, 0 },

                { 2014, 0, 0, 0, WEEKS, 2014, 0, 0 },
                { 2014, 1, 45, 3, WEEKS, 2014, 0, 0 },
                { 2014, 2, 12, -5, WEEKS, 2014, 0, 0 },
                { 2010, 0, 0, 73 * 4, WEEKS, 2014, 0, 0 },

                { 2014, 0, 0, 0, MONTHS, 2014, 0, 0 },
                { 2013, 3, 60, 3, MONTHS, 2014, 0, 0 },
                { 2015, 1, 60, -5, MONTHS, 2014, 0, 0 },
                { 2010, 0, 0, 20, MONTHS, 2014, 0, 0 },

                { 2014, 0, 0, 0, YEARS, 2014, 0, 0 },
                { 2011, 1, 60, 3, YEARS, 2014, 0, 0 },
                { 2019, 1, 60, -5, YEARS, 2014, 0, 0 },
                { 2010, 0, 0, 4, YEARS, 2014, 0, 0 }
        };
    }

    @ParameterizedTest
    @MethodSource("data_minus_leap")
    public void test_minus_leap_TemporalUnit(
            int expectedYear,
            int expectedMonth,
            int expectedDayOfMonth,
            long amount,
            TemporalUnit unit,
            int year,
            int month,
            int dayOfMonth) {

        DiscordianDate expected = DiscordianDate.of(expectedYear, expectedMonth, expectedDayOfMonth);
        DiscordianDate actual = DiscordianDate.of(year, month, dayOfMonth).minus(amount, unit);

        assertEquals(expected, actual);
    }
}
