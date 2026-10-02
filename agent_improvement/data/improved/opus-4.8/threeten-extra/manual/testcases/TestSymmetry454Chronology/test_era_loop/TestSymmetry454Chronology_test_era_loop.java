package org.threeten.extra.chrono;

import static java.time.temporal.ChronoField.YEAR;
import static java.time.temporal.ChronoField.YEAR_OF_ERA;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.chrono.IsoEra;

import org.junit.jupiter.api.Test;

/**
 * Verifies how {@link Symmetry454Chronology} maps proleptic years onto eras.
 *
 * <p>The Symmetry454 calendar uses the ISO era model:
 * <ul>
 *   <li>years {@code >= 1} belong to {@link IsoEra#CE}, where the year-of-era equals the proleptic year;</li>
 *   <li>years {@code <= 0} belong to {@link IsoEra#BCE}, where the year-of-era equals {@code 1 - prolepticYear}.</li>
 * </ul>
 *
 * <p>It also confirms that building a date from an (era, year-of-era) pair yields the same date
 * as building it directly from the proleptic year.
 */
@SuppressWarnings({ "static-method" })
public class TestSymmetry454Chronology_test_era_loop {

    private static final Symmetry454Chronology CHRONOLOGY = Symmetry454Chronology.INSTANCE;

    @Test
    public void test_era_loop() {
        // CE era: proleptic years 1..199 map directly onto the year-of-era.
        for (int prolepticYear = 1; prolepticYear < 200; prolepticYear++) {
            Symmetry454Date dateByProlepticYear = CHRONOLOGY.date(prolepticYear, 1, 1);

            assertEquals(prolepticYear, dateByProlepticYear.get(YEAR));
            assertEquals(IsoEra.CE, dateByProlepticYear.getEra());
            assertEquals(prolepticYear, dateByProlepticYear.get(YEAR_OF_ERA));

            Symmetry454Date dateByEra = CHRONOLOGY.date(IsoEra.CE, prolepticYear, 1, 1);
            assertEquals(dateByProlepticYear, dateByEra);
        }

        // BCE era: proleptic years -200..-1 map onto year-of-era = 1 - prolepticYear.
        for (int prolepticYear = -200; prolepticYear < 0; prolepticYear++) {
            Symmetry454Date dateByProlepticYear = CHRONOLOGY.date(prolepticYear, 1, 1);

            assertEquals(prolepticYear, dateByProlepticYear.get(YEAR));
            assertEquals(IsoEra.BCE, dateByProlepticYear.getEra());
            assertEquals(1 - prolepticYear, dateByProlepticYear.get(YEAR_OF_ERA));

            Symmetry454Date dateByEra = CHRONOLOGY.date(IsoEra.BCE, prolepticYear, 1, 1);
            assertEquals(dateByProlepticYear, dateByEra);
        }
    }
}
