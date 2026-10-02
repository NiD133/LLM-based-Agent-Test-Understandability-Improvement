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

/**
 * Tests how {@link PaxDate#until(java.time.temporal.Temporal, TemporalUnit)}
 * measures the amount of time between two Pax-calendar dates in a given unit.
 */
@SuppressWarnings({ "static-method" })
public class TestPaxChronology_test_until_TemporalUnit {

    /**
     * Each row is: start (year, month, dayOfMonth), end (year, month, dayOfMonth),
     * the unit to measure in, and the expected whole-unit amount from start to end.
     */
    public static Object[][] data_until() {
        return new Object[][] {
            // DAYS: exact, forwards across a month boundary, and backwards.
            { 2014, 5, 26, 2014, 5, 26, DAYS, 0 },
            { 2014, 5, 26, 2014, 6, 4, DAYS, 6 },
            { 2014, 5, 26, 2014, 5, 20, DAYS, -6 },

            // WEEKS: amount is truncated until a full 7 days have elapsed.
            { 2014, 5, 26, 2014, 5, 26, WEEKS, 0 },
            { 2014, 5, 26, 2014, 6, 4, WEEKS, 0 },
            { 2014, 5, 26, 2014, 6, 5, WEEKS, 1 },

            // MONTHS: amount is truncated until the day-of-month is reached.
            { 2014, 5, 26, 2014, 5, 26, MONTHS, 0 },
            { 2014, 5, 26, 2014, 6, 25, MONTHS, 0 },
            { 2014, 5, 26, 2014, 6, 26, MONTHS, 1 },

            // YEARS: amount is truncated until the full anniversary is reached.
            { 2014, 5, 26, 2014, 5, 26, YEARS, 0 },
            { 2014, 5, 26, 2015, 5, 25, YEARS, 0 },
            { 2014, 5, 26, 2015, 5, 26, YEARS, 1 },

            // DECADES (10 years), truncated at the 10-year mark.
            { 2014, 5, 26, 2014, 5, 26, DECADES, 0 },
            { 2014, 5, 26, 2024, 5, 25, DECADES, 0 },
            { 2014, 5, 26, 2024, 5, 26, DECADES, 1 },

            // CENTURIES (100 years), truncated at the 100-year mark.
            { 2014, 5, 26, 2014, 5, 26, CENTURIES, 0 },
            { 2014, 5, 26, 2114, 5, 25, CENTURIES, 0 },
            { 2014, 5, 26, 2114, 5, 26, CENTURIES, 1 },

            // MILLENNIA (1000 years), truncated at the 1000-year mark.
            { 2014, 5, 26, 2014, 5, 26, MILLENNIA, 0 },
            { 2014, 5, 26, 3014, 5, 25, MILLENNIA, 0 },
            { 2014, 5, 26, 3014, 5, 26, MILLENNIA, 1 },

            // ERAS: 0 within the same era, 1 when crossing from BCE to CE.
            { -2013, 5, 26, 0, 5, 26, ERAS, 0 },
            { -2013, 5, 26, 2014, 5, 26, ERAS, 1 },

            // YEARS around the Pax leap year: 2012 is a leap year with a 14th month.
            { 2011, 13, 26, 2013, 13, 26, YEARS, 2 },
            { 2011, 13, 26, 2012, 14, 26, YEARS, 1 },
            { 2012, 14, 26, 2011, 13, 26, YEARS, -1 },
            { 2012, 14, 26, 2013, 13, 26, YEARS, 1 },
            { 2011, 13, 6, 2012, 13, 6, YEARS, 0 },
            { 2012, 13, 6, 2011, 13, 6, YEARS, 0 },
            { 2011, 13, 1, 2012, 13, 7, YEARS, 0 },
            { 2012, 13, 7, 2011, 13, 1, YEARS, 0 },
            { 2011, 12, 28, 2012, 13, 1, YEARS, 1 },
            { 2012, 13, 1, 2011, 12, 28, YEARS, -1 },
            { 2013, 13, 6, 2012, 13, 6, YEARS, -1 },
            { 2012, 13, 6, 2013, 13, 6, YEARS, 1 },
        };
    }

    @ParameterizedTest
    @MethodSource("data_until")
    public void test_until_TemporalUnit(int year1, int month1, int dom1,
            int year2, int month2, int dom2, TemporalUnit unit, long expected) {
        PaxDate start = PaxDate.of(year1, month1, dom1);
        PaxDate end = PaxDate.of(year2, month2, dom2);

        assertEquals(expected, start.until(end, unit));
    }
}
