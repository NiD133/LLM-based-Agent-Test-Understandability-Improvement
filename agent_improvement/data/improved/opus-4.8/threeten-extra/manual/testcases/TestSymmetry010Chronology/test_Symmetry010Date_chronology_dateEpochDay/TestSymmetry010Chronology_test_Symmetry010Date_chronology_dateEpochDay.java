package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

/**
 * Tests that {@link Symmetry010Chronology#dateEpochDay(long)} reconstructs the
 * Symmetry010 date that corresponds to a given epoch day.
 * <p>
 * Each sample pairs a {@link Symmetry010Date} with the ISO {@link LocalDate} that
 * falls on the same epoch day. Feeding that ISO date's epoch day into
 * {@code dateEpochDay} must yield back the matching Symmetry010 date.
 */
@SuppressWarnings({ "static-method" })
public class TestSymmetry010Chronology_test_Symmetry010Date_chronology_dateEpochDay {

    /**
     * Pairs of equivalent dates: each Symmetry010 date and the ISO date sharing its epoch day.
     */
    public static Stream<Arguments> data_samples() {
        return Stream.of(
            Arguments.of(Symmetry010Date.of(1, 1, 1), LocalDate.of(1, 1, 1)),
            Arguments.of(Symmetry010Date.of(272, 2, 28), LocalDate.of(272, 2, 27)),
            Arguments.of(Symmetry010Date.of(272, 2, 27), LocalDate.of(272, 2, 26)),
            Arguments.of(Symmetry010Date.of(742, 3, 27), LocalDate.of(742, 4, 2)),
            Arguments.of(Symmetry010Date.of(742, 4, 2), LocalDate.of(742, 4, 7)),
            Arguments.of(Symmetry010Date.of(1066, 10, 14), LocalDate.of(1066, 10, 14)),
            Arguments.of(Symmetry010Date.of(1304, 7, 21), LocalDate.of(1304, 7, 20)),
            Arguments.of(Symmetry010Date.of(1304, 7, 20), LocalDate.of(1304, 7, 19)),
            Arguments.of(Symmetry010Date.of(1433, 11, 12), LocalDate.of(1433, 11, 10)),
            Arguments.of(Symmetry010Date.of(1433, 11, 10), LocalDate.of(1433, 11, 8)),
            Arguments.of(Symmetry010Date.of(1452, 4, 11), LocalDate.of(1452, 4, 15)),
            Arguments.of(Symmetry010Date.of(1452, 4, 15), LocalDate.of(1452, 4, 19)),
            Arguments.of(Symmetry010Date.of(1492, 10, 10), LocalDate.of(1492, 10, 12)),
            Arguments.of(Symmetry010Date.of(1492, 10, 12), LocalDate.of(1492, 10, 14)),
            Arguments.of(Symmetry010Date.of(1564, 2, 18), LocalDate.of(1564, 2, 15)),
            Arguments.of(Symmetry010Date.of(1564, 2, 15), LocalDate.of(1564, 2, 12)),
            Arguments.of(Symmetry010Date.of(1564, 4, 28), LocalDate.of(1564, 4, 26)),
            Arguments.of(Symmetry010Date.of(1564, 4, 26), LocalDate.of(1564, 4, 24)),
            Arguments.of(Symmetry010Date.of(1643, 1, 7), LocalDate.of(1643, 1, 4)),
            Arguments.of(Symmetry010Date.of(1643, 1, 4), LocalDate.of(1643, 1, 1)),
            Arguments.of(Symmetry010Date.of(1707, 4, 12), LocalDate.of(1707, 4, 15)),
            Arguments.of(Symmetry010Date.of(1707, 4, 15), LocalDate.of(1707, 4, 18)),
            Arguments.of(Symmetry010Date.of(1789, 7, 16), LocalDate.of(1789, 7, 14)),
            Arguments.of(Symmetry010Date.of(1789, 7, 14), LocalDate.of(1789, 7, 12)),
            Arguments.of(Symmetry010Date.of(1879, 3, 14), LocalDate.of(1879, 3, 14)),
            Arguments.of(Symmetry010Date.of(1941, 9, 11), LocalDate.of(1941, 9, 9)),
            Arguments.of(Symmetry010Date.of(1941, 9, 9), LocalDate.of(1941, 9, 7)),
            Arguments.of(Symmetry010Date.of(1970, 1, 4), LocalDate.of(1970, 1, 1)),
            Arguments.of(Symmetry010Date.of(1970, 1, 1), LocalDate.of(1969, 12, 29)),
            Arguments.of(Symmetry010Date.of(1999, 12, 29), LocalDate.of(2000, 1, 1)),
            Arguments.of(Symmetry010Date.of(2000, 1, 1), LocalDate.of(2000, 1, 3)));
    }

    @ParameterizedTest
    @MethodSource("data_samples")
    public void test_Symmetry010Date_chronology_dateEpochDay(Symmetry010Date expectedSym010Date, LocalDate equivalentIsoDate) {
        long epochDay = equivalentIsoDate.toEpochDay();

        Symmetry010Date actualSym010Date = Symmetry010Chronology.INSTANCE.dateEpochDay(epochDay);

        assertEquals(expectedSym010Date, actualSym010Date);
    }
}
