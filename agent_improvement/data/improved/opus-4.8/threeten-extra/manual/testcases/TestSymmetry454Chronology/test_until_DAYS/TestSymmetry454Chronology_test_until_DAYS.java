package org.threeten.extra.chrono;

import static java.time.temporal.ChronoUnit.DAYS;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Tests {@link Symmetry454Date#until(java.time.temporal.Temporal, java.time.temporal.TemporalUnit)}
 * when measuring the distance to an ISO {@link LocalDate} in whole days.
 */
@SuppressWarnings({ "static-method" })
public class TestSymmetry454Chronology_test_until_DAYS {

    /**
     * Pairs of equivalent dates: a {@link Symmetry454Date} and the ISO {@link LocalDate}
     * that falls on the very same day. Because they denote the same instant in time, the
     * number of days between them is zero.
     */
    public static Object[][] data_equivalentSym454AndIsoDates() {
        return new Object[][] {
            { Symmetry454Date.of(1, 1, 1), LocalDate.of(1, 1, 1) },
            { Symmetry454Date.of(272, 2, 30), LocalDate.of(272, 2, 27) },
            { Symmetry454Date.of(272, 2, 27), LocalDate.of(272, 2, 24) },
            { Symmetry454Date.of(742, 3, 25), LocalDate.of(742, 4, 2) },
            { Symmetry454Date.of(742, 4, 2), LocalDate.of(742, 4, 7) },
            { Symmetry454Date.of(1066, 10, 14), LocalDate.of(1066, 10, 14) },
            { Symmetry454Date.of(1304, 7, 21), LocalDate.of(1304, 7, 20) },
            { Symmetry454Date.of(1304, 7, 20), LocalDate.of(1304, 7, 19) },
            { Symmetry454Date.of(1433, 11, 14), LocalDate.of(1433, 11, 10) },
            { Symmetry454Date.of(1433, 11, 10), LocalDate.of(1433, 11, 6) },
            { Symmetry454Date.of(1452, 4, 11), LocalDate.of(1452, 4, 15) },
            { Symmetry454Date.of(1452, 4, 15), LocalDate.of(1452, 4, 19) },
            { Symmetry454Date.of(1492, 10, 10), LocalDate.of(1492, 10, 12) },
            { Symmetry454Date.of(1492, 10, 12), LocalDate.of(1492, 10, 14) },
            { Symmetry454Date.of(1564, 2, 20), LocalDate.of(1564, 2, 15) },
            { Symmetry454Date.of(1564, 2, 15), LocalDate.of(1564, 2, 10) },
            { Symmetry454Date.of(1564, 4, 28), LocalDate.of(1564, 4, 26) },
            { Symmetry454Date.of(1564, 4, 26), LocalDate.of(1564, 4, 24) },
            { Symmetry454Date.of(1643, 1, 7), LocalDate.of(1643, 1, 4) },
            { Symmetry454Date.of(1643, 1, 4), LocalDate.of(1643, 1, 1) },
            { Symmetry454Date.of(1707, 4, 12), LocalDate.of(1707, 4, 15) },
            { Symmetry454Date.of(1707, 4, 15), LocalDate.of(1707, 4, 18) },
            { Symmetry454Date.of(1789, 7, 16), LocalDate.of(1789, 7, 14) },
            { Symmetry454Date.of(1789, 7, 14), LocalDate.of(1789, 7, 12) },
            { Symmetry454Date.of(1879, 3, 12), LocalDate.of(1879, 3, 14) },
            { Symmetry454Date.of(1879, 3, 14), LocalDate.of(1879, 3, 16) },
            { Symmetry454Date.of(1941, 9, 9), LocalDate.of(1941, 9, 9) },
            { Symmetry454Date.of(1970, 1, 4), LocalDate.of(1970, 1, 1) },
            { Symmetry454Date.of(1970, 1, 1), LocalDate.of(1969, 12, 29) },
            { Symmetry454Date.of(1999, 12, 27), LocalDate.of(2000, 1, 1) },
            { Symmetry454Date.of(2000, 1, 1), LocalDate.of(2000, 1, 3) },
        };
    }

    /**
     * Counting in {@link java.time.temporal.ChronoUnit#DAYS DAYS} from a Symmetry454 date to an
     * ISO date should return the signed number of days between them. Starting from an ISO date
     * that is equivalent to the Symmetry454 date, offsetting the target forwards or backwards by
     * a known number of days should yield exactly that offset.
     */
    @ParameterizedTest
    @MethodSource("data_equivalentSym454AndIsoDates")
    public void test_until_DAYS(Symmetry454Date sym454, LocalDate equivalentIso) {
        assertEquals(0, sym454.until(equivalentIso.plusDays(0), DAYS));
        assertEquals(1, sym454.until(equivalentIso.plusDays(1), DAYS));
        assertEquals(35, sym454.until(equivalentIso.plusDays(35), DAYS));
        assertEquals(-40, sym454.until(equivalentIso.minusDays(40), DAYS));
    }
}
