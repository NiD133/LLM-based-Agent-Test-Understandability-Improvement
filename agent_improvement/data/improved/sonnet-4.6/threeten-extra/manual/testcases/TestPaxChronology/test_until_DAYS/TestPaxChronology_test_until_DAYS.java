package org.threeten.extra.chrono;

import static java.time.temporal.ChronoUnit.DAYS;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

@SuppressWarnings({ "static-method" })
public class TestPaxChronology_test_until_DAYS {

    /**
     * Pairs of equivalent dates: each PaxDate and its corresponding ISO LocalDate
     * represent the same point in time, so pax.until(iso, DAYS) should always be 0.
     *
     * Columns: paxDate, equivalentIsoDate
     */
    public static Object[][] data_samples() {
        return new Object[][] {
            // Early CE dates around year 1
            { PaxDate.of(1, 1, 1),   LocalDate.of(0, 12, 31) },
            { PaxDate.of(1, 1, 2),   LocalDate.of(1,  1,  1) },
            { PaxDate.of(1, 1, 3),   LocalDate.of(1,  1,  2) },
            { PaxDate.of(1, 1, 28),  LocalDate.of(1,  1, 27) },
            { PaxDate.of(1, 2, 1),   LocalDate.of(1,  1, 28) },
            { PaxDate.of(1, 2, 2),   LocalDate.of(1,  1, 29) },
            { PaxDate.of(1, 2, 3),   LocalDate.of(1,  1, 30) },

            // Year 6 — straddles the leap-month boundary (month 13 is the short Pax month)
            { PaxDate.of(6, 13, 6),  LocalDate.of(6, 12,  1) },
            { PaxDate.of(6, 13, 7),  LocalDate.of(6, 12,  2) },
            { PaxDate.of(6, 14, 1),  LocalDate.of(6, 12,  3) },
            { PaxDate.of(6, 14, 2),  LocalDate.of(6, 12,  4) },
            { PaxDate.of(6, 14, 3),  LocalDate.of(6, 12,  5) },
            { PaxDate.of(6, 14, 27), LocalDate.of(6, 12, 29) },
            { PaxDate.of(6, 14, 28), LocalDate.of(6, 12, 30) },
            { PaxDate.of(7, 1, 1),   LocalDate.of(6, 12, 31) },
            { PaxDate.of(7, 1, 2),   LocalDate.of(7,  1,  1) },

            // Year 399 — near a century boundary
            { PaxDate.of(399, 13, 6),  LocalDate.of(399, 12,  3) },
            { PaxDate.of(399, 13, 7),  LocalDate.of(399, 12,  4) },
            { PaxDate.of(399, 14, 1),  LocalDate.of(399, 12,  5) },
            { PaxDate.of(399, 14, 2),  LocalDate.of(399, 12,  6) },
            { PaxDate.of(399, 14, 3),  LocalDate.of(399, 12,  7) },

            // Year 400 — divisible by 400, so NOT a leap year in Pax (no month 14)
            { PaxDate.of(400, 13, 27), LocalDate.of(400, 12, 29) },
            { PaxDate.of(400, 13, 28), LocalDate.of(400, 12, 30) },

            // Year 401 — just after the 400-year boundary
            { PaxDate.of(401, 1, 1),  LocalDate.of(400, 12, 31) },
            { PaxDate.of(401, 1, 2),  LocalDate.of(401,  1,  1) },
            { PaxDate.of(401, 1, 3),  LocalDate.of(401,  1,  2) },

            // Year 0 (BCE 1) boundary
            { PaxDate.of(0, 13, 28), LocalDate.of(0, 12, 30) },
            { PaxDate.of(0, 13, 27), LocalDate.of(0, 12, 29) },

            // Historic dates
            { PaxDate.of(1582, 10, 5),  LocalDate.of(1582,  9,  9) },
            { PaxDate.of(1582, 10, 6),  LocalDate.of(1582,  9, 10) },
            { PaxDate.of(1945, 10, 28), LocalDate.of(1945, 10,  6) },
            { PaxDate.of(2012,  6, 23), LocalDate.of(2012,  6,  4) },
            { PaxDate.of(2012,  6, 24), LocalDate.of(2012,  6,  5) },

            // Negative years (BCE) — year -6 is a leap year in Pax
            { PaxDate.of(-6,  1,  1),  LocalDate.of(-6,  1,  2) },
            { PaxDate.of(-6, 13,  6),  LocalDate.of(-6, 12,  9) },
            { PaxDate.of(-6, 13,  7),  LocalDate.of(-6, 12, 10) },
            { PaxDate.of(-6, 14,  1),  LocalDate.of(-6, 12, 11) },
            { PaxDate.of(-6, 14,  2),  LocalDate.of(-6, 12, 12) },
            { PaxDate.of(-6, 14, 27),  LocalDate.of(-5,  1,  6) },
            { PaxDate.of(-6, 14, 28),  LocalDate.of(-5,  1,  7) },
            { PaxDate.of(-5,  1,  1),  LocalDate.of(-5,  1,  8) },
            { PaxDate.of(-5,  1,  2),  LocalDate.of(-5,  1,  9) },

            // Year -99 — last two digits are 99, so it IS a leap year
            { PaxDate.of(-99,  1,  1),  LocalDate.of(-99,  1,  6) },
            { PaxDate.of(-99, 13,  6),  LocalDate.of(-99, 12, 13) },
            { PaxDate.of(-99, 13,  7),  LocalDate.of(-99, 12, 14) },
            { PaxDate.of(-99, 14,  1),  LocalDate.of(-99, 12, 15) },
            { PaxDate.of(-99, 14,  2),  LocalDate.of(-99, 12, 16) },

            // Year -100 — last two digits are 00 and NOT divisible by 400, so it IS a leap year
            { PaxDate.of(-100,  1,  1),  LocalDate.of(-101, 12, 31) },
            { PaxDate.of(-100, 13,  6),  LocalDate.of(-100, 12,  7) },
            { PaxDate.of(-100, 13,  7),  LocalDate.of(-100, 12,  8) },
            { PaxDate.of(-100, 14,  1),  LocalDate.of(-100, 12,  9) },
            { PaxDate.of(-100, 14,  2),  LocalDate.of(-100, 12, 10) },
        };
    }

    /**
     * Verifies that {@code PaxDate.until(endDate, DAYS)} returns the correct day count
     * for a range of positive, zero, and negative offsets relative to the equivalent ISO date.
     *
     * The {@code pax} and {@code iso} parameters represent the same calendar instant,
     * so {@code pax.until(iso, DAYS)} is always 0; adding N days to {@code iso} gives N.
     */
    @ParameterizedTest
    @MethodSource("data_samples")
    public void test_until_DAYS(PaxDate pax, LocalDate iso) {
        assertEquals(  0, pax.until(iso.plusDays( 0), DAYS));
        assertEquals(  1, pax.until(iso.plusDays( 1), DAYS));
        assertEquals( 35, pax.until(iso.plusDays(35), DAYS));
        assertEquals(-40, pax.until(iso.minusDays(40), DAYS));
    }
}
