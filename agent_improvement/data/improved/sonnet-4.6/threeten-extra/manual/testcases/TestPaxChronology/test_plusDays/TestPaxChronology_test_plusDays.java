package org.threeten.extra.chrono;

import static java.time.temporal.ChronoUnit.DAYS;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

@SuppressWarnings({ "static-method" })
public class TestPaxChronology_test_plusDays {

    /**
     * Pairs of (PaxDate, equivalent ISO LocalDate) used to verify that adding
     * days in the Pax calendar produces the same result as adding days in ISO.
     *
     * Groups:
     *   - Epoch boundary: Pax year 1, month 1 maps to late ISO year 0
     *   - Month boundaries within a regular year (year 1)
     *   - Leap-year (year 6) with the extra "Pax" month (month 13, 7 days)
     *   - Century boundaries around years 399-401 (non-leap 400)
     *   - Year 0 (BCE) edge cases
     *   - Historical spot-checks (1582, 1945, 2012)
     *   - Negative proleptic years (-5, -6, -99, -100) with their own leap rules
     */
    public static Object[][] data_samples() {
        return new Object[][] {
            // --- Epoch boundary: Pax 0001-01-01 corresponds to ISO 0000-12-31 ---
            { PaxDate.of(1, 1, 1),  LocalDate.of(0,  12, 31) },
            { PaxDate.of(1, 1, 2),  LocalDate.of(1,   1,  1) },
            { PaxDate.of(1, 1, 3),  LocalDate.of(1,   1,  2) },
            { PaxDate.of(1, 1, 28), LocalDate.of(1,   1, 27) },

            // --- Month boundary within year 1 (no leap month) ---
            { PaxDate.of(1, 2, 1),  LocalDate.of(1,   1, 28) },
            { PaxDate.of(1, 2, 2),  LocalDate.of(1,   1, 29) },
            { PaxDate.of(1, 2, 3),  LocalDate.of(1,   1, 30) },

            // --- Leap year 6: month 13 is the 7-day "Pax" intercalary month;
            //     month 14 follows it (the usual 13th month shifted by one) ---
            { PaxDate.of(6, 13, 6),  LocalDate.of(6,  12,  1) },
            { PaxDate.of(6, 13, 7),  LocalDate.of(6,  12,  2) },
            { PaxDate.of(6, 14, 1),  LocalDate.of(6,  12,  3) },
            { PaxDate.of(6, 14, 2),  LocalDate.of(6,  12,  4) },
            { PaxDate.of(6, 14, 3),  LocalDate.of(6,  12,  5) },
            { PaxDate.of(6, 14, 27), LocalDate.of(6,  12, 29) },
            { PaxDate.of(6, 14, 28), LocalDate.of(6,  12, 30) },
            { PaxDate.of(7, 1, 1),   LocalDate.of(6,  12, 31) },
            { PaxDate.of(7, 1, 2),   LocalDate.of(7,   1,  1) },

            // --- Year 399 (leap year: 99 last two digits) ---
            { PaxDate.of(399, 13, 6),  LocalDate.of(399, 12,  3) },
            { PaxDate.of(399, 13, 7),  LocalDate.of(399, 12,  4) },
            { PaxDate.of(399, 14, 1),  LocalDate.of(399, 12,  5) },
            { PaxDate.of(399, 14, 2),  LocalDate.of(399, 12,  6) },
            { PaxDate.of(399, 14, 3),  LocalDate.of(399, 12,  7) },

            // --- Year 400 is NOT a leap year (divisible by 400), so only 13 months ---
            { PaxDate.of(400, 13, 27), LocalDate.of(400, 12, 29) },
            { PaxDate.of(400, 13, 28), LocalDate.of(400, 12, 30) },
            { PaxDate.of(401, 1, 1),   LocalDate.of(400, 12, 31) },
            { PaxDate.of(401, 1, 2),   LocalDate.of(401,  1,  1) },
            { PaxDate.of(401, 1, 3),   LocalDate.of(401,  1,  2) },

            // --- Year 0 (proleptic BCE boundary) is a leap year (last two digits 00,
            //     but 0 is not divisible by 400) ---
            { PaxDate.of(0, 13, 28), LocalDate.of(0, 12, 30) },
            { PaxDate.of(0, 13, 27), LocalDate.of(0, 12, 29) },

            // --- Historical spot-checks ---
            { PaxDate.of(1582, 10, 5),  LocalDate.of(1582,  9,  9) },
            { PaxDate.of(1582, 10, 6),  LocalDate.of(1582,  9, 10) },
            { PaxDate.of(1945, 10, 28), LocalDate.of(1945, 10,  6) },
            { PaxDate.of(2012,  6, 23), LocalDate.of(2012,  6,  4) },
            { PaxDate.of(2012,  6, 24), LocalDate.of(2012,  6,  5) },

            // --- Negative proleptic years ---
            // Year -6 is a leap year (last two digits of |-6| = 6, divisible by 6)
            { PaxDate.of(-6,  1,  1),  LocalDate.of(-6,   1,  2) },
            { PaxDate.of(-6, 13,  6),  LocalDate.of(-6,  12,  9) },
            { PaxDate.of(-6, 13,  7),  LocalDate.of(-6,  12, 10) },
            { PaxDate.of(-6, 14,  1),  LocalDate.of(-6,  12, 11) },
            { PaxDate.of(-6, 14,  2),  LocalDate.of(-6,  12, 12) },
            { PaxDate.of(-6, 14, 27),  LocalDate.of(-5,   1,  6) },
            { PaxDate.of(-6, 14, 28),  LocalDate.of(-5,   1,  7) },

            // Year -5 is not a leap year
            { PaxDate.of(-5, 1, 1),  LocalDate.of(-5,  1,  8) },
            { PaxDate.of(-5, 1, 2),  LocalDate.of(-5,  1,  9) },

            // Year -99 is a leap year (last two digits 99)
            { PaxDate.of(-99,  1,  1),  LocalDate.of(-99,  1,  6) },
            { PaxDate.of(-99, 13,  6),  LocalDate.of(-99, 12, 13) },
            { PaxDate.of(-99, 13,  7),  LocalDate.of(-99, 12, 14) },
            { PaxDate.of(-99, 14,  1),  LocalDate.of(-99, 12, 15) },
            { PaxDate.of(-99, 14,  2),  LocalDate.of(-99, 12, 16) },

            // Year -100 is NOT a leap year (last two digits 00, but |-100| % 400 == 100, not 0;
            //   however 100 % 6 != 0 and 100 != 99, so -100 is not a leap year)
            { PaxDate.of(-100,  1,  1),  LocalDate.of(-101, 12, 31) },
            { PaxDate.of(-100, 13,  6),  LocalDate.of(-100, 12,  7) },
            { PaxDate.of(-100, 13,  7),  LocalDate.of(-100, 12,  8) },
            { PaxDate.of(-100, 14,  1),  LocalDate.of(-100, 12,  9) },
            { PaxDate.of(-100, 14,  2),  LocalDate.of(-100, 12, 10) },
        };
    }

    /**
     * Verifies that adding N days to a PaxDate yields the same calendar date as
     * adding N days to the equivalent ISO LocalDate, for several values of N
     * (0, +1, +35, -1, -60) that cross month and year boundaries.
     */
    @ParameterizedTest
    @MethodSource("data_samples")
    public void test_plusDays(PaxDate pax, LocalDate iso) {
        assertEquals(iso,              LocalDate.from(pax.plus(0,   DAYS)));
        assertEquals(iso.plusDays(1),  LocalDate.from(pax.plus(1,   DAYS)));
        assertEquals(iso.plusDays(35), LocalDate.from(pax.plus(35,  DAYS)));
        assertEquals(iso.plusDays(-1), LocalDate.from(pax.plus(-1,  DAYS)));
        assertEquals(iso.plusDays(-60),LocalDate.from(pax.plus(-60, DAYS)));
    }
}
