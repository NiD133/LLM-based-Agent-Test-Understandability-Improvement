package org.threeten.extra.chrono;

import static java.time.temporal.ChronoField.YEAR;
import static java.time.temporal.ChronoField.YEAR_OF_ERA;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.chrono.IsoEra;

import org.junit.jupiter.api.Test;

@SuppressWarnings({ "static-method" })
public class TestSymmetry454Chronology_test_era_yearDay_loop {

    // Verifies that dateYearDay(year, 1) and dateYearDay(era, year, 1) produce identical dates,
    // and that the YEAR, ERA, and YEAR_OF_ERA fields round-trip correctly for CE years 1–199.
    @Test
    public void test_era_yearDay_loop() {
        for (int year = 1; year < 200; year++) {
            Symmetry454Date base = Symmetry454Chronology.INSTANCE.dateYearDay(year, 1);

            assertEquals(year, base.get(YEAR));

            IsoEra era = IsoEra.CE;
            assertEquals(era, base.getEra());
            assertEquals(year, base.get(YEAR_OF_ERA));

            Symmetry454Date eraBased = Symmetry454Chronology.INSTANCE.dateYearDay(era, year, 1);
            assertEquals(base, eraBased);
        }
    }
}
