package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Tests that {@link Symmetry010Chronology#date(java.time.temporal.TemporalAccessor)}
 * converts an ISO {@link LocalDate} into the equivalent {@link Symmetry010Date}.
 */
@SuppressWarnings({ "static-method" })
public class TestSymmetry010Chronology_test_Chronology_date_Temporal {

    /**
     * Pairs of equivalent dates: each ISO {@link LocalDate} and the
     * {@link Symmetry010Date} it should convert to. The comments mark the
     * historical events the sample years were chosen to represent.
     */
    public static Stream<Arguments> equivalentDatePairs() {
        return Stream.of(
                // Constantine the Great, Roman emperor (d. 337)
                Arguments.of(Symmetry010Date.of(1, 1, 1), LocalDate.of(1, 1, 1)),
                Arguments.of(Symmetry010Date.of(272, 2, 28), LocalDate.of(272, 2, 27)),
                Arguments.of(Symmetry010Date.of(272, 2, 27), LocalDate.of(272, 2, 26)),
                // Charlemagne, Frankish king (d. 814)
                Arguments.of(Symmetry010Date.of(742, 3, 27), LocalDate.of(742, 4, 2)),
                Arguments.of(Symmetry010Date.of(742, 4, 2), LocalDate.of(742, 4, 7)),
                // Norman Conquest: Battle of Hastings
                Arguments.of(Symmetry010Date.of(1066, 10, 14), LocalDate.of(1066, 10, 14)),
                // Francesco Petrarca - Petrarch, Italian scholar and poet, "Father of Humanism" (d. 1374)
                Arguments.of(Symmetry010Date.of(1304, 7, 21), LocalDate.of(1304, 7, 20)),
                Arguments.of(Symmetry010Date.of(1304, 7, 20), LocalDate.of(1304, 7, 19)),
                // Charles the Bold, French son of Isabella of Portugal, Duchess of Burgundy (d. 1477)
                Arguments.of(Symmetry010Date.of(1433, 11, 12), LocalDate.of(1433, 11, 10)),
                Arguments.of(Symmetry010Date.of(1433, 11, 10), LocalDate.of(1433, 11, 8)),
                // Leonardo da Vinci, Italian painter, sculptor, and architect (d. 1519)
                Arguments.of(Symmetry010Date.of(1452, 4, 11), LocalDate.of(1452, 4, 15)),
                Arguments.of(Symmetry010Date.of(1452, 4, 15), LocalDate.of(1452, 4, 19)),
                // Christopher Columbus's expedition makes landfall in the Caribbean.
                Arguments.of(Symmetry010Date.of(1492, 10, 10), LocalDate.of(1492, 10, 12)),
                Arguments.of(Symmetry010Date.of(1492, 10, 12), LocalDate.of(1492, 10, 14)),
                // Galileo Galilei, Italian astronomer and physicist (d. 1642)
                Arguments.of(Symmetry010Date.of(1564, 2, 18), LocalDate.of(1564, 2, 15)),
                Arguments.of(Symmetry010Date.of(1564, 2, 15), LocalDate.of(1564, 2, 12)),
                // William Shakespeare is baptized in Stratford-upon-Avon, England (d. 1616).
                Arguments.of(Symmetry010Date.of(1564, 4, 28), LocalDate.of(1564, 4, 26)),
                Arguments.of(Symmetry010Date.of(1564, 4, 26), LocalDate.of(1564, 4, 24)),
                // Sir Isaac Newton, English physicist and mathematician (d. 1727)
                Arguments.of(Symmetry010Date.of(1643, 1, 7), LocalDate.of(1643, 1, 4)),
                Arguments.of(Symmetry010Date.of(1643, 1, 4), LocalDate.of(1643, 1, 1)),
                // Leonhard Euler, Swiss mathematician and physicist (d. 1783)
                Arguments.of(Symmetry010Date.of(1707, 4, 12), LocalDate.of(1707, 4, 15)),
                Arguments.of(Symmetry010Date.of(1707, 4, 15), LocalDate.of(1707, 4, 18)),
                // French Revolution: Citizens of Paris storm the Bastille.
                Arguments.of(Symmetry010Date.of(1789, 7, 16), LocalDate.of(1789, 7, 14)),
                Arguments.of(Symmetry010Date.of(1789, 7, 14), LocalDate.of(1789, 7, 12)),
                // Albert Einstein, German theoretical physicist (d. 1955)
                Arguments.of(Symmetry010Date.of(1879, 3, 14), LocalDate.of(1879, 3, 14)),
                // Dennis MacAlistair Ritchie, American computer scientist (d. 2011)
                Arguments.of(Symmetry010Date.of(1941, 9, 11), LocalDate.of(1941, 9, 9)),
                Arguments.of(Symmetry010Date.of(1941, 9, 9), LocalDate.of(1941, 9, 7)),
                // Unix time begins at 00:00:00 UTC/GMT.
                Arguments.of(Symmetry010Date.of(1970, 1, 4), LocalDate.of(1970, 1, 1)),
                Arguments.of(Symmetry010Date.of(1970, 1, 1), LocalDate.of(1969, 12, 29)),
                // Start of the 21st century / 3rd millennium
                Arguments.of(Symmetry010Date.of(1999, 12, 29), LocalDate.of(2000, 1, 1)),
                Arguments.of(Symmetry010Date.of(2000, 1, 1), LocalDate.of(2000, 1, 3)));
    }

    @ParameterizedTest
    @MethodSource("equivalentDatePairs")
    public void date_fromIsoLocalDate_returnsEquivalentSymmetry010Date(Symmetry010Date expectedSym010, LocalDate iso) {
        assertEquals(expectedSym010, Symmetry010Chronology.INSTANCE.date(iso));
    }
}
