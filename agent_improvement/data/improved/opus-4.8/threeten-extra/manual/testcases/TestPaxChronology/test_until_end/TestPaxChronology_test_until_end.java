package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.chrono.ChronoPeriod;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Tests that {@link PaxDate#until(java.time.chrono.ChronoLocalDate)} returns the
 * Pax-calendar period (years, months, days) separating two Pax dates.
 */
@SuppressWarnings({ "static-method" })
public class TestPaxChronology_test_until_end {

    /**
     * Each case describes the period {@code start.until(end)} is expected to produce.
     * Columns: start (year, month, day), end (year, month, day),
     * then the expected period as (years, months, days).
     */
    public static Object[][] data_until_period() {
        return new Object[][] {
            // Same date -> zero period.
            { 2014, 5, 26, 2014, 5, 26, /* expect */ 0, 0, 0 },

            // Differences of only a few days, forwards and backwards.
            { 2014, 5, 26, 2014, 6, 4, /* expect */ 0, 0, 6 },
            { 2014, 5, 26, 2014, 5, 20, /* expect */ 0, 0, -6 },
            { 2014, 5, 26, 2014, 6, 5, /* expect */ 0, 0, 7 },
            { 2014, 5, 26, 2014, 6, 25, /* expect */ 0, 0, 27 },

            // Crossing one or more month boundaries.
            { 2014, 5, 26, 2014, 6, 26, /* expect */ 0, 1, 0 },
            { 2014, 5, 26, 2015, 5, 25, /* expect */ 0, 12, 27 },

            // Whole-year and multi-year spans.
            { 2014, 5, 26, 2015, 5, 26, /* expect */ 1, 0, 0 },
            { 2014, 5, 26, 2024, 5, 25, /* expect */ 9, 12, 27 },

            // Spans involving the leap month (month 13) and 14-month leap years.
            { 2011, 13, 26, 2013, 13, 26, /* expect */ 2, 0, 0 },
            { 2011, 13, 26, 2012, 14, 26, /* expect */ 1, 0, 0 },
            { 2012, 14, 26, 2011, 13, 26, /* expect */ -1, 0, 0 },
            { 2012, 14, 26, 2013, 13, 26, /* expect */ 1, 0, 0 },
            { 2011, 13, 6, 2012, 13, 6, /* expect */ 0, 13, 0 },
            { 2012, 13, 6, 2011, 13, 6, /* expect */ 0, -13, 0 },
            { 2011, 13, 1, 2012, 13, 7, /* expect */ 0, 13, 6 },
            { 2012, 13, 7, 2011, 13, 1, /* expect */ 0, -13, -6 },
            { 2011, 12, 28, 2012, 13, 1, /* expect */ 1, 0, 1 },
            { 2012, 13, 1, 2011, 12, 28, /* expect */ -1, 0, -1 },
            { 2013, 13, 6, 2012, 13, 6, /* expect */ -1, -1, 0 },
            { 2012, 13, 6, 2013, 13, 6, /* expect */ 1, 0, 0 },
        };
    }

    @ParameterizedTest(name = "{0}-{1}-{2} until {3}-{4}-{5} = P{6}Y{7}M{8}D")
    @MethodSource("data_until_period")
    public void test_until_end(int startYear, int startMonth, int startDay,
            int endYear, int endMonth, int endDay,
            int expectedYears, int expectedMonths, int expectedDays) {
        PaxDate start = PaxDate.of(startYear, startMonth, startDay);
        PaxDate end = PaxDate.of(endYear, endMonth, endDay);

        ChronoPeriod expectedPeriod =
                PaxChronology.INSTANCE.period(expectedYears, expectedMonths, expectedDays);

        assertEquals(expectedPeriod, start.until(end));
    }
}
