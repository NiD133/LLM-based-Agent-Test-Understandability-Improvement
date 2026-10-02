package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Verifies that {@link LocalDate#from(java.time.temporal.TemporalAccessor)} correctly converts
 * a {@link Symmetry454Date} to its ISO {@link LocalDate} equivalent.
 */
@SuppressWarnings({ "static-method" })
public class TestSymmetry454Chronology_test_LocalDate_from_Symmetry454Date {

    /**
     * Pairs of (Symmetry454Date, expected ISO LocalDate) covering historically notable dates
     * across a wide range of years to exercise the conversion logic.
     */
    public static Object[][] data_samples() {
        return new Object[][] {
            // Year 1 — epoch anchor
            { Symmetry454Date.of(1, 1, 1),          LocalDate.of(1, 1, 1) },

            // Constantine the Great, Roman emperor (d. 337)
            { Symmetry454Date.of(272, 2, 30),        LocalDate.of(272, 2, 27) },
            { Symmetry454Date.of(272, 2, 27),        LocalDate.of(272, 2, 24) },

            // Charlemagne, Frankish king (d. 814)
            { Symmetry454Date.of(742, 3, 25),        LocalDate.of(742, 4, 2) },
            { Symmetry454Date.of(742, 4, 2),         LocalDate.of(742, 4, 7) },

            // Norman Conquest: Battle of Hastings
            { Symmetry454Date.of(1066, 10, 14),      LocalDate.of(1066, 10, 14) },

            // Francesco Petrarch, "Father of Humanism" (d. 1374)
            { Symmetry454Date.of(1304, 7, 21),       LocalDate.of(1304, 7, 20) },
            { Symmetry454Date.of(1304, 7, 20),       LocalDate.of(1304, 7, 19) },

            // Charles the Bold, Duke of Burgundy (d. 1477)
            { Symmetry454Date.of(1433, 11, 14),      LocalDate.of(1433, 11, 10) },
            { Symmetry454Date.of(1433, 11, 10),      LocalDate.of(1433, 11, 6) },

            // Leonardo da Vinci, Italian polymath (d. 1519)
            { Symmetry454Date.of(1452, 4, 11),       LocalDate.of(1452, 4, 15) },
            { Symmetry454Date.of(1452, 4, 15),       LocalDate.of(1452, 4, 19) },

            // Christopher Columbus makes landfall in the Caribbean
            { Symmetry454Date.of(1492, 10, 10),      LocalDate.of(1492, 10, 12) },
            { Symmetry454Date.of(1492, 10, 12),      LocalDate.of(1492, 10, 14) },

            // Galileo Galilei, astronomer and physicist (d. 1642)
            { Symmetry454Date.of(1564, 2, 20),       LocalDate.of(1564, 2, 15) },
            { Symmetry454Date.of(1564, 2, 15),       LocalDate.of(1564, 2, 10) },

            // William Shakespeare baptized in Stratford-upon-Avon (d. 1616)
            { Symmetry454Date.of(1564, 4, 28),       LocalDate.of(1564, 4, 26) },
            { Symmetry454Date.of(1564, 4, 26),       LocalDate.of(1564, 4, 24) },

            // Sir Isaac Newton, physicist and mathematician (d. 1727)
            { Symmetry454Date.of(1643, 1, 7),        LocalDate.of(1643, 1, 4) },
            { Symmetry454Date.of(1643, 1, 4),        LocalDate.of(1643, 1, 1) },

            // Leonhard Euler, mathematician and physicist (d. 1783)
            { Symmetry454Date.of(1707, 4, 12),       LocalDate.of(1707, 4, 15) },
            { Symmetry454Date.of(1707, 4, 15),       LocalDate.of(1707, 4, 18) },

            // French Revolution: storming of the Bastille
            { Symmetry454Date.of(1789, 7, 16),       LocalDate.of(1789, 7, 14) },
            { Symmetry454Date.of(1789, 7, 14),       LocalDate.of(1789, 7, 12) },

            // Albert Einstein, theoretical physicist (d. 1955)
            { Symmetry454Date.of(1879, 3, 12),       LocalDate.of(1879, 3, 14) },
            { Symmetry454Date.of(1879, 3, 14),       LocalDate.of(1879, 3, 16) },

            // Dennis Ritchie, computer scientist (d. 2011)
            { Symmetry454Date.of(1941, 9, 9),        LocalDate.of(1941, 9, 9) },

            // Unix epoch: 1970-01-01 00:00:00 UTC
            { Symmetry454Date.of(1970, 1, 4),        LocalDate.of(1970, 1, 1) },
            { Symmetry454Date.of(1970, 1, 1),        LocalDate.of(1969, 12, 29) },

            // Start of the 21st century / 3rd millennium
            { Symmetry454Date.of(1999, 12, 27),      LocalDate.of(2000, 1, 1) },
            { Symmetry454Date.of(2000, 1, 1),        LocalDate.of(2000, 1, 3) },
        };
    }

    @ParameterizedTest
    @MethodSource("data_samples")
    public void test_LocalDate_from_Symmetry454Date(Symmetry454Date sym454, LocalDate iso) {
        assertEquals(iso, LocalDate.from(sym454));
    }
}
