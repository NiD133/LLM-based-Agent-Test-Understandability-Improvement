package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Tests that {@code Symmetry010Date.until(Symmetry010Date)} returns a zero period
 * when both the start and end date are the same date.
 */
@SuppressWarnings({ "static-method" })
public class TestSymmetry010Chronology_test_Symmetry010Date_until_Symmetry010Date {

    /**
     * Historical dates used to exercise the calendar across a wide range of years.
     * Each entry is a single {@code Symmetry010Date} that will be used as both
     * start and end of the {@code until} call.
     */
    public static Symmetry010Date[] data_samples() {
        return new Symmetry010Date[] {
            // Constantine the Great, Roman emperor (d. 337)
            Symmetry010Date.of(1, 1, 1),
            Symmetry010Date.of(272, 2, 28),
            Symmetry010Date.of(272, 2, 27),
            // Charlemagne, Frankish king (d. 814)
            Symmetry010Date.of(742, 3, 27),
            Symmetry010Date.of(742, 4, 2),
            // Norman Conquest: Battle of Hastings
            Symmetry010Date.of(1066, 10, 14),
            // Francesco Petrarca - Petrarch, Italian scholar and poet (d. 1374)
            Symmetry010Date.of(1304, 7, 21),
            Symmetry010Date.of(1304, 7, 20),
            // Charles the Bold, Duke of Burgundy (d. 1477)
            Symmetry010Date.of(1433, 11, 12),
            Symmetry010Date.of(1433, 11, 10),
            // Leonardo da Vinci, Italian painter, sculptor, and architect (d. 1519)
            Symmetry010Date.of(1452, 4, 11),
            Symmetry010Date.of(1452, 4, 15),
            // Christopher Columbus's expedition makes landfall in the Caribbean
            Symmetry010Date.of(1492, 10, 10),
            Symmetry010Date.of(1492, 10, 12),
            // Galileo Galilei, Italian astronomer and physicist (d. 1642)
            Symmetry010Date.of(1564, 2, 18),
            Symmetry010Date.of(1564, 2, 15),
            // William Shakespeare baptized in Stratford-upon-Avon (d. 1616)
            Symmetry010Date.of(1564, 4, 28),
            Symmetry010Date.of(1564, 4, 26),
            // Sir Isaac Newton, English physicist and mathematician (d. 1727)
            Symmetry010Date.of(1643, 1, 7),
            Symmetry010Date.of(1643, 1, 4),
            // Leonhard Euler, Swiss mathematician and physicist (d. 1783)
            Symmetry010Date.of(1707, 4, 12),
            Symmetry010Date.of(1707, 4, 15),
            // French Revolution: Citizens of Paris storm the Bastille
            Symmetry010Date.of(1789, 7, 16),
            Symmetry010Date.of(1789, 7, 14),
            // Albert Einstein, German theoretical physicist (d. 1955)
            Symmetry010Date.of(1879, 3, 14),
            // Dennis MacAlistair Ritchie, American computer scientist (d. 2011)
            Symmetry010Date.of(1941, 9, 11),
            Symmetry010Date.of(1941, 9, 9),
            // Unix epoch begins at 00:00:00 UTC
            Symmetry010Date.of(1970, 1, 4),
            Symmetry010Date.of(1970, 1, 1),
            // Start of the 21st century / 3rd millennium
            Symmetry010Date.of(1999, 12, 29),
            Symmetry010Date.of(2000, 1, 1),
        };
    }

    @ParameterizedTest
    @MethodSource("data_samples")
    @DisplayName("until(self) returns a zero period for any Symmetry010Date")
    public void test_Symmetry010Date_until_Symmetry010Date(Symmetry010Date sym010) {
        assertEquals(Symmetry010Chronology.INSTANCE.period(0, 0, 0), sym010.until(sym010));
    }
}
