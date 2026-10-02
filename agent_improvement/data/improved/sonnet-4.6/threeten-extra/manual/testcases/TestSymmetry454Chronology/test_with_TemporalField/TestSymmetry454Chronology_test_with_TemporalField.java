package org.threeten.extra.chrono;

import static java.time.temporal.ChronoField.ALIGNED_DAY_OF_WEEK_IN_MONTH;
import static java.time.temporal.ChronoField.ALIGNED_DAY_OF_WEEK_IN_YEAR;
import static java.time.temporal.ChronoField.ALIGNED_WEEK_OF_MONTH;
import static java.time.temporal.ChronoField.ALIGNED_WEEK_OF_YEAR;
import static java.time.temporal.ChronoField.DAY_OF_MONTH;
import static java.time.temporal.ChronoField.DAY_OF_WEEK;
import static java.time.temporal.ChronoField.DAY_OF_YEAR;
import static java.time.temporal.ChronoField.ERA;
import static java.time.temporal.ChronoField.MONTH_OF_YEAR;
import static java.time.temporal.ChronoField.PROLEPTIC_MONTH;
import static java.time.temporal.ChronoField.YEAR;
import static java.time.temporal.ChronoField.YEAR_OF_ERA;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.temporal.TemporalField;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

@SuppressWarnings({ "static-method" })
public class TestSymmetry454Chronology_test_with_TemporalField {

    // Each row: { year, month, dom, field, value, expectedYear, expectedMonth, expectedDom }
    // Tests that Symmetry454Date.with(field, value) produces the expected adjusted date.
    public static Object[][] data_with() {
        return new Object[][] {
            // --- DAY_OF_WEEK ---
            { 2014, 5, 26, DAY_OF_WEEK, 1, 2014, 5, 22 },   // adjust to Monday of same week
            { 2014, 5, 26, DAY_OF_WEEK, 5, 2014, 5, 26 },   // same day (Friday stays)

            // --- DAY_OF_MONTH ---
            { 2014, 5, 26, DAY_OF_MONTH, 28, 2014, 5, 28 }, // advance within same month
            { 2014, 5, 26, DAY_OF_MONTH, 26, 2014, 5, 26 }, // same day (no change)

            // --- DAY_OF_YEAR ---
            { 2014, 5, 26, DAY_OF_YEAR, 364, 2014, 12, 28 }, // last day of normal year
            { 2014, 5, 26, DAY_OF_YEAR, 138, 2014, 5, 19 },  // earlier in year

            // --- ALIGNED_DAY_OF_WEEK_IN_MONTH ---
            { 2014, 5, 26, ALIGNED_DAY_OF_WEEK_IN_MONTH, 3, 2014, 5, 24 }, // shift within aligned week
            { 2014, 5, 26, ALIGNED_DAY_OF_WEEK_IN_MONTH, 5, 2014, 5, 26 }, // same position (no change)

            // --- ALIGNED_WEEK_OF_MONTH ---
            { 2014, 5, 26, ALIGNED_WEEK_OF_MONTH, 1, 2014, 5, 5 },  // first week of month
            { 2014, 5, 26, ALIGNED_WEEK_OF_MONTH, 4, 2014, 5, 26 }, // same week (no change)

            // --- ALIGNED_DAY_OF_WEEK_IN_YEAR ---
            { 2014, 5, 26, ALIGNED_DAY_OF_WEEK_IN_YEAR, 2, 2014, 5, 23 }, // shift within aligned week
            { 2014, 5, 26, ALIGNED_DAY_OF_WEEK_IN_YEAR, 5, 2014, 5, 26 }, // same position (no change)

            // --- ALIGNED_WEEK_OF_YEAR ---
            { 2014, 5, 26, ALIGNED_WEEK_OF_YEAR, 23, 2014, 6, 19 }, // advance to week 23
            { 2014, 5, 26, ALIGNED_WEEK_OF_YEAR, 20, 2014, 5, 26 }, // same week (no change)

            // --- MONTH_OF_YEAR ---
            { 2014, 5, 26, MONTH_OF_YEAR, 4, 2014, 4, 26 }, // move to previous month
            { 2014, 5, 26, MONTH_OF_YEAR, 5, 2014, 5, 26 }, // same month (no change)

            // --- PROLEPTIC_MONTH ---
            { 2014, 5, 26, PROLEPTIC_MONTH, 2013 * 12 + 3 - 1, 2013, 3, 26 }, // move to March 2013
            { 2014, 5, 26, PROLEPTIC_MONTH, 2014 * 12 + 5 - 1, 2014, 5, 26 }, // same month (no change)

            // --- YEAR ---
            { 2014, 5, 26, YEAR, 2012, 2012, 5, 26 }, // move to earlier year
            { 2014, 5, 26, YEAR, 2014, 2014, 5, 26 }, // same year (no change)

            // --- YEAR_OF_ERA ---
            { 2014, 5, 26, YEAR_OF_ERA, 2012, 2012, 5, 26 }, // move to earlier year
            { 2014, 5, 26, YEAR_OF_ERA, 2014, 2014, 5, 26 }, // same year (no change)

            // --- ERA ---
            { 2014, 5, 26, ERA, 1, 2014, 5, 26 }, // set to CE (no change)

            // --- ALIGNED_DAY_OF_WEEK_IN_MONTH edge cases (leap-year Dec with 35 days) ---
            { 2015, 12, 22, ALIGNED_DAY_OF_WEEK_IN_MONTH, 1, 2015, 12, 22 },
            { 2015, 12, 22, ALIGNED_DAY_OF_WEEK_IN_MONTH, 2, 2015, 12, 23 },
            { 2015, 12, 22, ALIGNED_DAY_OF_WEEK_IN_MONTH, 3, 2015, 12, 24 },
            { 2015, 12, 22, ALIGNED_DAY_OF_WEEK_IN_MONTH, 4, 2015, 12, 25 },
            { 2015, 12, 22, ALIGNED_DAY_OF_WEEK_IN_MONTH, 5, 2015, 12, 26 },
            { 2015, 12, 22, ALIGNED_DAY_OF_WEEK_IN_MONTH, 6, 2015, 12, 27 },
            { 2015, 12, 22, ALIGNED_DAY_OF_WEEK_IN_MONTH, 7, 2015, 12, 28 },

            // --- ALIGNED_DAY_OF_WEEK_IN_YEAR edge cases ---
            { 2015, 12, 22, ALIGNED_DAY_OF_WEEK_IN_YEAR, 1, 2015, 12, 22 },
            { 2015, 12, 22, ALIGNED_DAY_OF_WEEK_IN_YEAR, 2, 2015, 12, 23 },
            { 2015, 12, 22, ALIGNED_DAY_OF_WEEK_IN_YEAR, 3, 2015, 12, 24 },
            { 2015, 12, 22, ALIGNED_DAY_OF_WEEK_IN_YEAR, 4, 2015, 12, 25 },
            { 2015, 12, 22, ALIGNED_DAY_OF_WEEK_IN_YEAR, 5, 2015, 12, 26 },
            { 2015, 12, 22, ALIGNED_DAY_OF_WEEK_IN_YEAR, 6, 2015, 12, 27 },
            { 2015, 12, 22, ALIGNED_DAY_OF_WEEK_IN_YEAR, 7, 2015, 12, 28 },

            // --- ALIGNED_WEEK_OF_MONTH / ALIGNED_WEEK_OF_YEAR: day 29 is the leap-week start ---
            { 2015, 12, 29, ALIGNED_WEEK_OF_MONTH, 0, 2015, 12, 29 },  // week 0 keeps leap day
            { 2015, 12, 29, ALIGNED_WEEK_OF_MONTH, 3, 2015, 12, 15 },  // move to week 3
            { 2015, 12, 29, ALIGNED_WEEK_OF_YEAR, 0, 2015, 12, 29 },   // week 0 keeps leap day
            { 2015, 12, 29, ALIGNED_WEEK_OF_YEAR, 3, 2015, 1, 15 },    // move to week 3 of year

            // --- DAY_OF_WEEK: day 29 (outside normal week) and full week sweep ---
            { 2015, 12, 29, DAY_OF_WEEK, 0, 2015, 12, 29 },   // day 0 keeps leap day
            { 2015, 12, 28, DAY_OF_WEEK, 1, 2015, 12, 22 },
            { 2015, 12, 28, DAY_OF_WEEK, 2, 2015, 12, 23 },
            { 2015, 12, 28, DAY_OF_WEEK, 3, 2015, 12, 24 },
            { 2015, 12, 28, DAY_OF_WEEK, 4, 2015, 12, 25 },
            { 2015, 12, 28, DAY_OF_WEEK, 5, 2015, 12, 26 },
            { 2015, 12, 28, DAY_OF_WEEK, 6, 2015, 12, 27 },
            { 2015, 12, 28, DAY_OF_WEEK, 7, 2015, 12, 28 },

            // --- DAY_OF_MONTH: leap-year Dec day 29 ---
            { 2015, 12, 29, DAY_OF_MONTH, 1, 2015, 12, 1 },
            { 2015, 12, 29, DAY_OF_MONTH, 3, 2015, 12, 3 },

            // --- MONTH_OF_YEAR: clamping when target month is shorter ---
            { 2015, 12, 29, MONTH_OF_YEAR, 1, 2015, 1, 28 },   // Dec 29 → Jan, clamped to Jan 28
            { 2015, 12, 29, MONTH_OF_YEAR, 12, 2015, 12, 29 }, // same month (no change)
            { 2015, 12, 29, MONTH_OF_YEAR, 2, 2015, 2, 29 },   // to long-month Feb (35 days, day 29 valid)

            // --- YEAR: clamping when target year lacks leap week ---
            { 2015, 12, 29, YEAR, 2014, 2014, 12, 28 }, // leap week removed → clamp to day 28
            { 2015, 12, 29, YEAR, 2013, 2013, 12, 28 }, // same clamping

            // --- DAY_OF_MONTH: standard month boundaries ---
            { 2014, 3, 28, DAY_OF_MONTH, 1, 2014, 3, 1 },
            { 2014, 1, 28, DAY_OF_MONTH, 1, 2014, 1, 1 },

            // --- MONTH_OF_YEAR: move 28-day March to 28-day January (same length) ---
            { 2014, 3, 28, MONTH_OF_YEAR, 1, 2014, 1, 28 },

            // --- DAY_OF_YEAR: last day of leap year and normal year ---
            { 2015, 3, 28, DAY_OF_YEAR, 371, 2015, 12, 35 }, // last day of leap year
            { 2012, 3, 28, DAY_OF_YEAR, 364, 2012, 12, 28 }, // last day of normal year
        };
    }

    @ParameterizedTest
    @MethodSource("data_with")
    public void test_with_TemporalField(
            int year, int month, int dom,
            TemporalField field, long value,
            int expectedYear, int expectedMonth, int expectedDom) {
        assertEquals(
                Symmetry454Date.of(expectedYear, expectedMonth, expectedDom),
                Symmetry454Date.of(year, month, dom).with(field, value));
    }
}
