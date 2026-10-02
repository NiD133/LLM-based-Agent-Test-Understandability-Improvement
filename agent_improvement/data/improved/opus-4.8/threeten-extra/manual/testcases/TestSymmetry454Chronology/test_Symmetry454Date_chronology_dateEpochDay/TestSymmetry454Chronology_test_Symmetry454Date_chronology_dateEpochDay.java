package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

/**
 * Verifies that {@link Symmetry454Chronology#dateEpochDay(long)} reconstructs the
 * expected {@link Symmetry454Date} from an epoch-day value.
 * <p>
 * Each sample pairs a Symmetry454 date with the ISO date that shares the same epoch day.
 * The test feeds the ISO date's epoch day into {@code dateEpochDay} and asserts that the
 * matching Symmetry454 date comes back.
 */
@SuppressWarnings({ "static-method" })
public class TestSymmetry454Chronology_test_Symmetry454Date_chronology_dateEpochDay {

    /**
     * Pairs of equivalent dates: { Symmetry454 date, ISO date with the same epoch day }.
     * The labels note the historical event each sample is anchored to.
     */
    public static Stream<Arguments> data_samples() {
        return Stream.of(
                // Constantine the Great, Roman emperor (d. 337)
                Arguments.of(Symmetry454Date.of(1, 1, 1), LocalDate.of(1, 1, 1)),
                Arguments.of(Symmetry454Date.of(272, 2, 30), LocalDate.of(272, 2, 27)),
                Arguments.of(Symmetry454Date.of(272, 2, 27), LocalDate.of(272, 2, 24)),
                // Charlemagne, Frankish king (d. 814)
                Arguments.of(Symmetry454Date.of(742, 3, 25), LocalDate.of(742, 4, 2)),
                Arguments.of(Symmetry454Date.of(742, 4, 2), LocalDate.of(742, 4, 7)),
                // Norman Conquest: Battle of Hastings
                Arguments.of(Symmetry454Date.of(1066, 10, 14), LocalDate.of(1066, 10, 14)),
                // Francesco Petrarca (Petrarch), "Father of Humanism" (d. 1374)
                Arguments.of(Symmetry454Date.of(1304, 7, 21), LocalDate.of(1304, 7, 20)),
                Arguments.of(Symmetry454Date.of(1304, 7, 20), LocalDate.of(1304, 7, 19)),
                // Charles the Bold, son of Isabella of Portugal (d. 1477)
                Arguments.of(Symmetry454Date.of(1433, 11, 14), LocalDate.of(1433, 11, 10)),
                Arguments.of(Symmetry454Date.of(1433, 11, 10), LocalDate.of(1433, 11, 6)),
                // Leonardo da Vinci, Italian painter and inventor (d. 1519)
                Arguments.of(Symmetry454Date.of(1452, 4, 11), LocalDate.of(1452, 4, 15)),
                Arguments.of(Symmetry454Date.of(1452, 4, 15), LocalDate.of(1452, 4, 19)),
                // Christopher Columbus makes landfall in the Caribbean
                Arguments.of(Symmetry454Date.of(1492, 10, 10), LocalDate.of(1492, 10, 12)),
                Arguments.of(Symmetry454Date.of(1492, 10, 12), LocalDate.of(1492, 10, 14)),
                // Galileo Galilei, Italian astronomer and physicist (d. 1642)
                Arguments.of(Symmetry454Date.of(1564, 2, 20), LocalDate.of(1564, 2, 15)),
                Arguments.of(Symmetry454Date.of(1564, 2, 15), LocalDate.of(1564, 2, 10)),
                // William Shakespeare baptized in Stratford-upon-Avon (d. 1616)
                Arguments.of(Symmetry454Date.of(1564, 4, 28), LocalDate.of(1564, 4, 26)),
                Arguments.of(Symmetry454Date.of(1564, 4, 26), LocalDate.of(1564, 4, 24)),
                // Sir Isaac Newton, English physicist and mathematician (d. 1727)
                Arguments.of(Symmetry454Date.of(1643, 1, 7), LocalDate.of(1643, 1, 4)),
                Arguments.of(Symmetry454Date.of(1643, 1, 4), LocalDate.of(1643, 1, 1)),
                // Leonhard Euler, Swiss mathematician and physicist (d. 1783)
                Arguments.of(Symmetry454Date.of(1707, 4, 12), LocalDate.of(1707, 4, 15)),
                Arguments.of(Symmetry454Date.of(1707, 4, 15), LocalDate.of(1707, 4, 18)),
                // French Revolution: citizens of Paris storm the Bastille
                Arguments.of(Symmetry454Date.of(1789, 7, 16), LocalDate.of(1789, 7, 14)),
                Arguments.of(Symmetry454Date.of(1789, 7, 14), LocalDate.of(1789, 7, 12)),
                // Albert Einstein, German theoretical physicist (d. 1955)
                Arguments.of(Symmetry454Date.of(1879, 3, 12), LocalDate.of(1879, 3, 14)),
                Arguments.of(Symmetry454Date.of(1879, 3, 14), LocalDate.of(1879, 3, 16)),
                // Dennis MacAlistair Ritchie, American computer scientist (d. 2011)
                Arguments.of(Symmetry454Date.of(1941, 9, 9), LocalDate.of(1941, 9, 9)),
                // Unix time begins at 00:00:00 UTC/GMT
                Arguments.of(Symmetry454Date.of(1970, 1, 4), LocalDate.of(1970, 1, 1)),
                Arguments.of(Symmetry454Date.of(1970, 1, 1), LocalDate.of(1969, 12, 29)),
                // Start of the 21st century / 3rd millennium
                Arguments.of(Symmetry454Date.of(1999, 12, 27), LocalDate.of(2000, 1, 1)),
                Arguments.of(Symmetry454Date.of(2000, 1, 1), LocalDate.of(2000, 1, 3)));
    }

    @ParameterizedTest
    @MethodSource("data_samples")
    public void test_Symmetry454Date_chronology_dateEpochDay(Symmetry454Date sym454, LocalDate iso) {
        assertEquals(sym454, Symmetry454Chronology.INSTANCE.dateEpochDay(iso.toEpochDay()));
    }
}
