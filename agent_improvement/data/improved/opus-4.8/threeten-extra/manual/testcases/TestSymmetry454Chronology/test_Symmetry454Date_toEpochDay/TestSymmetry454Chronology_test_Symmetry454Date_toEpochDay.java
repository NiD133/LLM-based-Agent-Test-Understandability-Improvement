package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Verifies that {@link Symmetry454Date#toEpochDay()} agrees with the ISO calendar.
 *
 * <p>Each sample pairs a {@link Symmetry454Date} with the {@link LocalDate} that
 * denotes the very same calendar day. Two dates that fall on the same day must
 * share the same epoch-day value, so the test simply compares the two epoch days.
 */
@SuppressWarnings({ "static-method" })
public class TestSymmetry454Chronology_test_Symmetry454Date_toEpochDay {

    /**
     * Sym454-date / equivalent ISO-date pairs, anchored to well-known historical
     * dates so the conversions are easy to spot-check.
     */
    public static Object[][] data_samples() {
        return new Object[][] {
            // A Sym454 date paired with the ISO LocalDate for the identical day.
            { Symmetry454Date.of(1, 1, 1),       LocalDate.of(1, 1, 1) },        // start of the proleptic calendar
            { Symmetry454Date.of(272, 2, 30),    LocalDate.of(272, 2, 27) },     // Constantine the Great, Roman emperor (d. 337)
            { Symmetry454Date.of(272, 2, 27),    LocalDate.of(272, 2, 24) },
            { Symmetry454Date.of(742, 3, 25),    LocalDate.of(742, 4, 2) },      // Charlemagne, Frankish king (d. 814)
            { Symmetry454Date.of(742, 4, 2),     LocalDate.of(742, 4, 7) },
            { Symmetry454Date.of(1066, 10, 14),  LocalDate.of(1066, 10, 14) },   // Norman Conquest: Battle of Hastings
            { Symmetry454Date.of(1304, 7, 21),   LocalDate.of(1304, 7, 20) },    // Francesco Petrarca - Petrarch (d. 1374)
            { Symmetry454Date.of(1304, 7, 20),   LocalDate.of(1304, 7, 19) },
            { Symmetry454Date.of(1433, 11, 14),  LocalDate.of(1433, 11, 10) },   // Charles the Bold of Burgundy (d. 1477)
            { Symmetry454Date.of(1433, 11, 10),  LocalDate.of(1433, 11, 6) },
            { Symmetry454Date.of(1452, 4, 11),   LocalDate.of(1452, 4, 15) },    // Leonardo da Vinci, Italian polymath (d. 1519)
            { Symmetry454Date.of(1452, 4, 15),   LocalDate.of(1452, 4, 19) },
            { Symmetry454Date.of(1492, 10, 10),  LocalDate.of(1492, 10, 12) },   // Columbus makes landfall in the Caribbean
            { Symmetry454Date.of(1492, 10, 12),  LocalDate.of(1492, 10, 14) },
            { Symmetry454Date.of(1564, 2, 20),   LocalDate.of(1564, 2, 15) },    // Galileo Galilei, astronomer (d. 1642)
            { Symmetry454Date.of(1564, 2, 15),   LocalDate.of(1564, 2, 10) },
            { Symmetry454Date.of(1564, 4, 28),   LocalDate.of(1564, 4, 26) },    // William Shakespeare baptized (d. 1616)
            { Symmetry454Date.of(1564, 4, 26),   LocalDate.of(1564, 4, 24) },
            { Symmetry454Date.of(1643, 1, 7),    LocalDate.of(1643, 1, 4) },     // Sir Isaac Newton, physicist (d. 1727)
            { Symmetry454Date.of(1643, 1, 4),    LocalDate.of(1643, 1, 1) },
            { Symmetry454Date.of(1707, 4, 12),   LocalDate.of(1707, 4, 15) },    // Leonhard Euler, mathematician (d. 1783)
            { Symmetry454Date.of(1707, 4, 15),   LocalDate.of(1707, 4, 18) },
            { Symmetry454Date.of(1789, 7, 16),   LocalDate.of(1789, 7, 14) },    // French Revolution: storming of the Bastille
            { Symmetry454Date.of(1789, 7, 14),   LocalDate.of(1789, 7, 12) },
            { Symmetry454Date.of(1879, 3, 12),   LocalDate.of(1879, 3, 14) },    // Albert Einstein, theoretical physicist (d. 1955)
            { Symmetry454Date.of(1879, 3, 14),   LocalDate.of(1879, 3, 16) },
            { Symmetry454Date.of(1941, 9, 9),    LocalDate.of(1941, 9, 9) },     // Dennis Ritchie, computer scientist (d. 2011)
            { Symmetry454Date.of(1970, 1, 4),    LocalDate.of(1970, 1, 1) },     // Unix epoch begins at 00:00:00 UTC
            { Symmetry454Date.of(1970, 1, 1),    LocalDate.of(1969, 12, 29) },
            { Symmetry454Date.of(1999, 12, 27),  LocalDate.of(2000, 1, 1) },     // start of the 21st century / 3rd millennium
            { Symmetry454Date.of(2000, 1, 1),    LocalDate.of(2000, 1, 3) },
        };
    }

    @ParameterizedTest
    @MethodSource("data_samples")
    public void test_Symmetry454Date_toEpochDay(Symmetry454Date sym454, LocalDate iso) {
        // The same calendar day yields the same epoch day, regardless of calendar system.
        assertEquals(iso.toEpochDay(), sym454.toEpochDay());
    }
}
