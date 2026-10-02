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
 * Tests {@link DiscordianDate#minus(long, TemporalUnit)} for dates involving St. Tib's Day
 * (the Discordian leap day, represented as month=0, day=0).
 *
 * <p>In a Discordian leap year, St. Tib's Day is inserted between the 59th and 60th
 * day of the first season (month 1). Subtracting units that cross this boundary must
 * correctly skip or land on the leap day.
 */
public class TestDiscordianChronology_test_minus_leap_TemporalUnit {

    /**
     * Provides test cases for {@code minus(amount, unit)} involving leap-day dates.
     *
     * <p>Each row is: { sourceYear, sourceMonth, sourceDom, amount, unit,
     *                   expectedYear, expectedMonth, expectedDom }
     *
     * <p>Month 0 / day 0 represents St. Tib's Day (the Discordian leap day).
     */
    public static Object[][] data_minus_leap() {
        return new Object[][] {
            // --- DAYS ---
            // Subtracting 0 days from St. Tib's Day leaves it unchanged
            { 2014, 0, 0,  0, DAYS, 2014, 0, 0 },
            // Subtracting 8 days from St. Tib's Day lands before the leap day
            { 2014, 1, 52,  8, DAYS, 2014, 0, 0 },
            // Subtracting -3 days (i.e. adding 3) from St. Tib's Day moves past the leap day
            { 2014, 1, 62, -3, DAYS, 2014, 0, 0 },

            // --- WEEKS ---
            // Subtracting 0 weeks from St. Tib's Day leaves it unchanged
            { 2014, 0, 0,  0, WEEKS, 2014, 0, 0 },
            // Subtracting 3 weeks from St. Tib's Day
            { 2014, 1, 45,  3, WEEKS, 2014, 0, 0 },
            // Subtracting -5 weeks (adding 5) from St. Tib's Day
            { 2014, 2, 12, -5, WEEKS, 2014, 0, 0 },
            // Subtracting exactly 73*4 weeks spans 4 leap-year cycles back to another St. Tib's Day
            { 2010, 0, 0, 73 * 4, WEEKS, 2014, 0, 0 },

            // --- MONTHS ---
            // Subtracting 0 months from St. Tib's Day leaves it unchanged
            { 2014, 0, 0,  0, MONTHS, 2014, 0, 0 },
            // Subtracting 3 months, starting from a normal date, lands on St. Tib's Day
            { 2013, 3, 60,  3, MONTHS, 2014, 0, 0 },
            // Subtracting -5 months (adding 5), starting from a normal date, lands on St. Tib's Day
            { 2015, 1, 60, -5, MONTHS, 2014, 0, 0 },
            // Subtracting 20 months from another St. Tib's Day (4 years earlier) reaches St. Tib's Day 2014
            { 2010, 0, 0, 20, MONTHS, 2014, 0, 0 },

            // --- YEARS ---
            // Subtracting 0 years from St. Tib's Day leaves it unchanged
            { 2014, 0, 0,  0, YEARS, 2014, 0, 0 },
            // Subtracting 3 years from a normal date lands on St. Tib's Day
            { 2011, 1, 60,  3, YEARS, 2014, 0, 0 },
            // Subtracting -5 years (adding 5) from a normal date lands on St. Tib's Day
            { 2019, 1, 60, -5, YEARS, 2014, 0, 0 },
            // Subtracting 4 years from the previous leap St. Tib's Day lands on St. Tib's Day 2014
            { 2010, 0, 0,  4, YEARS, 2014, 0, 0 },
        };
    }

    @ParameterizedTest
    @MethodSource("data_minus_leap")
    public void test_minus_leap_TemporalUnit(
            int expectedYear, int expectedMonth, int expectedDom,
            long amount, TemporalUnit unit,
            int year, int month, int dom) {
        assertEquals(
            DiscordianDate.of(expectedYear, expectedMonth, expectedDom),
            DiscordianDate.of(year, month, dom).minus(amount, unit));
    }
}
