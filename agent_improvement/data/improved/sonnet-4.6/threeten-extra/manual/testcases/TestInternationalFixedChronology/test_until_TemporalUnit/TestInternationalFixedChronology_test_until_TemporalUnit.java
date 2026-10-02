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

@SuppressWarnings({ "static-method" })
public class TestInternationalFixedChronology_test_until_TemporalUnit {

    /**
     * Test data for {@link #test_until_TemporalUnit}.
     * Each row: year1, month1, dom1, year2, month2, dom2, unit, expectedAmount
     *
     * <p>In the IFC calendar, month 13 day 29 is "Year Day" and month 6 day 29 (leap years only)
     * is "Leap Day". The ordering within each year is: ..., month 6 day 28, Leap Day,
     * month 7 day 1, ..., month 13 day 28, Year Day, month 1 day 1 of the next year.
     */
    public static Object[][] data_until() {
        return new Object[][] {
            // --- basic cases: regular date (2014-05-26) as start ---
            { 2014, 5, 26, 2014, 5, 26, DAYS,      0 },
            { 2014, 5, 26, 2014, 6,  4, DAYS,      6 },
            { 2014, 5, 26, 2014, 5, 20, DAYS,     -6 },

            { 2014, 5, 26, 2014, 5, 26, WEEKS,     0 },
            { 2014, 5, 26, 2014, 6,  4, WEEKS,     0 },
            { 2014, 5, 26, 2014, 6,  5, WEEKS,     1 },

            { 2014, 5, 26, 2014, 5, 26, MONTHS,    0 },
            { 2014, 5, 26, 2014, 6, 25, MONTHS,    0 },
            { 2014, 5, 26, 2014, 6, 26, MONTHS,    1 },

            { 2014, 5, 26, 2014, 5, 26, YEARS,     0 },
            { 2014, 5, 26, 2015, 5, 25, YEARS,     0 },
            { 2014, 5, 26, 2015, 5, 26, YEARS,     1 },

            { 2014, 5, 26, 2014, 5, 26, DECADES,   0 },
            { 2014, 5, 26, 2024, 5, 25, DECADES,   0 },
            { 2014, 5, 26, 2024, 5, 26, DECADES,   1 },

            { 2014, 5, 26, 2014, 5, 26, CENTURIES, 0 },
            { 2014, 5, 26, 2114, 5, 25, CENTURIES, 0 },
            { 2014, 5, 26, 2114, 5, 26, CENTURIES, 1 },

            { 2014, 5, 26, 2014, 5, 26, MILLENNIA, 0 },
            { 2014, 5, 26, 3014, 5, 25, MILLENNIA, 0 },
            { 2014, 5, 26, 3014, 5, 26, MILLENNIA, 1 },

            { 2014, 5, 26, 3014, 5, 26, ERAS,      0 },

            // --- Year Day boundary (month 13 day 29 = Year Day; non-leap year) ---
            // days crossing Year Day
            { 2014, 13, 28, 2015,  1,  1, DAYS,  2 },
            { 2014, 13, 28, 2014, 13, 29, DAYS,  1 },
            { 2014, 13, 29, 2015,  1,  1, DAYS,  1 },
            { 2015,  1,  1, 2014, 13, 24, DAYS, -6 },

            // weeks crossing Year Day
            { 2014, 13, 29, 2014, 13, 29, WEEKS,  0 },
            { 2015,  1,  1, 2015,  1,  1, WEEKS,  0 },
            { 2015,  1,  1, 2014, 13, 28, WEEKS,  0 },
            { 2015,  1,  1, 2014, 13, 23, WEEKS,  0 },
            { 2015,  1,  1, 2014, 13, 22, WEEKS, -1 },
            { 2014, 13, 29, 2014, 13, 21, WEEKS, -1 },
            { 2014, 13, 29, 2014, 13, 22, WEEKS,  0 },
            { 2014, 13, 29, 2015,  1,  7, WEEKS,  0 },
            { 2014, 13, 29, 2015,  1,  8, WEEKS,  1 },
            { 2014, 13, 21, 2014, 13, 29, WEEKS,  1 },
            { 2014, 13, 22, 2014, 13, 29, WEEKS,  0 },
            { 2015,  1,  7, 2014, 13, 29, WEEKS,  0 },
            { 2015,  1,  8, 2014, 13, 29, WEEKS, -1 },
            { 2014, 13,  1, 2015,  1,  1, WEEKS,  4 },

            // months crossing Year Day
            { 2014, 13, 29, 2014, 13, 29, MONTHS,  0 },
            { 2014, 13, 29, 2015,  1, 28, MONTHS,  0 },
            { 2014, 13, 29, 2015,  2,  1, MONTHS,  1 },
            { 2015,  2,  1, 2014, 13, 29, MONTHS, -1 },
            { 2015,  1, 28, 2014, 13, 29, MONTHS,  0 },
            { 2014, 12, 28, 2014, 13, 29, MONTHS,  1 },
            { 2014, 13,  1, 2014, 13, 29, MONTHS,  0 },
            { 2014, 13,  1, 2015,  1,  1, MONTHS,  1 },

            // years crossing Year Day
            { 2014, 13, 29, 2014, 13, 29, YEARS,  0 },
            { 2014, 13, 29, 2015, 13, 28, YEARS,  0 },
            { 2014, 13, 29, 2015, 13, 29, YEARS,  1 },
            { 2014, 13, 29, 2016,  1,  1, YEARS,  1 },
            { 2014,  1,  1, 2014, 13, 29, YEARS,  0 },
            { 2013, 13, 29, 2014, 13, 29, YEARS,  1 },
            { 2013, 13, 28, 2014, 13, 29, YEARS,  1 },

            // --- Leap Day boundary (month 6 day 29 = Leap Day; leap years only) ---
            // days crossing Leap Day
            { 2012, 6, 28, 2012, 7,  1, DAYS,  2 },
            { 2012, 6, 28, 2012, 6, 29, DAYS,  1 },
            { 2012, 6, 29, 2012, 7,  1, DAYS,  1 },
            { 2012, 7,  1, 2012, 6, 24, DAYS, -6 },

            // weeks crossing Leap Day
            { 2012, 6, 29, 2012, 6, 29, WEEKS,  0 },
            { 2012, 7,  1, 2012, 7,  1, WEEKS,  0 },
            { 2012, 7,  1, 2012, 6, 28, WEEKS,  0 },
            { 2012, 7,  1, 2012, 6, 23, WEEKS,  0 },
            { 2012, 7,  1, 2012, 6, 22, WEEKS, -1 },
            { 2012, 6, 29, 2012, 6, 21, WEEKS, -1 },
            { 2012, 6, 29, 2012, 6, 22, WEEKS,  0 },
            { 2012, 6, 29, 2012, 7,  7, WEEKS,  0 },
            { 2012, 6, 29, 2012, 7,  8, WEEKS,  1 },
            { 2012, 6, 21, 2012, 6, 29, WEEKS,  1 },
            { 2012, 6, 22, 2012, 6, 29, WEEKS,  0 },
            { 2012, 7,  7, 2012, 6, 29, WEEKS,  0 },
            { 2012, 7,  8, 2012, 6, 29, WEEKS, -1 },

            // months crossing Leap Day
            { 2012, 6, 29, 2012, 6, 29, MONTHS,  0 },
            { 2012, 6, 29, 2012, 7, 28, MONTHS,  0 },
            { 2012, 6, 29, 2012, 8,  1, MONTHS,  1 },
            { 2012, 8,  1, 2012, 6, 29, MONTHS, -1 },
            { 2012, 7, 28, 2012, 6, 29, MONTHS,  0 },
            { 2012, 5, 28, 2012, 6, 29, MONTHS,  1 },
            { 2012, 6,  1, 2012, 6, 29, MONTHS,  0 },
            { 2012, 6,  1, 2012, 7,  1, MONTHS,  1 },

            // years crossing Leap Day
            { 2012, 6, 29, 2012, 6, 29, YEARS,   0 },
            { 2012, 6, 29, 2013, 6, 28, YEARS,   0 },
            { 2012, 6, 29, 2013, 7,  1, YEARS,   1 },
            { 2011, 7,  1, 2012, 6, 29, YEARS,   0 },
            { 2011, 6, 28, 2012, 6, 29, YEARS,   1 },
            { 2011, 7,  1, 2012, 7,  1, YEARS,   1 },
            { 2012, 6, 29, 2011, 6, 28, YEARS,  -1 },
            { 2012, 6, 29, 2011, 7,  1, YEARS,   0 },
            { 2013, 7,  1, 2012, 6, 29, YEARS,  -1 },
            { 2013, 6, 28, 2012, 6, 29, YEARS,   0 },
            { 2016, 6, 29, 2012, 6, 29, YEARS,  -4 },
            { 2012, 6, 29, 2016, 6, 29, YEARS,   4 },

            // --- Interactions between Leap Day and Year Day ---
            // The ordering within a year is: ..., month 6 day 28, Leap Day, month 7 day 1,
            //   ..., month 13 day 28, Year Day, month 1 day 1 of the next year.
            // Year Day is "after the 28th"; Leap Day is "before the 1st".
            { 2012,  6, 29, 2012, 13, 29, DAYS,   197 },

            { 2012,  6, 29, 2012, 13, 28, WEEKS,   27 },
            { 2012,  6, 29, 2012, 13, 29, WEEKS,   28 },
            { 2012,  6, 29, 2013,  1,  1, WEEKS,   28 },
            { 2012,  6, 29, 2011, 13, 28, WEEKS,  -24 },
            { 2012,  6, 29, 2011, 13, 29, WEEKS,  -24 },
            { 2012,  6, 29, 2012,  1,  1, WEEKS,  -23 },
            { 2012, 13, 29, 2012,  6, 28, WEEKS,  -28 },
            { 2012, 13, 29, 2012,  6, 29, WEEKS,  -28 },
            { 2012, 13, 29, 2012,  7,  1, WEEKS,  -27 },
            { 2011, 13, 29, 2012,  6, 28, WEEKS,   23 },
            { 2011, 13, 29, 2012,  6, 29, WEEKS,   24 },
            { 2011, 13, 29, 2012,  7,  1, WEEKS,   24 },
            { 2012, 13, 29, 2013, 13, 29, WEEKS,   52 },
            { 2012, 13, 29, 2016, 13, 29, WEEKS,   52 * 4 },
            { 2012,  6, 29, 2016,  6, 29, WEEKS,   52 * 4 },

            { 2012,  6, 29, 2012, 13, 28, MONTHS,   6 },
            { 2012,  6, 29, 2012, 13, 29, MONTHS,   7 },
            { 2012,  6, 29, 2013,  1,  1, MONTHS,   7 },
            { 2012,  6, 29, 2011, 13, 28, MONTHS,  -6 },
            { 2012,  6, 29, 2011, 13, 29, MONTHS,  -6 },
            { 2012,  6, 29, 2012,  1,  1, MONTHS,  -5 },
            { 2012, 13, 29, 2012,  6, 28, MONTHS,  -7 },
            { 2012, 13, 29, 2012,  6, 29, MONTHS,  -7 },
            { 2012, 13, 29, 2012,  7,  1, MONTHS,  -6 },
            { 2011, 13, 29, 2012,  6, 28, MONTHS,   5 },
            { 2011, 13, 29, 2012,  6, 29, MONTHS,   6 },
            { 2011, 13, 29, 2012,  7,  1, MONTHS,   6 },
        };
    }

    @ParameterizedTest
    @MethodSource("data_until")
    public void test_until_TemporalUnit(
            int year1, int month1, int dom1,
            int year2, int month2, int dom2,
            TemporalUnit unit, long expected) {
        InternationalFixedDate start = InternationalFixedDate.of(year1, month1, dom1);
        InternationalFixedDate end   = InternationalFixedDate.of(year2, month2, dom2);
        assertEquals(expected, start.until(end, unit));
    }
}
