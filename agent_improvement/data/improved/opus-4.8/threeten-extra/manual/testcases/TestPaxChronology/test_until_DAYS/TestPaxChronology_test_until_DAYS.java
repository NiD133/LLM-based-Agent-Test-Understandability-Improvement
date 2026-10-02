package org.threeten.extra.chrono;

import static java.time.temporal.ChronoUnit.DAYS;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Verifies {@link PaxDate#until(java.time.temporal.Temporal, java.time.temporal.TemporalUnit)}
 * when measuring whole {@code DAYS} between a Pax date and an ISO date.
 */
@SuppressWarnings({ "static-method" })
public class TestPaxChronology_test_until_DAYS {

    /**
     * Each row pairs a {@link PaxDate} with the ISO {@link LocalDate} that falls on
     * the very same day. Because both arguments denote the same calendar day, the
     * day-distance between them is exactly the offset that is later added to (or
     * subtracted from) the ISO date.
     */
    public static Object[][] data_equivalentPaxAndIsoDates() {
        return new Object[][] {
            { PaxDate.of(1, 1, 1), LocalDate.of(0, 12, 31) },
            { PaxDate.of(1, 1, 2), LocalDate.of(1, 1, 1) },
            { PaxDate.of(1, 1, 3), LocalDate.of(1, 1, 2) },
            { PaxDate.of(1, 1, 28), LocalDate.of(1, 1, 27) },
            { PaxDate.of(1, 2, 1), LocalDate.of(1, 1, 28) },
            { PaxDate.of(1, 2, 2), LocalDate.of(1, 1, 29) },
            { PaxDate.of(1, 2, 3), LocalDate.of(1, 1, 30) },
            { PaxDate.of(6, 13, 6), LocalDate.of(6, 12, 1) },
            { PaxDate.of(6, 13, 7), LocalDate.of(6, 12, 2) },
            { PaxDate.of(6, 14, 1), LocalDate.of(6, 12, 3) },
            { PaxDate.of(6, 14, 2), LocalDate.of(6, 12, 4) },
            { PaxDate.of(6, 14, 3), LocalDate.of(6, 12, 5) },
            { PaxDate.of(6, 14, 27), LocalDate.of(6, 12, 29) },
            { PaxDate.of(6, 14, 28), LocalDate.of(6, 12, 30) },
            { PaxDate.of(7, 1, 1), LocalDate.of(6, 12, 31) },
            { PaxDate.of(7, 1, 2), LocalDate.of(7, 1, 1) },
            { PaxDate.of(399, 13, 6), LocalDate.of(399, 12, 3) },
            { PaxDate.of(399, 13, 7), LocalDate.of(399, 12, 4) },
            { PaxDate.of(399, 14, 1), LocalDate.of(399, 12, 5) },
            { PaxDate.of(399, 14, 2), LocalDate.of(399, 12, 6) },
            { PaxDate.of(399, 14, 3), LocalDate.of(399, 12, 7) },
            { PaxDate.of(400, 13, 27), LocalDate.of(400, 12, 29) },
            { PaxDate.of(400, 13, 28), LocalDate.of(400, 12, 30) },
            { PaxDate.of(401, 1, 1), LocalDate.of(400, 12, 31) },
            { PaxDate.of(401, 1, 2), LocalDate.of(401, 1, 1) },
            { PaxDate.of(401, 1, 3), LocalDate.of(401, 1, 2) },
            { PaxDate.of(0, 13, 28), LocalDate.of(0, 12, 30) },
            { PaxDate.of(0, 13, 27), LocalDate.of(0, 12, 29) },
            { PaxDate.of(1582, 10, 5), LocalDate.of(1582, 9, 9) },
            { PaxDate.of(1582, 10, 6), LocalDate.of(1582, 9, 10) },
            { PaxDate.of(1945, 10, 28), LocalDate.of(1945, 10, 6) },
            { PaxDate.of(2012, 6, 23), LocalDate.of(2012, 6, 4) },
            { PaxDate.of(2012, 6, 24), LocalDate.of(2012, 6, 5) },
            { PaxDate.of(-6, 1, 1), LocalDate.of(-6, 1, 2) },
            { PaxDate.of(-6, 13, 6), LocalDate.of(-6, 12, 9) },
            { PaxDate.of(-6, 13, 7), LocalDate.of(-6, 12, 10) },
            { PaxDate.of(-6, 14, 1), LocalDate.of(-6, 12, 11) },
            { PaxDate.of(-6, 14, 2), LocalDate.of(-6, 12, 12) },
            { PaxDate.of(-6, 14, 27), LocalDate.of(-5, 1, 6) },
            { PaxDate.of(-6, 14, 28), LocalDate.of(-5, 1, 7) },
            { PaxDate.of(-5, 1, 1), LocalDate.of(-5, 1, 8) },
            { PaxDate.of(-5, 1, 2), LocalDate.of(-5, 1, 9) },
            { PaxDate.of(-99, 1, 1), LocalDate.of(-99, 1, 6) },
            { PaxDate.of(-99, 13, 6), LocalDate.of(-99, 12, 13) },
            { PaxDate.of(-99, 13, 7), LocalDate.of(-99, 12, 14) },
            { PaxDate.of(-99, 14, 1), LocalDate.of(-99, 12, 15) },
            { PaxDate.of(-99, 14, 2), LocalDate.of(-99, 12, 16) },
            { PaxDate.of(-100, 1, 1), LocalDate.of(-101, 12, 31) },
            { PaxDate.of(-100, 13, 6), LocalDate.of(-100, 12, 7) },
            { PaxDate.of(-100, 13, 7), LocalDate.of(-100, 12, 8) },
            { PaxDate.of(-100, 14, 1), LocalDate.of(-100, 12, 9) },
            { PaxDate.of(-100, 14, 2), LocalDate.of(-100, 12, 10) },
        };
    }

    @ParameterizedTest
    @MethodSource("data_equivalentPaxAndIsoDates")
    public void test_until_DAYS(PaxDate pax, LocalDate sameDayAsIso) {
        // The Pax date and the ISO date are the same day, so the day-distance to a
        // shifted ISO date equals the shift itself.
        assertEquals(0, pax.until(sameDayAsIso.plusDays(0), DAYS));
        assertEquals(1, pax.until(sameDayAsIso.plusDays(1), DAYS));
        assertEquals(35, pax.until(sameDayAsIso.plusDays(35), DAYS));
        assertEquals(-40, pax.until(sameDayAsIso.minusDays(40), DAYS));
    }
}
