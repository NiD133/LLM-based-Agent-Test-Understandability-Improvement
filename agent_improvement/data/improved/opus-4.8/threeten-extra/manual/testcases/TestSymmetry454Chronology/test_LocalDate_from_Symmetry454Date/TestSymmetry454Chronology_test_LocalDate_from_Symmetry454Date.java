package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Verifies that an ISO {@link LocalDate} can be obtained from a {@link Symmetry454Date}
 * via {@link LocalDate#from(java.time.temporal.TemporalAccessor)}.
 *
 * <p>Each sample pairs a Symmetry454 date with the ISO date that falls on the same day,
 * so the conversion is expected to round-trip to that exact ISO date.
 */
@SuppressWarnings({ "static-method" })
public class TestSymmetry454Chronology_test_LocalDate_from_Symmetry454Date {

    /**
     * Pairs of equivalent dates: {@code { symmetry454Date, equivalentIsoDate }}.
     * The accompanying comments note the historical event that motivated each sample.
     */
    public static Object[][] data_samples() {
        return new Object[][] {
            // Constantine the Great, Roman emperor (d. 337)
            { Symmetry454Date.of(1, 1, 1), LocalDate.of(1, 1, 1) },
            { Symmetry454Date.of(272, 2, 30), LocalDate.of(272, 2, 27) },
            { Symmetry454Date.of(272, 2, 27), LocalDate.of(272, 2, 24) },
            // Charlemagne, Frankish king (d. 814)
            { Symmetry454Date.of(742, 3, 25), LocalDate.of(742, 4, 2) },
            { Symmetry454Date.of(742, 4, 2), LocalDate.of(742, 4, 7) },
            // Norman Conquest: Battle of Hastings
            { Symmetry454Date.of(1066, 10, 14), LocalDate.of(1066, 10, 14) },
            // Francesco Petrarca (Petrarch), "Father of Humanism" (d. 1374)
            { Symmetry454Date.of(1304, 7, 21), LocalDate.of(1304, 7, 20) },
            { Symmetry454Date.of(1304, 7, 20), LocalDate.of(1304, 7, 19) },
            // Charles the Bold, son of Isabella of Portugal, Duchess of Burgundy (d. 1477)
            { Symmetry454Date.of(1433, 11, 14), LocalDate.of(1433, 11, 10) },
            { Symmetry454Date.of(1433, 11, 10), LocalDate.of(1433, 11, 6) },
            // Leonardo da Vinci, Italian polymath (d. 1519)
            { Symmetry454Date.of(1452, 4, 11), LocalDate.of(1452, 4, 15) },
            { Symmetry454Date.of(1452, 4, 15), LocalDate.of(1452, 4, 19) },
            // Columbus's expedition makes landfall in the Caribbean
            { Symmetry454Date.of(1492, 10, 10), LocalDate.of(1492, 10, 12) },
            { Symmetry454Date.of(1492, 10, 12), LocalDate.of(1492, 10, 14) },
            // Galileo Galilei, Italian astronomer and physicist (d. 1642)
            { Symmetry454Date.of(1564, 2, 20), LocalDate.of(1564, 2, 15) },
            { Symmetry454Date.of(1564, 2, 15), LocalDate.of(1564, 2, 10) },
            // William Shakespeare baptized in Stratford-upon-Avon (d. 1616)
            { Symmetry454Date.of(1564, 4, 28), LocalDate.of(1564, 4, 26) },
            { Symmetry454Date.of(1564, 4, 26), LocalDate.of(1564, 4, 24) },
            // Sir Isaac Newton, English physicist and mathematician (d. 1727)
            { Symmetry454Date.of(1643, 1, 7), LocalDate.of(1643, 1, 4) },
            { Symmetry454Date.of(1643, 1, 4), LocalDate.of(1643, 1, 1) },
            // Leonhard Euler, Swiss mathematician and physicist (d. 1783)
            { Symmetry454Date.of(1707, 4, 12), LocalDate.of(1707, 4, 15) },
            { Symmetry454Date.of(1707, 4, 15), LocalDate.of(1707, 4, 18) },
            // French Revolution: citizens of Paris storm the Bastille
            { Symmetry454Date.of(1789, 7, 16), LocalDate.of(1789, 7, 14) },
            { Symmetry454Date.of(1789, 7, 14), LocalDate.of(1789, 7, 12) },
            // Albert Einstein, German theoretical physicist (d. 1955)
            { Symmetry454Date.of(1879, 3, 12), LocalDate.of(1879, 3, 14) },
            { Symmetry454Date.of(1879, 3, 14), LocalDate.of(1879, 3, 16) },
            // Dennis MacAlistair Ritchie, American computer scientist (d. 2011)
            { Symmetry454Date.of(1941, 9, 9), LocalDate.of(1941, 9, 9) },
            // Unix time begins at 00:00:00 UTC
            { Symmetry454Date.of(1970, 1, 4), LocalDate.of(1970, 1, 1) },
            { Symmetry454Date.of(1970, 1, 1), LocalDate.of(1969, 12, 29) },
            // Start of the 21st century / 3rd millennium
            { Symmetry454Date.of(1999, 12, 27), LocalDate.of(2000, 1, 1) },
            { Symmetry454Date.of(2000, 1, 1), LocalDate.of(2000, 1, 3) },
        };
    }

    @ParameterizedTest
    @MethodSource("data_samples")
    public void test_LocalDate_from_Symmetry454Date(Symmetry454Date symmetry454Date, LocalDate expectedIsoDate) {
        assertEquals(expectedIsoDate, LocalDate.from(symmetry454Date));
    }
}
