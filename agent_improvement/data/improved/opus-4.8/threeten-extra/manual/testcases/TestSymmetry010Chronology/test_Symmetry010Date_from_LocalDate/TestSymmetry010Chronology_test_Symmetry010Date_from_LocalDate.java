package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Tests that {@link Symmetry010Date#from(java.time.temporal.TemporalAccessor)} converts an ISO
 * {@link LocalDate} into the equivalent Symmetry010 date.
 */
@SuppressWarnings({ "static-method" })
public class TestSymmetry010Chronology_test_Symmetry010Date_from_LocalDate {

    /**
     * Pairs of equivalent dates: the expected Symmetry010 date and the ISO date it must be
     * produced from. Each group is anchored to a well-known historical date to make the values
     * easy to sanity-check.
     */
    public static Object[][] data_samples() {
        return new Object[][] {
            // Calendar epoch: Sym010 0001/01/01 is aligned with ISO 0001-01-01
            { Symmetry010Date.of(1, 1, 1), LocalDate.of(1, 1, 1) },

            // Constantine the Great, Roman emperor (d. 337)
            { Symmetry010Date.of(272, 2, 28), LocalDate.of(272, 2, 27) },
            { Symmetry010Date.of(272, 2, 27), LocalDate.of(272, 2, 26) },

            // Charlemagne, Frankish king (d. 814)
            { Symmetry010Date.of(742, 3, 27), LocalDate.of(742, 4, 2) },
            { Symmetry010Date.of(742, 4, 2), LocalDate.of(742, 4, 7) },

            // Norman Conquest: Battle of Hastings
            { Symmetry010Date.of(1066, 10, 14), LocalDate.of(1066, 10, 14) },

            // Francesco Petrarca (Petrarch), "Father of Humanism" (d. 1374)
            { Symmetry010Date.of(1304, 7, 21), LocalDate.of(1304, 7, 20) },
            { Symmetry010Date.of(1304, 7, 20), LocalDate.of(1304, 7, 19) },

            // Charles the Bold, son of Isabella of Portugal, Duchess of Burgundy (d. 1477)
            { Symmetry010Date.of(1433, 11, 12), LocalDate.of(1433, 11, 10) },
            { Symmetry010Date.of(1433, 11, 10), LocalDate.of(1433, 11, 8) },

            // Leonardo da Vinci, Italian painter, sculptor and architect (d. 1519)
            { Symmetry010Date.of(1452, 4, 11), LocalDate.of(1452, 4, 15) },
            { Symmetry010Date.of(1452, 4, 15), LocalDate.of(1452, 4, 19) },

            // Christopher Columbus's expedition makes landfall in the Caribbean
            { Symmetry010Date.of(1492, 10, 10), LocalDate.of(1492, 10, 12) },
            { Symmetry010Date.of(1492, 10, 12), LocalDate.of(1492, 10, 14) },

            // Galileo Galilei, Italian astronomer and physicist (d. 1642)
            { Symmetry010Date.of(1564, 2, 18), LocalDate.of(1564, 2, 15) },
            { Symmetry010Date.of(1564, 2, 15), LocalDate.of(1564, 2, 12) },

            // William Shakespeare is baptized in Stratford-upon-Avon (d. 1616)
            { Symmetry010Date.of(1564, 4, 28), LocalDate.of(1564, 4, 26) },
            { Symmetry010Date.of(1564, 4, 26), LocalDate.of(1564, 4, 24) },

            // Sir Isaac Newton, English physicist and mathematician (d. 1727)
            { Symmetry010Date.of(1643, 1, 7), LocalDate.of(1643, 1, 4) },
            { Symmetry010Date.of(1643, 1, 4), LocalDate.of(1643, 1, 1) },

            // Leonhard Euler, Swiss mathematician and physicist (d. 1783)
            { Symmetry010Date.of(1707, 4, 12), LocalDate.of(1707, 4, 15) },
            { Symmetry010Date.of(1707, 4, 15), LocalDate.of(1707, 4, 18) },

            // French Revolution: citizens of Paris storm the Bastille
            { Symmetry010Date.of(1789, 7, 16), LocalDate.of(1789, 7, 14) },
            { Symmetry010Date.of(1789, 7, 14), LocalDate.of(1789, 7, 12) },

            // Albert Einstein, German theoretical physicist (d. 1955)
            { Symmetry010Date.of(1879, 3, 14), LocalDate.of(1879, 3, 14) },

            // Dennis MacAlistair Ritchie, American computer scientist (d. 2011)
            { Symmetry010Date.of(1941, 9, 11), LocalDate.of(1941, 9, 9) },
            { Symmetry010Date.of(1941, 9, 9), LocalDate.of(1941, 9, 7) },

            // Unix time begins at 00:00:00 UTC/GMT
            { Symmetry010Date.of(1970, 1, 4), LocalDate.of(1970, 1, 1) },
            { Symmetry010Date.of(1970, 1, 1), LocalDate.of(1969, 12, 29) },

            // Start of the 21st century / 3rd millennium
            { Symmetry010Date.of(1999, 12, 29), LocalDate.of(2000, 1, 1) },
            { Symmetry010Date.of(2000, 1, 1), LocalDate.of(2000, 1, 3) },
        };
    }

    @ParameterizedTest
    @MethodSource("data_samples")
    public void test_Symmetry010Date_from_LocalDate(Symmetry010Date expectedSymmetry010Date, LocalDate isoDate) {
        assertEquals(expectedSymmetry010Date, Symmetry010Date.from(isoDate));
    }
}
