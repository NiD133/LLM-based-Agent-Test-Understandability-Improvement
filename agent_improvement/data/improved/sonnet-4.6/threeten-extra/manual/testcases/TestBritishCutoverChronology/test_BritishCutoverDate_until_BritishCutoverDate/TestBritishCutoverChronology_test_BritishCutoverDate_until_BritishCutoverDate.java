package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Tests that {@code BritishCutoverDate.until(BritishCutoverDate)} returns a zero period
 * when the start and end date are the same, across a representative set of dates
 * spanning both the Julian and Gregorian portions of the British cutover calendar.
 */
public class TestBritishCutoverChronology_test_BritishCutoverDate_until_BritishCutoverDate {

    /**
     * Pairs of (BritishCutoverDate, equivalent ISO LocalDate) covering:
     * - Early Julian dates (year 1)
     * - Dates around Julian leap years (year 4, 100)
     * - Dates spanning the 1582 Gregorian introduction
     * - Dates in the 1751/1752 year-start change
     * - Dates straddling the September 1752 cutover gap (including leniently-accepted invalid dates)
     * - Post-cutover Gregorian dates
     */
    public static Object[][] data_samples() {
        return new Object[][] {
            { BritishCutoverDate.of(1, 1, 1),    LocalDate.of(0,    12, 30) },
            { BritishCutoverDate.of(1, 1, 2),    LocalDate.of(0,    12, 31) },
            { BritishCutoverDate.of(1, 1, 3),    LocalDate.of(1,     1,  1) },
            { BritishCutoverDate.of(1, 2, 28),   LocalDate.of(1,     2, 26) },
            { BritishCutoverDate.of(1, 3,  1),   LocalDate.of(1,     2, 27) },
            { BritishCutoverDate.of(1, 3,  2),   LocalDate.of(1,     2, 28) },
            { BritishCutoverDate.of(1, 3,  3),   LocalDate.of(1,     3,  1) },
            { BritishCutoverDate.of(4, 2, 28),   LocalDate.of(4,     2, 26) },
            { BritishCutoverDate.of(4, 2, 29),   LocalDate.of(4,     2, 27) },
            { BritishCutoverDate.of(4, 3,  1),   LocalDate.of(4,     2, 28) },
            { BritishCutoverDate.of(4, 3,  2),   LocalDate.of(4,     2, 29) },
            { BritishCutoverDate.of(4, 3,  3),   LocalDate.of(4,     3,  1) },
            { BritishCutoverDate.of(100, 2, 28), LocalDate.of(100,   2, 26) },
            { BritishCutoverDate.of(100, 2, 29), LocalDate.of(100,   2, 27) },
            { BritishCutoverDate.of(100, 3,  1), LocalDate.of(100,   2, 28) },
            { BritishCutoverDate.of(100, 3,  2), LocalDate.of(100,   3,  1) },
            { BritishCutoverDate.of(100, 3,  3), LocalDate.of(100,   3,  2) },
            { BritishCutoverDate.of(0,  12, 31), LocalDate.of(0,    12, 29) },
            { BritishCutoverDate.of(0,  12, 30), LocalDate.of(0,    12, 28) },
            { BritishCutoverDate.of(1582, 10, 4),  LocalDate.of(1582, 10, 14) },
            { BritishCutoverDate.of(1582, 10, 5),  LocalDate.of(1582, 10, 15) },
            { BritishCutoverDate.of(1751, 12, 20), LocalDate.of(1751, 12, 31) },
            { BritishCutoverDate.of(1751, 12, 31), LocalDate.of(1752,  1, 11) },
            { BritishCutoverDate.of(1752,  1,  1), LocalDate.of(1752,  1, 12) },
            { BritishCutoverDate.of(1752,  9,  1), LocalDate.of(1752,  9, 12) },
            { BritishCutoverDate.of(1752,  9,  2), LocalDate.of(1752,  9, 13) },
            { BritishCutoverDate.of(1752,  9,  3), LocalDate.of(1752,  9, 14) }, // leniently accept invalid
            { BritishCutoverDate.of(1752,  9, 13), LocalDate.of(1752,  9, 24) }, // leniently accept invalid
            { BritishCutoverDate.of(1752,  9, 14), LocalDate.of(1752,  9, 14) },
            { BritishCutoverDate.of(1945, 11, 12), LocalDate.of(1945, 11, 12) },
            { BritishCutoverDate.of(2012,  7,  5), LocalDate.of(2012,  7,  5) },
            { BritishCutoverDate.of(2012,  7,  6), LocalDate.of(2012,  7,  6) },
        };
    }

    /**
     * Verifies that {@code date.until(date)} returns the zero period for every sample date.
     * The {@code iso} parameter documents the corresponding ISO date but is not used in the assertion.
     */
    @ParameterizedTest
    @MethodSource("data_samples")
    public void test_BritishCutoverDate_until_BritishCutoverDate(BritishCutoverDate cutover, LocalDate iso) {
        assertEquals(BritishCutoverChronology.INSTANCE.period(0, 0, 0), cutover.until(cutover));
    }
}
