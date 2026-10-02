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
 * Tests BritishCutoverDate.plus(long, TemporalUnit).
 *
 * <p>The British calendar skips 11 days at the Julian-to-Gregorian cutover:
 * Wednesday 2 September 1752 is immediately followed by Thursday 14 September 1752.
 * Adding 1 DAY to 1752-09-02 therefore yields 1752-09-14, not 1752-09-03.
 */
public class TestBritishCutoverChronology_test_plus_TemporalUnit {

    /**
     * Test data for plus(long, TemporalUnit).
     *
     * <p>Columns: year, month, day, amount, unit,
     *             expectedYear, expectedMonth, expectedDay, bidi (true = result + (-amount) gives back the start).
     */
    public static Object[][] data_plus() {
        return new Object[][] {

            // --- DAYS: arithmetic around the cutover gap (1752-09-02 is the last Julian day) ---
            // Adding 1 day jumps 11 days because days 3-13 Sep were deleted.
            { 1752, 9,  2,  -1, DAYS, 1752,  9,  1, true  },
            { 1752, 9,  2,   0, DAYS, 1752,  9,  2, true  },
            { 1752, 9,  2,   1, DAYS, 1752,  9, 14, true  },
            { 1752, 9,  2,   2, DAYS, 1752,  9, 15, true  },
            // Symmetric: subtracting 1 day from 1752-09-14 (first Gregorian day) gives back 1752-09-02.
            { 1752, 9, 14,  -1, DAYS, 1752,  9,  2, true  },
            { 1752, 9, 14,   0, DAYS, 1752,  9, 14, true  },
            { 1752, 9, 14,   1, DAYS, 1752,  9, 15, true  },
            // Regular date (no cutover involved)
            { 2014, 5, 26,   0, DAYS, 2014,  5, 26, true  },
            { 2014, 5, 26,   8, DAYS, 2014,  6,  3, true  },
            { 2014, 5, 26,  -3, DAYS, 2014,  5, 23, true  },

            // --- WEEKS: each week = 7 days, still subject to the 11-day gap ---
            { 1752, 9,  2,  -1, WEEKS, 1752,  8, 26, true  },
            { 1752, 9,  2,   0, WEEKS, 1752,  9,  2, true  },
            { 1752, 9,  2,   1, WEEKS, 1752,  9, 20, true  },
            { 1752, 9, 14,  -1, WEEKS, 1752,  8, 27, true  },
            { 1752, 9, 14,   0, WEEKS, 1752,  9, 14, true  },
            { 1752, 9, 14,   1, WEEKS, 1752,  9, 21, true  },
            { 2014, 5, 26,   0, WEEKS, 2014,  5, 26, true  },
            { 2014, 5, 26,   3, WEEKS, 2014,  6, 16, true  },
            { 2014, 5, 26,  -5, WEEKS, 2014,  4, 21, true  },

            // --- MONTHS: month arithmetic around the cutover ---
            // Dates that land cleanly (same day-of-month exists in target month)
            { 1752, 9,  2,  -1, MONTHS, 1752,  8,  2, true  },
            { 1752, 9,  2,   0, MONTHS, 1752,  9,  2, true  },
            { 1752, 9,  2,   1, MONTHS, 1752, 10,  2, true  },
            { 1752, 9, 14,  -1, MONTHS, 1752,  8, 14, true  },
            { 1752, 9, 14,   0, MONTHS, 1752,  9, 14, true  },
            { 1752, 9, 14,   1, MONTHS, 1752, 10, 14, true  },
            // Dates where adding a month lands inside the cutover gap (leniently adjusted to next valid day)
            { 1752,  8, 12,   1, MONTHS, 1752,  9, 23, false },
            { 1752, 10, 12,  -1, MONTHS, 1752,  9, 23, false },
            // Regular dates
            { 2014, 5, 26,   0, MONTHS, 2014,  5, 26, true  },
            { 2014, 5, 26,   3, MONTHS, 2014,  8, 26, true  },
            { 2014, 5, 26,  -5, MONTHS, 2013, 12, 26, true  },

            // --- YEARS ---
            { 2014, 5, 26,   0, YEARS, 2014, 5, 26, true  },
            { 2014, 5, 26,   3, YEARS, 2017, 5, 26, true  },
            { 2014, 5, 26,  -5, YEARS, 2009, 5, 26, true  },

            // --- DECADES ---
            { 2014, 5, 26,   0, DECADES, 2014, 5, 26, true  },
            { 2014, 5, 26,   3, DECADES, 2044, 5, 26, true  },
            { 2014, 5, 26,  -5, DECADES, 1964, 5, 26, true  },

            // --- CENTURIES ---
            { 2014, 5, 26,   0, CENTURIES, 2014, 5, 26, true  },
            { 2014, 5, 26,   3, CENTURIES, 2314, 5, 26, true  },
            { 2014, 5, 26,  -5, CENTURIES, 1514, 5, 26, true  },

            // --- MILLENNIA ---
            { 2014, 5, 26,   0, MILLENNIA, 2014,       5, 26, true  },
            { 2014, 5, 26,   3, MILLENNIA, 5014,       5, 26, true  },
            { 2014, 5, 26,  -5, MILLENNIA, 2014 - 5000, 5, 26, true  },

            // --- ERAS: flips from AD to BC (negates the proleptic year) ---
            { 2014, 5, 26,  -1, ERAS, -2013, 5, 26, true  },
        };
    }

    @ParameterizedTest
    @MethodSource("data_plus")
    public void test_plus_TemporalUnit(
            int year, int month, int dom,
            long amount, TemporalUnit unit,
            int expectedYear, int expectedMonth, int expectedDom,
            boolean bidi) {
        assertEquals(
                BritishCutoverDate.of(expectedYear, expectedMonth, expectedDom),
                BritishCutoverDate.of(year, month, dom).plus(amount, unit));
    }
}
