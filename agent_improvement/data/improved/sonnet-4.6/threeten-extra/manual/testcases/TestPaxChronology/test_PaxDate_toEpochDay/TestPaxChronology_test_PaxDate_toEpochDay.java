package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

@SuppressWarnings({ "static-method" })
public class TestPaxChronology_test_PaxDate_toEpochDay {

    // Maps PaxDate samples to equivalent ISO LocalDate values.
    // In the Pax calendar: 0001-01-01 (Pax) == 0000-12-31 (ISO).
    // Leap years add a 7-day "Pax" month between month 12 and 13,
    // shifting the original month 13 to become month 14.
    public static Object[][] data_samples() {
        return new Object[][] {
            // Year 1: Pax epoch starts one day before ISO epoch
            { PaxDate.of(1, 1,  1), LocalDate.of(0, 12, 31) },
            { PaxDate.of(1, 1,  2), LocalDate.of(1,  1,  1) },  // ISO epoch day 0
            { PaxDate.of(1, 1,  3), LocalDate.of(1,  1,  2) },
            { PaxDate.of(1, 1, 28), LocalDate.of(1,  1, 27) },
            { PaxDate.of(1, 2,  1), LocalDate.of(1,  1, 28) },
            { PaxDate.of(1, 2,  2), LocalDate.of(1,  1, 29) },
            { PaxDate.of(1, 2,  3), LocalDate.of(1,  1, 30) },

            // Year 6 (leap): Pax month (month 13, 7 days) inserted before regular month 13→14
            { PaxDate.of(6, 13,  6), LocalDate.of(6, 12,  1) },
            { PaxDate.of(6, 13,  7), LocalDate.of(6, 12,  2) },
            { PaxDate.of(6, 14,  1), LocalDate.of(6, 12,  3) },
            { PaxDate.of(6, 14,  2), LocalDate.of(6, 12,  4) },
            { PaxDate.of(6, 14,  3), LocalDate.of(6, 12,  5) },
            { PaxDate.of(6, 14, 27), LocalDate.of(6, 12, 29) },
            { PaxDate.of(6, 14, 28), LocalDate.of(6, 12, 30) },
            { PaxDate.of(7,  1,  1), LocalDate.of(6, 12, 31) },
            { PaxDate.of(7,  1,  2), LocalDate.of(7,  1,  1) },

            // Year 399 (leap): last two digits 99 → leap year
            { PaxDate.of(399, 13, 6), LocalDate.of(399, 12,  3) },
            { PaxDate.of(399, 13, 7), LocalDate.of(399, 12,  4) },
            { PaxDate.of(399, 14, 1), LocalDate.of(399, 12,  5) },
            { PaxDate.of(399, 14, 2), LocalDate.of(399, 12,  6) },
            { PaxDate.of(399, 14, 3), LocalDate.of(399, 12,  7) },

            // Year 400 (NOT leap): divisible by 400 → not a leap year; month 13 has 28 days
            { PaxDate.of(400, 13, 27), LocalDate.of(400, 12, 29) },
            { PaxDate.of(400, 13, 28), LocalDate.of(400, 12, 30) },
            { PaxDate.of(401,  1,  1), LocalDate.of(400, 12, 31) },
            { PaxDate.of(401,  1,  2), LocalDate.of(401,  1,  1) },
            { PaxDate.of(401,  1,  3), LocalDate.of(401,  1,  2) },

            // Year 0 (leap): last two digits 00, not divisible by 400 → leap
            { PaxDate.of(0, 13, 27), LocalDate.of(0, 12, 29) },
            { PaxDate.of(0, 13, 28), LocalDate.of(0, 12, 30) },

            // Historical dates (Gregorian reform era and WWII)
            { PaxDate.of(1582, 10,  5), LocalDate.of(1582,  9,  9) },
            { PaxDate.of(1582, 10,  6), LocalDate.of(1582,  9, 10) },
            { PaxDate.of(1945, 10, 28), LocalDate.of(1945, 10,  6) },
            { PaxDate.of(2012,  6, 23), LocalDate.of(2012,  6,  4) },
            { PaxDate.of(2012,  6, 24), LocalDate.of(2012,  6,  5) },

            // Negative year -6 (leap): Pax month spans year boundary with next ISO year
            { PaxDate.of(-6,  1,  1), LocalDate.of(-6,  1,  2) },
            { PaxDate.of(-6, 13,  6), LocalDate.of(-6, 12,  9) },
            { PaxDate.of(-6, 13,  7), LocalDate.of(-6, 12, 10) },
            { PaxDate.of(-6, 14,  1), LocalDate.of(-6, 12, 11) },
            { PaxDate.of(-6, 14,  2), LocalDate.of(-6, 12, 12) },
            { PaxDate.of(-6, 14, 27), LocalDate.of(-5,  1,  6) },
            { PaxDate.of(-6, 14, 28), LocalDate.of(-5,  1,  7) },
            { PaxDate.of(-5,  1,  1), LocalDate.of(-5,  1,  8) },
            { PaxDate.of(-5,  1,  2), LocalDate.of(-5,  1,  9) },

            // Negative year -99 (leap): last two digits 99 → leap
            { PaxDate.of(-99,  1,  1), LocalDate.of(-99,  1,  6) },
            { PaxDate.of(-99, 13,  6), LocalDate.of(-99, 12, 13) },
            { PaxDate.of(-99, 13,  7), LocalDate.of(-99, 12, 14) },
            { PaxDate.of(-99, 14,  1), LocalDate.of(-99, 12, 15) },
            { PaxDate.of(-99, 14,  2), LocalDate.of(-99, 12, 16) },

            // Negative year -100 (leap): last two digits 00, not divisible by 400 → leap
            { PaxDate.of(-100,  1,  1), LocalDate.of(-101, 12, 31) },
            { PaxDate.of(-100, 13,  6), LocalDate.of(-100, 12,  7) },
            { PaxDate.of(-100, 13,  7), LocalDate.of(-100, 12,  8) },
            { PaxDate.of(-100, 14,  1), LocalDate.of(-100, 12,  9) },
            { PaxDate.of(-100, 14,  2), LocalDate.of(-100, 12, 10) },
        };
    }

    @ParameterizedTest
    @MethodSource("data_samples")
    public void test_PaxDate_toEpochDay(PaxDate pax, LocalDate iso) {
        assertEquals(iso.toEpochDay(), pax.toEpochDay());
    }
}
