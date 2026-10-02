package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.chrono.ChronoPeriod;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Verifies that {@link Symmetry454Date#until(java.time.temporal.Temporal)} returns a zero-length
 * {@link ChronoPeriod} when the end date is an ISO {@link LocalDate} that represents the same
 * historical moment as the starting {@link Symmetry454Date}.
 *
 * <p>The {@code until} method internally converts the ISO date into the Symmetry454 calendar
 * system before computing the difference. When both calendar representations point to the
 * same real-world date the resulting period is (0 years, 0 months, 0 days).
 */
@SuppressWarnings({"static-method"})
public class TestSymmetry454Chronology_test_Symmetry454Date_until_LocalDate {

    /**
     * Pairs of (Symmetry454Date, ISO LocalDate) where each pair denotes the same historical date
     * expressed in two different calendar systems.
     */
    public static Object[][] data_samples() {
        return new Object[][] {
            // Constantine the Great, Roman emperor (d. 337)
            { Symmetry454Date.of(1,    1,  1),  LocalDate.of(1,    1,  1) },
            { Symmetry454Date.of(272,  2, 30),  LocalDate.of(272,  2, 27) },
            { Symmetry454Date.of(272,  2, 27),  LocalDate.of(272,  2, 24) },
            // Charlemagne, Frankish king (d. 814)
            { Symmetry454Date.of(742,  3, 25),  LocalDate.of(742,  4,  2) },
            { Symmetry454Date.of(742,  4,  2),  LocalDate.of(742,  4,  7) },
            // Norman Conquest: Battle of Hastings
            { Symmetry454Date.of(1066, 10, 14), LocalDate.of(1066, 10, 14) },
            // Francesco Petrarca - Petrarch, Italian scholar and poet (d. 1374)
            { Symmetry454Date.of(1304,  7, 21), LocalDate.of(1304,  7, 20) },
            { Symmetry454Date.of(1304,  7, 20), LocalDate.of(1304,  7, 19) },
            // Charles the Bold, Duke of Burgundy (d. 1477)
            { Symmetry454Date.of(1433, 11, 14), LocalDate.of(1433, 11, 10) },
            { Symmetry454Date.of(1433, 11, 10), LocalDate.of(1433, 11,  6) },
            // Leonardo da Vinci, Italian painter, sculptor, and architect (d. 1519)
            { Symmetry454Date.of(1452,  4, 11), LocalDate.of(1452,  4, 15) },
            { Symmetry454Date.of(1452,  4, 15), LocalDate.of(1452,  4, 19) },
            // Christopher Columbus's expedition makes landfall in the Caribbean
            { Symmetry454Date.of(1492, 10, 10), LocalDate.of(1492, 10, 12) },
            { Symmetry454Date.of(1492, 10, 12), LocalDate.of(1492, 10, 14) },
            // Galileo Galilei, Italian astronomer and physicist (d. 1642)
            { Symmetry454Date.of(1564,  2, 20), LocalDate.of(1564,  2, 15) },
            { Symmetry454Date.of(1564,  2, 15), LocalDate.of(1564,  2, 10) },
            // William Shakespeare is baptized in Stratford-upon-Avon (d. 1616)
            { Symmetry454Date.of(1564,  4, 28), LocalDate.of(1564,  4, 26) },
            { Symmetry454Date.of(1564,  4, 26), LocalDate.of(1564,  4, 24) },
            // Sir Isaac Newton, English physicist and mathematician (d. 1727)
            { Symmetry454Date.of(1643,  1,  7), LocalDate.of(1643,  1,  4) },
            { Symmetry454Date.of(1643,  1,  4), LocalDate.of(1643,  1,  1) },
            // Leonhard Euler, Swiss mathematician and physicist (d. 1783)
            { Symmetry454Date.of(1707,  4, 12), LocalDate.of(1707,  4, 15) },
            { Symmetry454Date.of(1707,  4, 15), LocalDate.of(1707,  4, 18) },
            // French Revolution: Citizens of Paris storm the Bastille
            { Symmetry454Date.of(1789,  7, 16), LocalDate.of(1789,  7, 14) },
            { Symmetry454Date.of(1789,  7, 14), LocalDate.of(1789,  7, 12) },
            // Albert Einstein, German theoretical physicist (d. 1955)
            { Symmetry454Date.of(1879,  3, 12), LocalDate.of(1879,  3, 14) },
            { Symmetry454Date.of(1879,  3, 14), LocalDate.of(1879,  3, 16) },
            // Dennis MacAlistair Ritchie, American computer scientist (d. 2011)
            { Symmetry454Date.of(1941,  9,  9), LocalDate.of(1941,  9,  9) },
            // Unix time begins at 00:00:00 UTC/GMT
            { Symmetry454Date.of(1970,  1,  4), LocalDate.of(1970,  1,  1) },
            { Symmetry454Date.of(1970,  1,  1), LocalDate.of(1969, 12, 29) },
            // Start of the 21st century / 3rd millennium
            { Symmetry454Date.of(1999, 12, 27), LocalDate.of(2000,  1,  1) },
            { Symmetry454Date.of(2000,  1,  1), LocalDate.of(2000,  1,  3) },
        };
    }

    /**
     * Asserts that the period from a Symmetry454Date until the corresponding ISO LocalDate is zero.
     *
     * <p>Internally {@code until} converts {@code iso} into the Symmetry454 calendar system,
     * so when both dates denote the same real-world point in time the computed period is empty.
     */
    @ParameterizedTest
    @MethodSource("data_samples")
    public void test_Symmetry454Date_until_LocalDate(Symmetry454Date sym454, LocalDate iso) {
        ChronoPeriod zeroPeriod = Symmetry454Chronology.INSTANCE.period(0, 0, 0);
        assertEquals(zeroPeriod, sym454.until(iso));
    }
}
