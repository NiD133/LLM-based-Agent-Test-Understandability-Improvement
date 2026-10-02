package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

@SuppressWarnings({ "static-method" })
public class TestSymmetry454Chronology_test_Symmetry454Date_from_LocalDate {

    public static Object[][] data_samples() {
        return new Object[][] {
                sample(1, 1, 1, 1, 1, 1),

                // Constantine the Great, Roman emperor (d. 337)
                sample(272, 2, 30, 272, 2, 27),
                sample(272, 2, 27, 272, 2, 24),

                // Charlemagne, Frankish king (d. 814)
                sample(742, 3, 25, 742, 4, 2),
                sample(742, 4, 2, 742, 4, 7),

                // Norman Conquest: Battle of Hastings
                sample(1066, 10, 14, 1066, 10, 14),

                // Francesco Petrarca - Petrarch, Italian scholar and poet in Renaissance Italy, "Father of Humanism" (d. 1374).
                sample(1304, 7, 21, 1304, 7, 20),
                sample(1304, 7, 20, 1304, 7, 19),

                // Charles the Bold, French son of Isabella of Portugal, Duchess of Burgundy (d. 1477)
                sample(1433, 11, 14, 1433, 11, 10),
                sample(1433, 11, 10, 1433, 11, 6),

                // Leonardo da Vinci, Italian painter, sculptor, and architect (d. 1519)
                sample(1452, 4, 11, 1452, 4, 15),
                sample(1452, 4, 15, 1452, 4, 19),

                // Christopher Columbus's expedition makes landfall in the Caribbean
                sample(1492, 10, 10, 1492, 10, 12),
                sample(1492, 10, 12, 1492, 10, 14),

                // Galileo Galilei, Italian astronomer and physicist (d. 1642)
                sample(1564, 2, 20, 1564, 2, 15),
                sample(1564, 2, 15, 1564, 2, 10),

                // William Shakespeare is baptized in Stratford-upon-Avon, Warwickshire, England (date of actual birth is unknown, d. 1616).
                sample(1564, 4, 28, 1564, 4, 26),
                sample(1564, 4, 26, 1564, 4, 24),

                // Sir Isaac Newton, English physicist and mathematician (d. 1727)
                sample(1643, 1, 7, 1643, 1, 4),
                sample(1643, 1, 4, 1643, 1, 1),

                // Leonhard Euler, Swiss mathematician and physicist (d. 1783)
                sample(1707, 4, 12, 1707, 4, 15),
                sample(1707, 4, 15, 1707, 4, 18),

                // French Revolution: Citizens of Paris storm the Bastille.
                sample(1789, 7, 16, 1789, 7, 14),
                sample(1789, 7, 14, 1789, 7, 12),

                // Albert Einstein, German theoretical physicist (d. 1955).
                sample(1879, 3, 12, 1879, 3, 14),
                sample(1879, 3, 14, 1879, 3, 16),

                // Dennis MacAlistair Ritchie, American computer scientist (d. 2011)
                sample(1941, 9, 9, 1941, 9, 9),

                // Unix time begins at 00:00:00 UTC/GMT.
                sample(1970, 1, 4, 1970, 1, 1),
                sample(1970, 1, 1, 1969, 12, 29),

                // Start of the 21st century or 3rd millennium
                sample(1999, 12, 27, 2000, 1, 1),
                sample(2000, 1, 1, 2000, 1, 3) };
    }

    private static Object[] sample(
            int symmetryYear,
            int symmetryMonth,
            int symmetryDay,
            int isoYear,
            int isoMonth,
            int isoDay) {

        return new Object[] {
                Symmetry454Date.of(symmetryYear, symmetryMonth, symmetryDay),
                LocalDate.of(isoYear, isoMonth, isoDay) };
    }

    @ParameterizedTest
    @MethodSource("data_samples")
    public void test_Symmetry454Date_from_LocalDate(Symmetry454Date sym454, LocalDate iso) {
        assertEquals(sym454, Symmetry454Date.from(iso));
    }
}
