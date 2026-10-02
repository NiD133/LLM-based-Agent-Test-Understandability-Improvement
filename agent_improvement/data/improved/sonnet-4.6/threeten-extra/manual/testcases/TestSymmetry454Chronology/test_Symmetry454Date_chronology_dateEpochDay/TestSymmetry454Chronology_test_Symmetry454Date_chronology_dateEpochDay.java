package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Tests that {@link Symmetry454Chronology#dateEpochDay(long)} correctly reconstructs
 * a {@link Symmetry454Date} from the ISO epoch day of a paired {@link LocalDate}.
 *
 * <p>Each data row pairs a Symmetry454 date with its ISO calendar equivalent.
 * The test converts the ISO date to an epoch day and verifies the chronology
 * produces the matching Symmetry454 date.
 */
@SuppressWarnings("static-method")
public class TestSymmetry454Chronology_test_Symmetry454Date_chronology_dateEpochDay {

    /**
     * Pairs of (Symmetry454Date, equivalent ISO LocalDate) used to verify round-trip
     * conversion through epoch day.
     */
    public static Object[][] data_samples() {
        return new Object[][] {
            // CE 1 Jan 1 — start of the proleptic Symmetry454 calendar
            { Symmetry454Date.of(1, 1, 1),        LocalDate.of(1, 1, 1)    },
            // Constantine the Great, Roman emperor (d. 337)
            { Symmetry454Date.of(272, 2, 30),     LocalDate.of(272, 2, 27) },
            { Symmetry454Date.of(272, 2, 27),     LocalDate.of(272, 2, 24) },
            // Charlemagne, Frankish king (d. 814)
            { Symmetry454Date.of(742, 3, 25),     LocalDate.of(742, 4, 2)  },
            { Symmetry454Date.of(742, 4, 2),      LocalDate.of(742, 4, 7)  },
            // Battle of Hastings — Norman Conquest
            { Symmetry454Date.of(1066, 10, 14),   LocalDate.of(1066, 10, 14) },
            // Francesco Petrarca (Petrarch), Father of Humanism (d. 1374)
            { Symmetry454Date.of(1304, 7, 21),    LocalDate.of(1304, 7, 20) },
            { Symmetry454Date.of(1304, 7, 20),    LocalDate.of(1304, 7, 19) },
            // Charles the Bold, Duke of Burgundy (d. 1477)
            { Symmetry454Date.of(1433, 11, 14),   LocalDate.of(1433, 11, 10) },
            { Symmetry454Date.of(1433, 11, 10),   LocalDate.of(1433, 11, 6)  },
            // Leonardo da Vinci, painter and polymath (d. 1519)
            { Symmetry454Date.of(1452, 4, 11),    LocalDate.of(1452, 4, 15) },
            { Symmetry454Date.of(1452, 4, 15),    LocalDate.of(1452, 4, 19) },
            // Columbus makes landfall in the Caribbean
            { Symmetry454Date.of(1492, 10, 10),   LocalDate.of(1492, 10, 12) },
            { Symmetry454Date.of(1492, 10, 12),   LocalDate.of(1492, 10, 14) },
            // Galileo Galilei, astronomer and physicist (d. 1642)
            { Symmetry454Date.of(1564, 2, 20),    LocalDate.of(1564, 2, 15) },
            { Symmetry454Date.of(1564, 2, 15),    LocalDate.of(1564, 2, 10) },
            // William Shakespeare baptized in Stratford-upon-Avon (d. 1616)
            { Symmetry454Date.of(1564, 4, 28),    LocalDate.of(1564, 4, 26) },
            { Symmetry454Date.of(1564, 4, 26),    LocalDate.of(1564, 4, 24) },
            // Sir Isaac Newton, physicist and mathematician (d. 1727)
            { Symmetry454Date.of(1643, 1, 7),     LocalDate.of(1643, 1, 4) },
            { Symmetry454Date.of(1643, 1, 4),     LocalDate.of(1643, 1, 1) },
            // Leonhard Euler, mathematician and physicist (d. 1783)
            { Symmetry454Date.of(1707, 4, 12),    LocalDate.of(1707, 4, 15) },
            { Symmetry454Date.of(1707, 4, 15),    LocalDate.of(1707, 4, 18) },
            // French Revolution — storming of the Bastille
            { Symmetry454Date.of(1789, 7, 16),    LocalDate.of(1789, 7, 14) },
            { Symmetry454Date.of(1789, 7, 14),    LocalDate.of(1789, 7, 12) },
            // Albert Einstein, theoretical physicist (d. 1955)
            { Symmetry454Date.of(1879, 3, 12),    LocalDate.of(1879, 3, 14) },
            { Symmetry454Date.of(1879, 3, 14),    LocalDate.of(1879, 3, 16) },
            // Dennis Ritchie, computer scientist and creator of C (d. 2011)
            { Symmetry454Date.of(1941, 9, 9),     LocalDate.of(1941, 9, 9)  },
            // Unix epoch begins (1970-01-01 ISO)
            { Symmetry454Date.of(1970, 1, 4),     LocalDate.of(1970, 1, 1)  },
            { Symmetry454Date.of(1970, 1, 1),     LocalDate.of(1969, 12, 29) },
            // Start of the 21st century / 3rd millennium
            { Symmetry454Date.of(1999, 12, 27),   LocalDate.of(2000, 1, 1)  },
            { Symmetry454Date.of(2000, 1, 1),     LocalDate.of(2000, 1, 3)  },
        };
    }

    /**
     * Verifies that converting an ISO {@link LocalDate} to an epoch day and then
     * calling {@link Symmetry454Chronology#dateEpochDay(long)} yields the expected
     * {@link Symmetry454Date}.
     */
    @ParameterizedTest
    @MethodSource("data_samples")
    public void test_Symmetry454Date_chronology_dateEpochDay(Symmetry454Date sym454, LocalDate iso) {
        assertEquals(sym454, Symmetry454Chronology.INSTANCE.dateEpochDay(iso.toEpochDay()));
    }
}
