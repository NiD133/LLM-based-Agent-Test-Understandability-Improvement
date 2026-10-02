package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Tests that PaxDate.until(itself) always returns a zero-length period,
 * regardless of which date is used as both start and end.
 */
@SuppressWarnings({ "static-method" })
public class TestPaxChronology_test_PaxDate_until_PaxDate {

    /**
     * A representative sample of PaxDate values covering:
     * - Epoch boundaries and ordinary dates in CE years
     * - Leap-year dates (months 13 and 14 in leap years like year 6, 12, 400, 2012)
     * - Non-leap year boundaries (months 13 only)
     * - Century and 400-year boundary cases
     * - Negative (BCE) proleptic years
     */
    public static PaxDate[] data_paxDates() {
        return new PaxDate[] {
            // Early CE dates near the epoch
            PaxDate.of(1, 1, 1),
            PaxDate.of(1, 1, 2),
            PaxDate.of(1, 1, 3),
            PaxDate.of(1, 1, 28),
            PaxDate.of(1, 2, 1),
            PaxDate.of(1, 2, 2),
            PaxDate.of(1, 2, 3),

            // Leap year 6: month 13 (Pax week) and month 14
            PaxDate.of(6, 13, 6),
            PaxDate.of(6, 13, 7),
            PaxDate.of(6, 14, 1),
            PaxDate.of(6, 14, 2),
            PaxDate.of(6, 14, 3),
            PaxDate.of(6, 14, 27),
            PaxDate.of(6, 14, 28),
            PaxDate.of(7, 1, 1),
            PaxDate.of(7, 1, 2),

            // Around year 399/400 boundary (400 is a non-leap century)
            PaxDate.of(399, 13, 6),
            PaxDate.of(399, 13, 7),
            PaxDate.of(399, 14, 1),
            PaxDate.of(399, 14, 2),
            PaxDate.of(399, 14, 3),
            PaxDate.of(400, 13, 27),
            PaxDate.of(400, 13, 28),
            PaxDate.of(401, 1, 1),
            PaxDate.of(401, 1, 2),
            PaxDate.of(401, 1, 3),

            // Year 0 (leap): last days of the year
            PaxDate.of(0, 13, 28),
            PaxDate.of(0, 13, 27),

            // Historical dates
            PaxDate.of(1582, 10, 5),
            PaxDate.of(1582, 10, 6),
            PaxDate.of(1945, 10, 28),

            // Leap year 2012: ordinary and Pax-month dates
            PaxDate.of(2012, 6, 23),
            PaxDate.of(2012, 6, 24),

            // Negative proleptic years (BCE)
            PaxDate.of(-6, 1, 1),
            PaxDate.of(-6, 13, 6),
            PaxDate.of(-6, 13, 7),
            PaxDate.of(-6, 14, 1),
            PaxDate.of(-6, 14, 2),
            PaxDate.of(-6, 14, 27),
            PaxDate.of(-6, 14, 28),
            PaxDate.of(-5, 1, 1),
            PaxDate.of(-5, 1, 2),

            // Century -99 (leap) and -100 (non-leap at -100, but check leap rule)
            PaxDate.of(-99, 1, 1),
            PaxDate.of(-99, 13, 6),
            PaxDate.of(-99, 13, 7),
            PaxDate.of(-99, 14, 1),
            PaxDate.of(-99, 14, 2),
            PaxDate.of(-100, 1, 1),
            PaxDate.of(-100, 13, 6),
            PaxDate.of(-100, 13, 7),
            PaxDate.of(-100, 14, 1),
            PaxDate.of(-100, 14, 2),
        };
    }

    /**
     * A date's interval to itself must always be a zero-length period.
     */
    @ParameterizedTest
    @MethodSource("data_paxDates")
    public void test_PaxDate_until_PaxDate(PaxDate pax) {
        assertEquals(PaxChronology.INSTANCE.period(0, 0, 0), pax.until(pax));
    }
}
