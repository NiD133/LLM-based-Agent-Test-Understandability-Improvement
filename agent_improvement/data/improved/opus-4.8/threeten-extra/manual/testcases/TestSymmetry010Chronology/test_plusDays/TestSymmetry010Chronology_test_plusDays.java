package org.threeten.extra.chrono;

import static java.time.temporal.ChronoUnit.DAYS;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Tests that {@link Symmetry010Date#plus(long, java.time.temporal.TemporalUnit)} with the
 * {@code DAYS} unit advances a Symmetry010 date by exactly the same number of days as the
 * equivalent ISO {@link LocalDate}.
 */
@SuppressWarnings({ "static-method" })
public class TestSymmetry010Chronology_test_plusDays {

    /**
     * Pairs of equivalent dates: a Symmetry010 date and the ISO {@link LocalDate} that falls
     * on the same day. Adding the same number of days to each must keep them equivalent.
     */
    public static Object[][] data_equivalentDates() {
        return new Object[][] {
            { Symmetry010Date.of(1, 1, 1), LocalDate.of(1, 1, 1) },
            { Symmetry010Date.of(272, 2, 28), LocalDate.of(272, 2, 27) },
            { Symmetry010Date.of(272, 2, 27), LocalDate.of(272, 2, 26) },
            { Symmetry010Date.of(742, 3, 27), LocalDate.of(742, 4, 2) },
            { Symmetry010Date.of(742, 4, 2), LocalDate.of(742, 4, 7) },
            { Symmetry010Date.of(1066, 10, 14), LocalDate.of(1066, 10, 14) },
            { Symmetry010Date.of(1304, 7, 21), LocalDate.of(1304, 7, 20) },
            { Symmetry010Date.of(1304, 7, 20), LocalDate.of(1304, 7, 19) },
            { Symmetry010Date.of(1433, 11, 12), LocalDate.of(1433, 11, 10) },
            { Symmetry010Date.of(1433, 11, 10), LocalDate.of(1433, 11, 8) },
            { Symmetry010Date.of(1452, 4, 11), LocalDate.of(1452, 4, 15) },
            { Symmetry010Date.of(1452, 4, 15), LocalDate.of(1452, 4, 19) },
            { Symmetry010Date.of(1492, 10, 10), LocalDate.of(1492, 10, 12) },
            { Symmetry010Date.of(1492, 10, 12), LocalDate.of(1492, 10, 14) },
            { Symmetry010Date.of(1564, 2, 18), LocalDate.of(1564, 2, 15) },
            { Symmetry010Date.of(1564, 2, 15), LocalDate.of(1564, 2, 12) },
            { Symmetry010Date.of(1564, 4, 28), LocalDate.of(1564, 4, 26) },
            { Symmetry010Date.of(1564, 4, 26), LocalDate.of(1564, 4, 24) },
            { Symmetry010Date.of(1643, 1, 7), LocalDate.of(1643, 1, 4) },
            { Symmetry010Date.of(1643, 1, 4), LocalDate.of(1643, 1, 1) },
            { Symmetry010Date.of(1707, 4, 12), LocalDate.of(1707, 4, 15) },
            { Symmetry010Date.of(1707, 4, 15), LocalDate.of(1707, 4, 18) },
            { Symmetry010Date.of(1789, 7, 16), LocalDate.of(1789, 7, 14) },
            { Symmetry010Date.of(1789, 7, 14), LocalDate.of(1789, 7, 12) },
            { Symmetry010Date.of(1879, 3, 14), LocalDate.of(1879, 3, 14) },
            { Symmetry010Date.of(1941, 9, 11), LocalDate.of(1941, 9, 9) },
            { Symmetry010Date.of(1941, 9, 9), LocalDate.of(1941, 9, 7) },
            { Symmetry010Date.of(1970, 1, 4), LocalDate.of(1970, 1, 1) },
            { Symmetry010Date.of(1970, 1, 1), LocalDate.of(1969, 12, 29) },
            { Symmetry010Date.of(1999, 12, 29), LocalDate.of(2000, 1, 1) },
            { Symmetry010Date.of(2000, 1, 1), LocalDate.of(2000, 1, 3) },
        };
    }

    @ParameterizedTest
    @MethodSource("data_equivalentDates")
    public void test_plusDays(Symmetry010Date sym010, LocalDate iso) {
        // Adding any day count to the Symmetry010 date must land on the same calendar day
        // as adding that same count to the equivalent ISO date.
        assertEquals(iso, LocalDate.from(sym010.plus(0, DAYS)));
        assertEquals(iso.plusDays(1), LocalDate.from(sym010.plus(1, DAYS)));
        assertEquals(iso.plusDays(35), LocalDate.from(sym010.plus(35, DAYS)));
        assertEquals(iso.plusDays(-1), LocalDate.from(sym010.plus(-1, DAYS)));
        assertEquals(iso.plusDays(-60), LocalDate.from(sym010.plus(-60, DAYS)));
    }
}
