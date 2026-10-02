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

public class TestDiscordianChronology_test_until_TemporalUnit {

    /**
     * Provides test data for {@link #test_until_TemporalUnit}.
     * Each row: year1, month1, dom1, year2, month2, dom2, unit, expected
     *
     * Scenarios are grouped by the TemporalUnit under test.
     */
    public static Object[][] data_until() {
        return new Object[][] {
            // --- DAYS ---
            // same date → 0
            { 2014, 5, 26,   2014, 5, 26,  DAYS,      0 },
            // forward / backward simple cases
            { 2014, 5, 26,   2014, 5, 32,  DAYS,      6 },
            { 2014, 5, 26,   2014, 5, 20,  DAYS,     -6 },
            // crossing St. Tib's Day (leap intercalary): dom 59→60 spans 2 real days
            { 2014, 1, 59,   2014, 1, 60,  DAYS,      2 },
            // from dom 59 to St. Tib's is 1 day
            { 2014, 1, 59,   2014, 0, 0,   DAYS,      1 },
            // from St. Tib's to dom 60 is 1 day
            { 2014, 0, 0,    2014, 1, 60,  DAYS,      1 },
            // backward across St. Tib's
            { 2014, 1, 60,   2014, 1, 55,  DAYS,     -6 },

            // --- WEEKS ---
            // same date → 0
            { 2014, 5, 26,   2014, 5, 26,  WEEKS,     0 },
            // less than a full week → 0
            { 2014, 5, 26,   2014, 5, 30,  WEEKS,     0 },
            // exactly one week → 1
            { 2014, 5, 26,   2014, 5, 31,  WEEKS,     1 },
            // St. Tib's Day week boundary cases
            { 2014, 0, 0,    2014, 0, 0,   WEEKS,     0 },
            { 2014, 1, 60,   2014, 1, 60,  WEEKS,     0 },
            { 2014, 1, 60,   2014, 1, 59,  WEEKS,     0 },
            { 2014, 1, 60,   2014, 1, 56,  WEEKS,     0 },
            { 2014, 1, 60,   2014, 1, 55,  WEEKS,    -1 },
            { 2014, 0, 0,    2014, 1, 54,  WEEKS,    -1 },
            { 2014, 0, 0,    2014, 1, 55,  WEEKS,     0 },
            { 2014, 0, 0,    2014, 1, 64,  WEEKS,     0 },
            { 2014, 0, 0,    2014, 1, 65,  WEEKS,     1 },
            { 2014, 1, 54,   2014, 0, 0,   WEEKS,     1 },
            { 2014, 1, 55,   2014, 0, 0,   WEEKS,     0 },
            { 2014, 1, 64,   2014, 0, 0,   WEEKS,     0 },
            { 2014, 1, 65,   2014, 0, 0,   WEEKS,    -1 },

            // --- MONTHS ---
            // same date → 0
            { 2014, 5, 26,   2014, 5, 26,  MONTHS,    0 },
            // one day before completing one month → 0
            { 2014, 5, 26,   2015, 1, 25,  MONTHS,    0 },
            // exactly one month → 1
            { 2014, 5, 26,   2015, 1, 26,  MONTHS,    1 },
            // St. Tib's Day month boundary cases
            { 2014, 0, 0,    2014, 0, 0,   MONTHS,    0 },
            { 2014, 0, 0,    2014, 2, 59,  MONTHS,    0 },
            { 2014, 0, 0,    2014, 2, 60,  MONTHS,    1 },
            { 2014, 2, 60,   2014, 0, 0,   MONTHS,   -1 },
            { 2014, 2, 59,   2014, 0, 0,   MONTHS,    0 },
            { 2013, 5, 59,   2014, 0, 0,   MONTHS,    1 },
            { 2013, 5, 60,   2014, 0, 0,   MONTHS,    0 },
            { 2013, 5, 60,   2014, 1, 60,  MONTHS,    1 },

            // --- YEARS ---
            // same date → 0
            { 2014, 5, 26,   2014, 5, 26,  YEARS,     0 },
            // one day before a full year → 0
            { 2014, 5, 26,   2015, 5, 25,  YEARS,     0 },
            // exactly one year → 1
            { 2014, 5, 26,   2015, 5, 26,  YEARS,     1 },
            // St. Tib's Day year boundary cases
            { 2014, 0, 0,    2014, 0, 0,   YEARS,     0 },
            { 2014, 0, 0,    2015, 1, 59,  YEARS,     0 },
            { 2014, 0, 0,    2015, 1, 60,  YEARS,     1 },
            { 2013, 1, 60,   2014, 0, 0,   YEARS,     0 },
            { 2013, 1, 59,   2014, 0, 0,   YEARS,     1 },
            { 2013, 1, 60,   2014, 1, 60,  YEARS,     1 },
            { 2014, 0, 0,    2013, 1, 59,  YEARS,    -1 },
            { 2014, 0, 0,    2013, 1, 60,  YEARS,     0 },
            { 2015, 1, 60,   2014, 0, 0,   YEARS,    -1 },
            { 2015, 1, 59,   2014, 0, 0,   YEARS,     0 },
            // leap-year-to-leap-year
            { 2018, 0, 0,    2014, 0, 0,   YEARS,    -4 },
            { 2014, 0, 0,    2018, 0, 0,   YEARS,     4 },

            // --- DECADES ---
            { 2014, 5, 26,   2014, 5, 26,  DECADES,   0 },
            { 2014, 5, 26,   2024, 5, 25,  DECADES,   0 },
            { 2014, 5, 26,   2024, 5, 26,  DECADES,   1 },

            // --- CENTURIES ---
            { 2014, 5, 26,   2014, 5, 26,  CENTURIES, 0 },
            { 2014, 5, 26,   2114, 5, 25,  CENTURIES, 0 },
            { 2014, 5, 26,   2114, 5, 26,  CENTURIES, 1 },

            // --- MILLENNIA ---
            { 2014, 5, 26,   2014, 5, 26,  MILLENNIA, 0 },
            { 2014, 5, 26,   3014, 5, 25,  MILLENNIA, 0 },
            { 2014, 5, 26,   3014, 5, 26,  MILLENNIA, 1 },

            // --- ERAS (always 0 within the single YOLD era) ---
            { 2013, 5, 26,   3014, 5, 26,  ERAS,      0 },
        };
    }

    @ParameterizedTest
    @MethodSource("data_until")
    public void test_until_TemporalUnit(
            int year1, int month1, int dom1,
            int year2, int month2, int dom2,
            TemporalUnit unit, long expected) {
        DiscordianDate start = DiscordianDate.of(year1, month1, dom1);
        DiscordianDate end   = DiscordianDate.of(year2, month2, dom2);
        assertEquals(expected, start.until(end, unit));
    }
}
