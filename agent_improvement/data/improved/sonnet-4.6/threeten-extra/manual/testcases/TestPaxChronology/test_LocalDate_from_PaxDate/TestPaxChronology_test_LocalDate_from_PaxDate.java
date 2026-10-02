package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Tests that {@link LocalDate#from(java.time.temporal.TemporalAccessor)} correctly converts
 * a {@link PaxDate} to its ISO {@link LocalDate} equivalent.
 *
 * <p>The Pax calendar starts one day before the ISO epoch: Pax 0001-01-01 == ISO 0000-12-31.
 * Leap years in Pax insert an extra 7-day month (month 13), shifting the normal month 13 to 14.
 */
@SuppressWarnings({"static-method"})
public class TestPaxChronology_test_LocalDate_from_PaxDate {

    // Each row: { PaxDate input, expected ISO LocalDate }
    public static Object[][] data_samples() {
        return new Object[][] {
            // --- Early CE dates (year 1) ---
            { PaxDate.of(1, 1,  1), LocalDate.of(0, 12, 31) },
            { PaxDate.of(1, 1,  2), LocalDate.of(1,  1,  1) },
            { PaxDate.of(1, 1,  3), LocalDate.of(1,  1,  2) },
            { PaxDate.of(1, 1, 28), LocalDate.of(1,  1, 27) },
            { PaxDate.of(1, 2,  1), LocalDate.of(1,  1, 28) },
            { PaxDate.of(1, 2,  2), LocalDate.of(1,  1, 29) },
            { PaxDate.of(1, 2,  3), LocalDate.of(1,  1, 30) },

            // --- Pax leap year 6: month 13 (leap week) and month 14 ---
            { PaxDate.of(6, 13,  6), LocalDate.of(6, 12,  1) },
            { PaxDate.of(6, 13,  7), LocalDate.of(6, 12,  2) },
            { PaxDate.of(6, 14,  1), LocalDate.of(6, 12,  3) },
            { PaxDate.of(6, 14,  2), LocalDate.of(6, 12,  4) },
            { PaxDate.of(6, 14,  3), LocalDate.of(6, 12,  5) },
            { PaxDate.of(6, 14, 27), LocalDate.of(6, 12, 29) },
            { PaxDate.of(6, 14, 28), LocalDate.of(6, 12, 30) },
            { PaxDate.of(7,  1,  1), LocalDate.of(6, 12, 31) },
            { PaxDate.of(7,  1,  2), LocalDate.of(7,  1,  1) },

            // --- Around year 399–401 (non-400 boundary) ---
            { PaxDate.of(399, 13,  6), LocalDate.of(399, 12,  3) },
            { PaxDate.of(399, 13,  7), LocalDate.of(399, 12,  4) },
            { PaxDate.of(399, 14,  1), LocalDate.of(399, 12,  5) },
            { PaxDate.of(399, 14,  2), LocalDate.of(399, 12,  6) },
            { PaxDate.of(399, 14,  3), LocalDate.of(399, 12,  7) },

            // --- Year 400 is NOT a Pax leap year (divisible by 400) ---
            { PaxDate.of(400, 13, 27), LocalDate.of(400, 12, 29) },
            { PaxDate.of(400, 13, 28), LocalDate.of(400, 12, 30) },
            { PaxDate.of(401,  1,  1), LocalDate.of(400, 12, 31) },
            { PaxDate.of(401,  1,  2), LocalDate.of(401,  1,  1) },
            { PaxDate.of(401,  1,  3), LocalDate.of(401,  1,  2) },

            // --- Year 0 (BCE 1 in Pax) ---
            { PaxDate.of(0, 13, 28), LocalDate.of(0, 12, 30) },
            { PaxDate.of(0, 13, 27), LocalDate.of(0, 12, 29) },

            // --- Historic dates ---
            { PaxDate.of(1582, 10,  5), LocalDate.of(1582,  9,  9) },
            { PaxDate.of(1582, 10,  6), LocalDate.of(1582,  9, 10) },
            { PaxDate.of(1945, 10, 28), LocalDate.of(1945, 10,  6) },
            { PaxDate.of(2012,  6, 23), LocalDate.of(2012,  6,  4) },
            { PaxDate.of(2012,  6, 24), LocalDate.of(2012,  6,  5) },

            // --- Negative (BCE) years ---
            { PaxDate.of(-6,  1,  1), LocalDate.of(-6,   1,  2) },
            { PaxDate.of(-6, 13,  6), LocalDate.of(-6,  12,  9) },
            { PaxDate.of(-6, 13,  7), LocalDate.of(-6,  12, 10) },
            { PaxDate.of(-6, 14,  1), LocalDate.of(-6,  12, 11) },
            { PaxDate.of(-6, 14,  2), LocalDate.of(-6,  12, 12) },
            { PaxDate.of(-6, 14, 27), LocalDate.of(-5,   1,  6) },
            { PaxDate.of(-6, 14, 28), LocalDate.of(-5,   1,  7) },
            { PaxDate.of(-5,  1,  1), LocalDate.of(-5,   1,  8) },
            { PaxDate.of(-5,  1,  2), LocalDate.of(-5,   1,  9) },

            { PaxDate.of(-99,  1,  1), LocalDate.of(-99,  1,  6) },
            { PaxDate.of(-99, 13,  6), LocalDate.of(-99, 12, 13) },
            { PaxDate.of(-99, 13,  7), LocalDate.of(-99, 12, 14) },
            { PaxDate.of(-99, 14,  1), LocalDate.of(-99, 12, 15) },
            { PaxDate.of(-99, 14,  2), LocalDate.of(-99, 12, 16) },

            // --- Year -100: NOT a Pax leap year (last two digits 00, but -100 is not divisible by 400)
            //     Wait — actually -100 % 400 != 0 so it IS a leap year in Pax.
            //     Year -100 has month 13 (leap week) and month 14. ---
            { PaxDate.of(-100,   1,  1), LocalDate.of(-101, 12, 31) },
            { PaxDate.of(-100,  13,  6), LocalDate.of(-100, 12,  7) },
            { PaxDate.of(-100,  13,  7), LocalDate.of(-100, 12,  8) },
            { PaxDate.of(-100,  14,  1), LocalDate.of(-100, 12,  9) },
            { PaxDate.of(-100,  14,  2), LocalDate.of(-100, 12, 10) },
        };
    }

    @ParameterizedTest
    @MethodSource("data_samples")
    public void test_LocalDate_from_PaxDate(PaxDate pax, LocalDate iso) {
        assertEquals(iso, LocalDate.from(pax));
    }
}
