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
 * Tests {@link DiscordianDate#plus(long, TemporalUnit)} when the starting date is St. Tib's Day.
 *
 * <p>In the Discordian calendar, St. Tib's Day is the leap-day inserted between day 59 and day 60
 * of the first month (Chaos) in a leap year.  It belongs to no regular month and is represented as
 * month&nbsp;=&nbsp;0, day&nbsp;=&nbsp;0.  Because it sits outside the normal month structure,
 * arithmetic from this date requires special handling.
 */
public class TestDiscordianChronology_test_plus_leap_TemporalUnit {

    // St. Tib's Day in a Discordian leap year is encoded as month=0, day=0.
    // Discordian year 2014 is a leap year (ISO 2014 - 1166 = 848; 848 % 4 == 0).
    // Discordian year 2018 is also a leap year (2018 - 1166 = 852; 852 % 4 == 0).
    public static Object[][] data_plus_leap() {
        return new Object[][] {
            // --- DAYS ---
            // Adding 0 days to St. Tib's Day leaves it unchanged
            { 2014, 0, 0,   0, DAYS,   2014, 0,  0 },
            // Adding 8 days crosses into month 1 (day 59 + 1 [St.Tib] + 8 = day 67 of Chaos)
            { 2014, 0, 0,   8, DAYS,   2014, 1, 67 },
            // Subtracting 3 days moves back into Chaos before St. Tib's Day
            { 2014, 0, 0,  -3, DAYS,   2014, 1, 57 },

            // --- WEEKS (1 week = 5 days) ---
            { 2014, 0, 0,   0, WEEKS,  2014, 0,  0 },
            { 2014, 0, 0,   3, WEEKS,  2014, 2,  2 },
            { 2014, 0, 0,  -5, WEEKS,  2014, 1, 35 },
            // 73*4 weeks spans exactly four Discordian years, landing on the next St. Tib's Day
            { 2014, 0, 0, 73 * 4, WEEKS, 2018, 0,  0 },

            // --- MONTHS ---
            { 2014, 0, 0,   0, MONTHS, 2014, 0,  0 },
            // +3 months from St. Tib's Day (treated as end of Chaos) lands at end of Bureaucracy
            { 2014, 0, 0,   3, MONTHS, 2014, 4, 60 },
            { 2014, 0, 0,  -5, MONTHS, 2013, 1, 60 },
            // +20 months (4 full years) lands on the next St. Tib's Day in 2018
            { 2014, 0, 0,  20, MONTHS, 2018, 0,  0 },

            // --- YEARS ---
            { 2014, 0, 0,   0, YEARS,  2014, 0,  0 },
            // Non-leap target years get day 60 of Chaos (the day after St. Tib's Day would be)
            { 2014, 0, 0,   3, YEARS,  2017, 1, 60 },
            { 2014, 0, 0,  -5, YEARS,  2009, 1, 60 },
            // +4 years hits the next Discordian leap year, yielding another St. Tib's Day
            { 2014, 0, 0,   4, YEARS,  2018, 0,  0 },
        };
    }

    @ParameterizedTest
    @MethodSource("data_plus_leap")
    public void test_plus_leap_TemporalUnit(
            int year, int month, int dom,
            long amount, TemporalUnit unit,
            int expectedYear, int expectedMonth, int expectedDom) {

        DiscordianDate start    = DiscordianDate.of(year, month, dom);
        DiscordianDate expected = DiscordianDate.of(expectedYear, expectedMonth, expectedDom);

        assertEquals(expected, start.plus(amount, unit));
    }
}
