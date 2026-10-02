package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Tests that {@link Symmetry010Chronology#dateEpochDay(long)} correctly reconstructs
 * a Symmetry010Date from the ISO epoch-day value of a paired ISO LocalDate.
 *
 * <p>Each test case is a (sym010Date, isoDate) pair where the ISO epoch-day of
 * {@code isoDate} should round-trip back to {@code sym010Date}.
 */
@SuppressWarnings("static-method")
public class TestSymmetry010Chronology_test_Symmetry010Date_chronology_dateEpochDay {

    /**
     * Pairs of (Symmetry010Date, ISO LocalDate) anchored to well-known historical dates.
     * The Symmetry010 calendar and the ISO calendar use different day-numbering, so the
     * dates in each pair are not necessarily the same calendar date — they share the same
     * underlying epoch-day count.
     */
    public static Object[][] data_samples() {
        return new Object[][] {
            // Constantine the Great, Roman emperor (d. 337)
            { Symmetry010Date.of(1,    1,  1),  LocalDate.of(1,    1,  1)  },
            // Proximates around the birth of Constantine
            { Symmetry010Date.of(272,  2, 28),  LocalDate.of(272,  2, 27)  },
            { Symmetry010Date.of(272,  2, 27),  LocalDate.of(272,  2, 26)  },
            // Charlemagne, Frankish king (d. 814)
            { Symmetry010Date.of(742,  3, 27),  LocalDate.of(742,  4,  2)  },
            { Symmetry010Date.of(742,  4,  2),  LocalDate.of(742,  4,  7)  },
            // Norman Conquest: Battle of Hastings
            { Symmetry010Date.of(1066, 10, 14), LocalDate.of(1066, 10, 14) },
            // Francesco Petrarca — "Father of Humanism" (d. 1374)
            { Symmetry010Date.of(1304,  7, 21), LocalDate.of(1304,  7, 20) },
            { Symmetry010Date.of(1304,  7, 20), LocalDate.of(1304,  7, 19) },
            // Charles the Bold, Duke of Burgundy (d. 1477)
            { Symmetry010Date.of(1433, 11, 12), LocalDate.of(1433, 11, 10) },
            { Symmetry010Date.of(1433, 11, 10), LocalDate.of(1433, 11,  8) },
            // Leonardo da Vinci (d. 1519)
            { Symmetry010Date.of(1452,  4, 11), LocalDate.of(1452,  4, 15) },
            { Symmetry010Date.of(1452,  4, 15), LocalDate.of(1452,  4, 19) },
            // Columbus makes landfall in the Caribbean
            { Symmetry010Date.of(1492, 10, 10), LocalDate.of(1492, 10, 12) },
            { Symmetry010Date.of(1492, 10, 12), LocalDate.of(1492, 10, 14) },
            // Galileo Galilei (d. 1642)
            { Symmetry010Date.of(1564,  2, 18), LocalDate.of(1564,  2, 15) },
            { Symmetry010Date.of(1564,  2, 15), LocalDate.of(1564,  2, 12) },
            // William Shakespeare baptised in Stratford-upon-Avon (d. 1616)
            { Symmetry010Date.of(1564,  4, 28), LocalDate.of(1564,  4, 26) },
            { Symmetry010Date.of(1564,  4, 26), LocalDate.of(1564,  4, 24) },
            // Sir Isaac Newton (d. 1727)
            { Symmetry010Date.of(1643,  1,  7), LocalDate.of(1643,  1,  4) },
            { Symmetry010Date.of(1643,  1,  4), LocalDate.of(1643,  1,  1) },
            // Leonhard Euler (d. 1783)
            { Symmetry010Date.of(1707,  4, 12), LocalDate.of(1707,  4, 15) },
            { Symmetry010Date.of(1707,  4, 15), LocalDate.of(1707,  4, 18) },
            // French Revolution: storming of the Bastille
            { Symmetry010Date.of(1789,  7, 16), LocalDate.of(1789,  7, 14) },
            { Symmetry010Date.of(1789,  7, 14), LocalDate.of(1789,  7, 12) },
            // Albert Einstein (d. 1955)
            { Symmetry010Date.of(1879,  3, 14), LocalDate.of(1879,  3, 14) },
            // Dennis Ritchie, creator of C and co-creator of Unix (d. 2011)
            { Symmetry010Date.of(1941,  9, 11), LocalDate.of(1941,  9,  9) },
            { Symmetry010Date.of(1941,  9,  9), LocalDate.of(1941,  9,  7) },
            // Unix epoch: 1970-01-01 ISO maps to Symmetry010 1970/01/04
            { Symmetry010Date.of(1970,  1,  4), LocalDate.of(1970,  1,  1) },
            { Symmetry010Date.of(1970,  1,  1), LocalDate.of(1969, 12, 29) },
            // Start of the 21st century / 3rd millennium
            { Symmetry010Date.of(1999, 12, 29), LocalDate.of(2000,  1,  1) },
            { Symmetry010Date.of(2000,  1,  1), LocalDate.of(2000,  1,  3) },
        };
    }

    /**
     * Verifies that converting an ISO {@link LocalDate} to its epoch-day and then calling
     * {@link Symmetry010Chronology#dateEpochDay(long)} produces the expected
     * {@link Symmetry010Date}.
     *
     * <p>This confirms that the chronology's epoch-day conversion is consistent with the
     * known mapping between ISO dates and Symmetry010 dates.
     */
    @ParameterizedTest
    @MethodSource("data_samples")
    public void test_Symmetry010Date_chronology_dateEpochDay(Symmetry010Date sym010, LocalDate iso) {
        assertEquals(sym010, Symmetry010Chronology.INSTANCE.dateEpochDay(iso.toEpochDay()));
    }
}
