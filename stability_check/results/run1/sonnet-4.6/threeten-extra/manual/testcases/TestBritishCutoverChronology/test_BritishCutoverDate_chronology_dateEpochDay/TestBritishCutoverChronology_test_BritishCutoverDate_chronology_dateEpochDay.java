package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Tests that {@link BritishCutoverChronology#dateEpochDay(long)} correctly reconstructs
 * a {@link BritishCutoverDate} from the epoch-day of the corresponding ISO {@link LocalDate}.
 *
 * <p>The British calendar used the Julian system until 2 September 1752, then jumped
 * 11 days to 14 September 1752 (the Gregorian/ISO cutover). Each test pair supplies
 * a BritishCutoverDate and the ISO LocalDate that represents the same instant in time,
 * verifying the round-trip through the epoch-day value.
 */
public class TestBritishCutoverChronology_test_BritishCutoverDate_chronology_dateEpochDay {

    /**
     * Pairs of (BritishCutoverDate, equivalent ISO LocalDate) used to verify
     * {@link BritishCutoverChronology#dateEpochDay(long)}.
     *
     * <p>Rows are grouped by the calendar region they exercise:
     * <ol>
     *   <li>Very early Julian dates (AD 1, near the proleptic epoch)
     *   <li>Julian leap-year edge cases (years 4 and 100)
     *   <li>Dates that straddle the proleptic era boundary (year 0 / year 1)
     *   <li>Dates around the 1582 Gregorian reform that <em>was not</em> adopted by Britain
     *   <li>Dates just before and spanning the 1752 British cutover (Sep 2 → Sep 14)
     *   <li>Modern (post-cutover Gregorian) dates
     * </ol>
     */
    public static Object[][] data_samples() {
        return new Object[][] {
            // --- AD 1 (early Julian dates near the proleptic epoch) ---
            { BritishCutoverDate.of(1, 1, 1),  LocalDate.of(0, 12, 30) },
            { BritishCutoverDate.of(1, 1, 2),  LocalDate.of(0, 12, 31) },
            { BritishCutoverDate.of(1, 1, 3),  LocalDate.of(1, 1, 1)  },
            { BritishCutoverDate.of(1, 2, 28), LocalDate.of(1, 2, 26) },
            { BritishCutoverDate.of(1, 3, 1),  LocalDate.of(1, 2, 27) },
            { BritishCutoverDate.of(1, 3, 2),  LocalDate.of(1, 2, 28) },
            { BritishCutoverDate.of(1, 3, 3),  LocalDate.of(1, 3, 1)  },

            // --- Julian leap year: year 4 (divisible by 4, Julian rule applies) ---
            { BritishCutoverDate.of(4, 2, 28), LocalDate.of(4, 2, 26) },
            { BritishCutoverDate.of(4, 2, 29), LocalDate.of(4, 2, 27) },
            { BritishCutoverDate.of(4, 3, 1),  LocalDate.of(4, 2, 28) },
            { BritishCutoverDate.of(4, 3, 2),  LocalDate.of(4, 2, 29) },
            { BritishCutoverDate.of(4, 3, 3),  LocalDate.of(4, 3, 1)  },

            // --- Julian century year: year 100 (leap under Julian, not Gregorian) ---
            { BritishCutoverDate.of(100, 2, 28), LocalDate.of(100, 2, 26) },
            { BritishCutoverDate.of(100, 2, 29), LocalDate.of(100, 2, 27) },
            { BritishCutoverDate.of(100, 3, 1),  LocalDate.of(100, 2, 28) },
            { BritishCutoverDate.of(100, 3, 2),  LocalDate.of(100, 3, 1)  },
            { BritishCutoverDate.of(100, 3, 3),  LocalDate.of(100, 3, 2)  },

            // --- Era boundary: year 0 (BC 1 in proleptic numbering) ---
            { BritishCutoverDate.of(0, 12, 31), LocalDate.of(0, 12, 29) },
            { BritishCutoverDate.of(0, 12, 30), LocalDate.of(0, 12, 28) },

            // --- 1582 Gregorian reform dates (Britain did NOT adopt it; still Julian) ---
            { BritishCutoverDate.of(1582, 10, 4), LocalDate.of(1582, 10, 14) },
            { BritishCutoverDate.of(1582, 10, 5), LocalDate.of(1582, 10, 15) },

            // --- Dates approaching the 1752 British cutover ---
            { BritishCutoverDate.of(1751, 12, 20), LocalDate.of(1751, 12, 31) },
            { BritishCutoverDate.of(1751, 12, 31), LocalDate.of(1752, 1, 11) },
            { BritishCutoverDate.of(1752, 1, 1),   LocalDate.of(1752, 1, 12) },

            // --- The 1752 cutover month: Sep 2 (last Julian day) through Sep 14 (first Gregorian) ---
            // Sep 1 and Sep 2 are still Julian; Sep 14 is the first Gregorian date.
            { BritishCutoverDate.of(1752, 9, 1),  LocalDate.of(1752, 9, 12) },
            { BritishCutoverDate.of(1752, 9, 2),  LocalDate.of(1752, 9, 13) },
            // Days 3–13 are in the skipped gap; they are accepted leniently as Julian dates
            { BritishCutoverDate.of(1752, 9, 3),  LocalDate.of(1752, 9, 14) }, // leniently accept invalid
            { BritishCutoverDate.of(1752, 9, 13), LocalDate.of(1752, 9, 24) },
            { BritishCutoverDate.of(1752, 9, 14), LocalDate.of(1752, 9, 14) },

            // --- Modern (post-cutover, Gregorian) dates ---
            { BritishCutoverDate.of(1945, 11, 12), LocalDate.of(1945, 11, 12) },
            { BritishCutoverDate.of(2012, 7, 5),   LocalDate.of(2012, 7, 5)  },
            { BritishCutoverDate.of(2012, 7, 6),   LocalDate.of(2012, 7, 6)  },
        };
    }

    /**
     * Verifies that converting an ISO epoch-day back to a {@link BritishCutoverDate}
     * via {@link BritishCutoverChronology#dateEpochDay(long)} yields the original date.
     *
     * @param cutover the expected BritishCutoverDate
     * @param iso the equivalent ISO LocalDate (same instant in time)
     */
    @ParameterizedTest
    @MethodSource("data_samples")
    public void test_BritishCutoverDate_chronology_dateEpochDay(BritishCutoverDate cutover, LocalDate iso) {
        assertEquals(cutover, BritishCutoverChronology.INSTANCE.dateEpochDay(iso.toEpochDay()));
    }
}
