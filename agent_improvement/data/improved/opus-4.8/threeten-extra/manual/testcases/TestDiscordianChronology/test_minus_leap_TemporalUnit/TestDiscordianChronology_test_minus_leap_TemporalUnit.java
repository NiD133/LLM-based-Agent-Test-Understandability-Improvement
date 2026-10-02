package org.threeten.extra.chrono;

import static java.time.temporal.ChronoUnit.DAYS;
import static java.time.temporal.ChronoUnit.MONTHS;
import static java.time.temporal.ChronoUnit.WEEKS;
import static java.time.temporal.ChronoUnit.YEARS;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.temporal.TemporalUnit;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Verifies {@link DiscordianDate#minus(long, TemporalUnit)} for cases that land on
 * (or start from) St. Tib's Day, the leap day that sits in its own pseudo-month
 * represented as month/day {@code 0/0}.
 */
public class TestDiscordianChronology_test_minus_leap_TemporalUnit {

    /**
     * Each row describes one subtraction:
     * <pre>
     *   { expectedYear, expectedMonth, expectedDom,   // the date we expect to get back
     *     amount, unit,                                // the amount and unit to subtract
     *     baseYear, baseMonth, baseDom }               // the starting date
     * </pre>
     * i.e. {@code DiscordianDate.of(base...).minus(amount, unit)} must equal
     * {@code DiscordianDate.of(expected...)}.
     */
    public static Object[][] data_minus_leap() {
        return new Object[][] {
            // Subtracting days, crossing St. Tib's Day
            { 2014, 0, 0, 0, DAYS, 2014, 0, 0 },
            { 2014, 1, 52, 8, DAYS, 2014, 0, 0 },
            { 2014, 1, 62, -3, DAYS, 2014, 0, 0 },
            // Subtracting weeks, crossing St. Tib's Day
            { 2014, 0, 0, 0, WEEKS, 2014, 0, 0 },
            { 2014, 1, 45, 3, WEEKS, 2014, 0, 0 },
            { 2014, 2, 12, -5, WEEKS, 2014, 0, 0 },
            { 2010, 0, 0, 73 * 4, WEEKS, 2014, 0, 0 },
            // Subtracting months, crossing St. Tib's Day
            { 2014, 0, 0, 0, MONTHS, 2014, 0, 0 },
            { 2013, 3, 60, 3, MONTHS, 2014, 0, 0 },
            { 2015, 1, 60, -5, MONTHS, 2014, 0, 0 },
            { 2010, 0, 0, 20, MONTHS, 2014, 0, 0 },
            // Subtracting years, crossing St. Tib's Day
            { 2014, 0, 0, 0, YEARS, 2014, 0, 0 },
            { 2011, 1, 60, 3, YEARS, 2014, 0, 0 },
            { 2019, 1, 60, -5, YEARS, 2014, 0, 0 },
            { 2010, 0, 0, 4, YEARS, 2014, 0, 0 },
        };
    }

    @ParameterizedTest
    @MethodSource("data_minus_leap")
    public void test_minus_leap_TemporalUnit(int expectedYear, int expectedMonth, int expectedDom,
            long amount, TemporalUnit unit, int baseYear, int baseMonth, int baseDom) {
        DiscordianDate expected = DiscordianDate.of(expectedYear, expectedMonth, expectedDom);
        DiscordianDate actual = DiscordianDate.of(baseYear, baseMonth, baseDom).minus(amount, unit);

        assertEquals(expected, actual);
    }
}
