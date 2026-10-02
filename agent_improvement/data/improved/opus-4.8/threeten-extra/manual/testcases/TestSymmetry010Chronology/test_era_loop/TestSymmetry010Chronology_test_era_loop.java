package org.threeten.extra.chrono;

import static java.time.temporal.ChronoField.YEAR;
import static java.time.temporal.ChronoField.YEAR_OF_ERA;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.chrono.IsoEra;

import org.junit.jupiter.api.Test;

/**
 * Tests how {@link Symmetry010Chronology} maps proleptic years onto ISO eras.
 *
 * <p>The Symmetry010 calendar uses the ISO era model:
 * <ul>
 *   <li>positive proleptic years belong to era {@link IsoEra#CE}, where the
 *       year-of-era equals the proleptic year;</li>
 *   <li>non-positive proleptic years belong to era {@link IsoEra#BCE}, where the
 *       year-of-era equals {@code 1 - prolepticYear}.</li>
 * </ul>
 *
 * <p>For each era the test also confirms that building a date from
 * (era, year, month, day) yields the same date as building it from the
 * proleptic year directly.
 */
@SuppressWarnings({ "static-method" })
public class TestSymmetry010Chronology_test_era_loop {

    private static final Symmetry010Chronology CHRONO = Symmetry010Chronology.INSTANCE;

    @Test
    public void test_era_loop() {
        // Positive proleptic years map to the CE era, year-of-era == proleptic year.
        for (int prolepticYear = 1; prolepticYear < 200; prolepticYear++) {
            Symmetry010Date date = CHRONO.date(prolepticYear, 1, 1);

            assertEquals(prolepticYear, date.get(YEAR));
            assertEquals(IsoEra.CE, date.getEra());
            assertEquals(prolepticYear, date.get(YEAR_OF_ERA));

            Symmetry010Date fromEra = CHRONO.date(IsoEra.CE, prolepticYear, 1, 1);
            assertEquals(date, fromEra);
        }

        // Non-positive proleptic years map to the BCE era, year-of-era == 1 - proleptic year.
        for (int prolepticYear = -200; prolepticYear < 0; prolepticYear++) {
            Symmetry010Date date = CHRONO.date(prolepticYear, 1, 1);

            assertEquals(prolepticYear, date.get(YEAR));
            assertEquals(IsoEra.BCE, date.getEra());
            assertEquals(1 - prolepticYear, date.get(YEAR_OF_ERA));

            Symmetry010Date fromEra = CHRONO.date(IsoEra.BCE, prolepticYear, 1, 1);
            assertEquals(date, fromEra);
        }
    }
}
