package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Verifies that {@link Symmetry454Chronology#date(java.time.temporal.TemporalAccessor)}
 * converts an ISO {@link LocalDate} into the equivalent {@link Symmetry454Date}.
 */
@SuppressWarnings({ "static-method" })
public class TestSymmetry454Chronology_test_Chronology_date_Temporal {

    /**
     * Each case pairs a Symmetry454 date with the ISO date that denotes the same day.
     * The dates are anchored to well-known historical events for readability.
     */
    public static Stream<Arguments> data_samples() {
        return Stream.of(
            sample(Symmetry454Date.of(1, 1, 1),        LocalDate.of(1, 1, 1)),        // Reign of Constantine the Great begins
            sample(Symmetry454Date.of(272, 2, 30),     LocalDate.of(272, 2, 27)),     // Constantine the Great, Roman emperor (d. 337)
            sample(Symmetry454Date.of(272, 2, 27),     LocalDate.of(272, 2, 24)),
            sample(Symmetry454Date.of(742, 3, 25),     LocalDate.of(742, 4, 2)),      // Charlemagne, Frankish king (d. 814)
            sample(Symmetry454Date.of(742, 4, 2),      LocalDate.of(742, 4, 7)),
            sample(Symmetry454Date.of(1066, 10, 14),   LocalDate.of(1066, 10, 14)),   // Norman Conquest: Battle of Hastings
            sample(Symmetry454Date.of(1304, 7, 21),    LocalDate.of(1304, 7, 20)),    // Francesco Petrarca - Petrarch, "Father of Humanism" (d. 1374)
            sample(Symmetry454Date.of(1304, 7, 20),    LocalDate.of(1304, 7, 19)),
            sample(Symmetry454Date.of(1433, 11, 14),   LocalDate.of(1433, 11, 10)),   // Charles the Bold, Duke of Burgundy (d. 1477)
            sample(Symmetry454Date.of(1433, 11, 10),   LocalDate.of(1433, 11, 6)),
            sample(Symmetry454Date.of(1452, 4, 11),    LocalDate.of(1452, 4, 15)),    // Leonardo da Vinci, Italian polymath (d. 1519)
            sample(Symmetry454Date.of(1452, 4, 15),    LocalDate.of(1452, 4, 19)),
            sample(Symmetry454Date.of(1492, 10, 10),   LocalDate.of(1492, 10, 12)),   // Columbus's expedition makes landfall in the Caribbean
            sample(Symmetry454Date.of(1492, 10, 12),   LocalDate.of(1492, 10, 14)),
            sample(Symmetry454Date.of(1564, 2, 20),    LocalDate.of(1564, 2, 15)),    // Galileo Galilei, astronomer and physicist (d. 1642)
            sample(Symmetry454Date.of(1564, 2, 15),    LocalDate.of(1564, 2, 10)),
            sample(Symmetry454Date.of(1564, 4, 28),    LocalDate.of(1564, 4, 26)),    // William Shakespeare is baptized (d. 1616)
            sample(Symmetry454Date.of(1564, 4, 26),    LocalDate.of(1564, 4, 24)),
            sample(Symmetry454Date.of(1643, 1, 7),     LocalDate.of(1643, 1, 4)),     // Sir Isaac Newton, physicist and mathematician (d. 1727)
            sample(Symmetry454Date.of(1643, 1, 4),     LocalDate.of(1643, 1, 1)),
            sample(Symmetry454Date.of(1707, 4, 12),    LocalDate.of(1707, 4, 15)),    // Leonhard Euler, Swiss mathematician (d. 1783)
            sample(Symmetry454Date.of(1707, 4, 15),    LocalDate.of(1707, 4, 18)),
            sample(Symmetry454Date.of(1789, 7, 16),    LocalDate.of(1789, 7, 14)),    // French Revolution: storming of the Bastille
            sample(Symmetry454Date.of(1789, 7, 14),    LocalDate.of(1789, 7, 12)),
            sample(Symmetry454Date.of(1879, 3, 12),    LocalDate.of(1879, 3, 14)),    // Albert Einstein, theoretical physicist (d. 1955)
            sample(Symmetry454Date.of(1879, 3, 14),    LocalDate.of(1879, 3, 16)),
            sample(Symmetry454Date.of(1941, 9, 9),     LocalDate.of(1941, 9, 9)),     // Dennis Ritchie, American computer scientist (d. 2011)
            sample(Symmetry454Date.of(1970, 1, 4),     LocalDate.of(1970, 1, 1)),     // Unix time begins at 00:00:00 UTC
            sample(Symmetry454Date.of(1970, 1, 1),     LocalDate.of(1969, 12, 29)),
            sample(Symmetry454Date.of(1999, 12, 27),   LocalDate.of(2000, 1, 1)),     // Start of the 3rd millennium
            sample(Symmetry454Date.of(2000, 1, 1),     LocalDate.of(2000, 1, 3))
        );
    }

    private static Arguments sample(Symmetry454Date sym454, LocalDate iso) {
        return Arguments.of(sym454, iso);
    }

    @ParameterizedTest
    @MethodSource("data_samples")
    public void test_Chronology_date_Temporal(Symmetry454Date sym454, LocalDate iso) {
        assertEquals(sym454, Symmetry454Chronology.INSTANCE.date(iso));
    }
}
