package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

@SuppressWarnings({ "static-method" })
public class TestSymmetry454Chronology_test_Symmetry454Date_toEpochDay {

    /**
     * Pairs of (Symmetry454Date, equivalent ISO LocalDate) used to verify that
     * toEpochDay() returns the same epoch-day value in both calendar systems.
     *
     * Each entry was chosen to cover a historically notable date or a calendar
     * boundary (e.g. leap-week years, quarter boundaries, the Unix epoch).
     */
    public static Object[][] data_samples() {
        return new Object[][] {
            // Year 1, first day of the Symmetry454 epoch
            { Symmetry454Date.of(1, 1, 1),       LocalDate.of(1, 1, 1) },

            // Constantine the Great, Roman emperor (b. 272)
            { Symmetry454Date.of(272, 2, 30),    LocalDate.of(272, 2, 27) },
            { Symmetry454Date.of(272, 2, 27),    LocalDate.of(272, 2, 24) },

            // Charlemagne, Frankish king (b. 742)
            { Symmetry454Date.of(742, 3, 25),    LocalDate.of(742, 4, 2) },
            { Symmetry454Date.of(742, 4, 2),     LocalDate.of(742, 4, 7) },

            // Norman Conquest: Battle of Hastings (1066-10-14)
            { Symmetry454Date.of(1066, 10, 14),  LocalDate.of(1066, 10, 14) },

            // Francesco Petrarca (Petrarch), "Father of Humanism" (b. 1304)
            { Symmetry454Date.of(1304, 7, 21),   LocalDate.of(1304, 7, 20) },
            { Symmetry454Date.of(1304, 7, 20),   LocalDate.of(1304, 7, 19) },

            // Charles the Bold, Duke of Burgundy (b. 1433)
            { Symmetry454Date.of(1433, 11, 14),  LocalDate.of(1433, 11, 10) },
            { Symmetry454Date.of(1433, 11, 10),  LocalDate.of(1433, 11, 6) },

            // Leonardo da Vinci, painter and polymath (b. 1452)
            { Symmetry454Date.of(1452, 4, 11),   LocalDate.of(1452, 4, 15) },
            { Symmetry454Date.of(1452, 4, 15),   LocalDate.of(1452, 4, 19) },

            // Christopher Columbus makes landfall in the Caribbean (1492)
            { Symmetry454Date.of(1492, 10, 10),  LocalDate.of(1492, 10, 12) },
            { Symmetry454Date.of(1492, 10, 12),  LocalDate.of(1492, 10, 14) },

            // Galileo Galilei, astronomer and physicist (b. 1564)
            { Symmetry454Date.of(1564, 2, 20),   LocalDate.of(1564, 2, 15) },
            { Symmetry454Date.of(1564, 2, 15),   LocalDate.of(1564, 2, 10) },

            // William Shakespeare baptised in Stratford-upon-Avon (b. 1564)
            { Symmetry454Date.of(1564, 4, 28),   LocalDate.of(1564, 4, 26) },
            { Symmetry454Date.of(1564, 4, 26),   LocalDate.of(1564, 4, 24) },

            // Sir Isaac Newton, physicist and mathematician (b. 1643)
            { Symmetry454Date.of(1643, 1, 7),    LocalDate.of(1643, 1, 4) },
            { Symmetry454Date.of(1643, 1, 4),    LocalDate.of(1643, 1, 1) },

            // Leonhard Euler, mathematician and physicist (b. 1707)
            { Symmetry454Date.of(1707, 4, 12),   LocalDate.of(1707, 4, 15) },
            { Symmetry454Date.of(1707, 4, 15),   LocalDate.of(1707, 4, 18) },

            // French Revolution: storming of the Bastille (1789-07-14)
            { Symmetry454Date.of(1789, 7, 16),   LocalDate.of(1789, 7, 14) },
            { Symmetry454Date.of(1789, 7, 14),   LocalDate.of(1789, 7, 12) },

            // Albert Einstein, theoretical physicist (b. 1879)
            { Symmetry454Date.of(1879, 3, 12),   LocalDate.of(1879, 3, 14) },
            { Symmetry454Date.of(1879, 3, 14),   LocalDate.of(1879, 3, 16) },

            // Dennis Ritchie, computer scientist and creator of C (b. 1941)
            { Symmetry454Date.of(1941, 9, 9),    LocalDate.of(1941, 9, 9) },

            // Unix epoch: 1970-01-01 00:00:00 UTC
            { Symmetry454Date.of(1970, 1, 4),    LocalDate.of(1970, 1, 1) },
            { Symmetry454Date.of(1970, 1, 1),    LocalDate.of(1969, 12, 29) },

            // Start of the 21st century / 3rd millennium (2000-01-01)
            { Symmetry454Date.of(1999, 12, 27),  LocalDate.of(2000, 1, 1) },
            { Symmetry454Date.of(2000, 1, 1),    LocalDate.of(2000, 1, 3) },
        };
    }

    @ParameterizedTest
    @MethodSource("data_samples")
    public void test_Symmetry454Date_toEpochDay(Symmetry454Date sym454, LocalDate iso) {
        assertEquals(iso.toEpochDay(), sym454.toEpochDay());
    }
}
