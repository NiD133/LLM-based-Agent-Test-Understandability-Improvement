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
 * Tests {@link DiscordianDate#plus(long, TemporalUnit)} when the addition crosses,
 * lands on, or starts from St. Tib's Day.
 * <p>
 * St. Tib's Day is the Discordian leap day. It is modelled with month {@code 0} and
 * day-of-month {@code 0}, sitting between days 59 and 60 of season 1 in a leap year.
 * Adding an amount to (or that reaches) this special day is the behaviour exercised here.
 */
public class TestDiscordianChronology_test_plus_leap_TemporalUnit {

    /** Month and day-of-month values that denote St. Tib's Day (the Discordian leap day). */
    private static final int ST_TIBS_MONTH = 0;
    private static final int ST_TIBS_DOM = 0;

    /**
     * Each case: start date (year, month, dom), amount and unit to add,
     * then the expected resulting date (year, month, dom).
     */
    public static Object[][] data_plus_leap() {
        return new Object[][] {
            // Adding days, starting from St. Tib's Day
            { 2014, ST_TIBS_MONTH, ST_TIBS_DOM, 0, DAYS, 2014, ST_TIBS_MONTH, ST_TIBS_DOM },
            { 2014, ST_TIBS_MONTH, ST_TIBS_DOM, 8, DAYS, 2014, 1, 67 },
            { 2014, ST_TIBS_MONTH, ST_TIBS_DOM, -3, DAYS, 2014, 1, 57 },

            // Adding weeks, starting from St. Tib's Day
            { 2014, ST_TIBS_MONTH, ST_TIBS_DOM, 0, WEEKS, 2014, ST_TIBS_MONTH, ST_TIBS_DOM },
            { 2014, ST_TIBS_MONTH, ST_TIBS_DOM, 3, WEEKS, 2014, 2, 2 },
            { 2014, ST_TIBS_MONTH, ST_TIBS_DOM, -5, WEEKS, 2014, 1, 35 },
            // Four whole years of weeks lands back on the next leap day's St. Tib's Day
            { 2014, ST_TIBS_MONTH, ST_TIBS_DOM, 73 * 4, WEEKS, 2018, ST_TIBS_MONTH, ST_TIBS_DOM },

            // Adding months, starting from St. Tib's Day
            { 2014, ST_TIBS_MONTH, ST_TIBS_DOM, 0, MONTHS, 2014, ST_TIBS_MONTH, ST_TIBS_DOM },
            { 2014, ST_TIBS_MONTH, ST_TIBS_DOM, 3, MONTHS, 2014, 4, 60 },
            { 2014, ST_TIBS_MONTH, ST_TIBS_DOM, -5, MONTHS, 2013, 1, 60 },
            { 2014, ST_TIBS_MONTH, ST_TIBS_DOM, 20, MONTHS, 2018, ST_TIBS_MONTH, ST_TIBS_DOM },

            // Adding years, starting from St. Tib's Day
            { 2014, ST_TIBS_MONTH, ST_TIBS_DOM, 0, YEARS, 2014, ST_TIBS_MONTH, ST_TIBS_DOM },
            { 2014, ST_TIBS_MONTH, ST_TIBS_DOM, 3, YEARS, 2017, 1, 60 },
            { 2014, ST_TIBS_MONTH, ST_TIBS_DOM, -5, YEARS, 2009, 1, 60 },
            // Adding four years reaches the next leap year, landing on St. Tib's Day again
            { 2014, ST_TIBS_MONTH, ST_TIBS_DOM, 4, YEARS, 2018, ST_TIBS_MONTH, ST_TIBS_DOM },
        };
    }

    @ParameterizedTest
    @MethodSource("data_plus_leap")
    public void test_plus_leap_TemporalUnit(int year, int month, int dom, long amount, TemporalUnit unit,
            int expectedYear, int expectedMonth, int expectedDom) {
        DiscordianDate start = DiscordianDate.of(year, month, dom);
        DiscordianDate expected = DiscordianDate.of(expectedYear, expectedMonth, expectedDom);

        assertEquals(expected, start.plus(amount, unit));
    }
}
