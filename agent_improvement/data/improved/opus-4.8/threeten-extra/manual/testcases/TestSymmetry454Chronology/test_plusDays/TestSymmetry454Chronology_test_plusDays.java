package org.threeten.extra.chrono;

import static java.time.temporal.ChronoUnit.DAYS;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Verifies that adding a number of days to a {@link Symmetry454Date} advances the
 * date by exactly the same amount as adding those days to the equivalent ISO
 * {@link LocalDate}.
 */
@SuppressWarnings({ "static-method" })
public class TestSymmetry454Chronology_test_plusDays {

    /**
     * Pairs of equivalent dates: a {@link Symmetry454Date} and the ISO
     * {@link LocalDate} that represents the very same day.
     */
    public static Object[][] data_equivalentDates() {
        return new Object[][] {
            { Symmetry454Date.of(1, 1, 1),        LocalDate.of(1, 1, 1) },
            { Symmetry454Date.of(272, 2, 30),     LocalDate.of(272, 2, 27) },
            { Symmetry454Date.of(272, 2, 27),     LocalDate.of(272, 2, 24) },
            { Symmetry454Date.of(742, 3, 25),     LocalDate.of(742, 4, 2) },
            { Symmetry454Date.of(742, 4, 2),      LocalDate.of(742, 4, 7) },
            { Symmetry454Date.of(1066, 10, 14),   LocalDate.of(1066, 10, 14) },
            { Symmetry454Date.of(1304, 7, 21),    LocalDate.of(1304, 7, 20) },
            { Symmetry454Date.of(1304, 7, 20),    LocalDate.of(1304, 7, 19) },
            { Symmetry454Date.of(1433, 11, 14),   LocalDate.of(1433, 11, 10) },
            { Symmetry454Date.of(1433, 11, 10),   LocalDate.of(1433, 11, 6) },
            { Symmetry454Date.of(1452, 4, 11),    LocalDate.of(1452, 4, 15) },
            { Symmetry454Date.of(1452, 4, 15),    LocalDate.of(1452, 4, 19) },
            { Symmetry454Date.of(1492, 10, 10),   LocalDate.of(1492, 10, 12) },
            { Symmetry454Date.of(1492, 10, 12),   LocalDate.of(1492, 10, 14) },
            { Symmetry454Date.of(1564, 2, 20),    LocalDate.of(1564, 2, 15) },
            { Symmetry454Date.of(1564, 2, 15),    LocalDate.of(1564, 2, 10) },
            { Symmetry454Date.of(1564, 4, 28),    LocalDate.of(1564, 4, 26) },
            { Symmetry454Date.of(1564, 4, 26),    LocalDate.of(1564, 4, 24) },
            { Symmetry454Date.of(1643, 1, 7),     LocalDate.of(1643, 1, 4) },
            { Symmetry454Date.of(1643, 1, 4),     LocalDate.of(1643, 1, 1) },
            { Symmetry454Date.of(1707, 4, 12),    LocalDate.of(1707, 4, 15) },
            { Symmetry454Date.of(1707, 4, 15),    LocalDate.of(1707, 4, 18) },
            { Symmetry454Date.of(1789, 7, 16),    LocalDate.of(1789, 7, 14) },
            { Symmetry454Date.of(1789, 7, 14),    LocalDate.of(1789, 7, 12) },
            { Symmetry454Date.of(1879, 3, 12),    LocalDate.of(1879, 3, 14) },
            { Symmetry454Date.of(1879, 3, 14),    LocalDate.of(1879, 3, 16) },
            { Symmetry454Date.of(1941, 9, 9),     LocalDate.of(1941, 9, 9) },
            { Symmetry454Date.of(1970, 1, 4),     LocalDate.of(1970, 1, 1) },
            { Symmetry454Date.of(1970, 1, 1),     LocalDate.of(1969, 12, 29) },
            { Symmetry454Date.of(1999, 12, 27),   LocalDate.of(2000, 1, 1) },
            { Symmetry454Date.of(2000, 1, 1),     LocalDate.of(2000, 1, 3) },
        };
    }

    /**
     * Adding the same offset (zero, positive and negative) to both calendars must
     * keep them pointing at the same day, as confirmed by converting the
     * Symmetry454 result back to an ISO {@link LocalDate}.
     */
    @ParameterizedTest
    @MethodSource("data_equivalentDates")
    public void test_plusDays(Symmetry454Date sym454, LocalDate iso) {
        assertEquals(iso, LocalDate.from(sym454.plus(0, DAYS)));
        assertEquals(iso.plusDays(1), LocalDate.from(sym454.plus(1, DAYS)));
        assertEquals(iso.plusDays(35), LocalDate.from(sym454.plus(35, DAYS)));
        assertEquals(iso.plusDays(-1), LocalDate.from(sym454.plus(-1, DAYS)));
        assertEquals(iso.plusDays(-60), LocalDate.from(sym454.plus(-60, DAYS)));
    }
}
