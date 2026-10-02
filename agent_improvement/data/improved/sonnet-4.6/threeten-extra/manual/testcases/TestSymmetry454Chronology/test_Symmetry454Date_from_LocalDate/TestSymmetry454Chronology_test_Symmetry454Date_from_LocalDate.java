package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

@SuppressWarnings({ "static-method" })
public class TestSymmetry454Chronology_test_Symmetry454Date_from_LocalDate {

    /**
     * Pairs of (expected Symmetry454Date, ISO LocalDate) covering historical dates
     * spread across the calendar to exercise the conversion from ISO to Sym454.
     */
    public static Object[][] data_samples() {
        return new Object[][] {
            // Year 1 epoch alignment
            { Symmetry454Date.of(1, 1, 1),       LocalDate.of(1, 1, 1) },

            // Year 272 — near dates of Constantine the Great (d. 337)
            { Symmetry454Date.of(272, 2, 30),    LocalDate.of(272, 2, 27) },
            { Symmetry454Date.of(272, 2, 27),    LocalDate.of(272, 2, 24) },

            // Year 742 — near dates of Charlemagne (d. 814)
            { Symmetry454Date.of(742, 3, 25),    LocalDate.of(742, 4, 2) },
            { Symmetry454Date.of(742, 4, 2),     LocalDate.of(742, 4, 7) },

            // 1066 — Battle of Hastings, Norman Conquest
            { Symmetry454Date.of(1066, 10, 14),  LocalDate.of(1066, 10, 14) },

            // 1304 — near dates of Petrarch, "Father of Humanism" (d. 1374)
            { Symmetry454Date.of(1304, 7, 21),   LocalDate.of(1304, 7, 20) },
            { Symmetry454Date.of(1304, 7, 20),   LocalDate.of(1304, 7, 19) },

            // 1433 — near dates of Charles the Bold (d. 1477)
            { Symmetry454Date.of(1433, 11, 14),  LocalDate.of(1433, 11, 10) },
            { Symmetry454Date.of(1433, 11, 10),  LocalDate.of(1433, 11, 6) },

            // 1452 — near dates of Leonardo da Vinci (d. 1519)
            { Symmetry454Date.of(1452, 4, 11),   LocalDate.of(1452, 4, 15) },
            { Symmetry454Date.of(1452, 4, 15),   LocalDate.of(1452, 4, 19) },

            // 1492 — Columbus makes landfall in the Caribbean
            { Symmetry454Date.of(1492, 10, 10),  LocalDate.of(1492, 10, 12) },
            { Symmetry454Date.of(1492, 10, 12),  LocalDate.of(1492, 10, 14) },

            // 1564 — near dates of Galileo Galilei (d. 1642)
            { Symmetry454Date.of(1564, 2, 20),   LocalDate.of(1564, 2, 15) },
            { Symmetry454Date.of(1564, 2, 15),   LocalDate.of(1564, 2, 10) },

            // 1564 — Shakespeare baptized in Stratford-upon-Avon (d. 1616)
            { Symmetry454Date.of(1564, 4, 28),   LocalDate.of(1564, 4, 26) },
            { Symmetry454Date.of(1564, 4, 26),   LocalDate.of(1564, 4, 24) },

            // 1643 — near dates of Sir Isaac Newton (d. 1727)
            { Symmetry454Date.of(1643, 1, 7),    LocalDate.of(1643, 1, 4) },
            { Symmetry454Date.of(1643, 1, 4),    LocalDate.of(1643, 1, 1) },

            // 1707 — near dates of Leonhard Euler (d. 1783)
            { Symmetry454Date.of(1707, 4, 12),   LocalDate.of(1707, 4, 15) },
            { Symmetry454Date.of(1707, 4, 15),   LocalDate.of(1707, 4, 18) },

            // 1789 — Storming of the Bastille, French Revolution
            { Symmetry454Date.of(1789, 7, 16),   LocalDate.of(1789, 7, 14) },
            { Symmetry454Date.of(1789, 7, 14),   LocalDate.of(1789, 7, 12) },

            // 1879 — near dates of Albert Einstein (d. 1955)
            { Symmetry454Date.of(1879, 3, 12),   LocalDate.of(1879, 3, 14) },
            { Symmetry454Date.of(1879, 3, 14),   LocalDate.of(1879, 3, 16) },

            // 1941 — Dennis Ritchie, American computer scientist (d. 2011)
            { Symmetry454Date.of(1941, 9, 9),    LocalDate.of(1941, 9, 9) },

            // 1970 — Unix epoch (1970-01-01 ISO)
            { Symmetry454Date.of(1970, 1, 4),    LocalDate.of(1970, 1, 1) },
            { Symmetry454Date.of(1970, 1, 1),    LocalDate.of(1969, 12, 29) },

            // 1999/2000 — start of the 21st century
            { Symmetry454Date.of(1999, 12, 27),  LocalDate.of(2000, 1, 1) },
            { Symmetry454Date.of(2000, 1, 1),    LocalDate.of(2000, 1, 3) },
        };
    }

    @ParameterizedTest
    @MethodSource("data_samples")
    public void test_Symmetry454Date_from_LocalDate(Symmetry454Date sym454, LocalDate iso) {
        assertEquals(sym454, Symmetry454Date.from(iso));
    }
}
