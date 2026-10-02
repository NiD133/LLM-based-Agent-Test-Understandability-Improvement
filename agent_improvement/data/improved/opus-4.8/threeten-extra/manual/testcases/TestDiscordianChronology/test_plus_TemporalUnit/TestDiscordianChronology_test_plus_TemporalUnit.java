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

/**
 * Tests {@link DiscordianDate#plus(long, TemporalUnit)}.
 *
 * <p>Each Discordian year has 5 months of 73 days (a 5-day week), so adding a
 * unit of time advances the date according to those calendar rules rather than
 * the ISO rules.
 */
public class TestDiscordianChronology_test_plus_TemporalUnit {

    /**
     * Cases for {@link #test_plus_TemporalUnit}.
     *
     * <p>Columns:
     * <pre>
     *   startYear, startMonth, startDom,   // the date to start from
     *   amount, unit,                      // the amount of time to add
     *   expectedYear, expectedMonth, expectedDom   // the resulting date
     * </pre>
     */
    public static Object[][] data_plus() {
        return new Object[][] {
            // Adding days stays within the same month here (73 days per month).
            { 2014, 5, 26, 0, DAYS, 2014, 5, 26 },
            { 2014, 5, 26, 8, DAYS, 2014, 5, 34 },
            { 2014, 5, 26, -3, DAYS, 2014, 5, 23 },

            // Adding weeks (5 days each).
            { 2014, 5, 26, 0, WEEKS, 2014, 5, 26 },
            { 2014, 5, 26, 3, WEEKS, 2014, 5, 41 },
            { 2014, 5, 26, -5, WEEKS, 2014, 5, 1 },

            // Adding months (5 months per year) rolls over into adjacent years.
            { 2014, 5, 26, 0, MONTHS, 2014, 5, 26 },
            { 2014, 5, 26, 3, MONTHS, 2015, 3, 26 },
            { 2014, 5, 26, -5, MONTHS, 2013, 5, 26 },

            // Adding years.
            { 2014, 5, 26, 0, YEARS, 2014, 5, 26 },
            { 2014, 5, 26, 3, YEARS, 2017, 5, 26 },
            { 2014, 5, 26, -5, YEARS, 2009, 5, 26 },

            // Adding decades.
            { 2014, 5, 26, 0, DECADES, 2014, 5, 26 },
            { 2014, 5, 26, 3, DECADES, 2044, 5, 26 },
            { 2014, 5, 26, -5, DECADES, 1964, 5, 26 },

            // Adding centuries.
            { 2014, 5, 26, 0, CENTURIES, 2014, 5, 26 },
            { 2014, 5, 26, 3, CENTURIES, 2314, 5, 26 },
            { 2014, 5, 26, -5, CENTURIES, 1514, 5, 26 },

            // Adding millennia.
            { 2014, 5, 26, 0, MILLENNIA, 2014, 5, 26 },
            { 2014, 5, 26, 3, MILLENNIA, 5014, 5, 26 },
            { 2014, 5, 26, -1, MILLENNIA, 2014 - 1000, 5, 26 },
        };
    }

    @ParameterizedTest
    @MethodSource("data_plus")
    public void test_plus_TemporalUnit(int year, int month, int dom, long amount, TemporalUnit unit,
            int expectedYear, int expectedMonth, int expectedDom) {
        DiscordianDate start = DiscordianDate.of(year, month, dom);
        DiscordianDate expected = DiscordianDate.of(expectedYear, expectedMonth, expectedDom);

        assertEquals(expected, start.plus(amount, unit));
    }
}
