package org.threeten.extra.chrono;

import static java.time.temporal.ChronoField.YEAR;
import static java.time.temporal.ChronoField.YEAR_OF_ERA;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.chrono.IsoEra;

import org.junit.jupiter.api.Test;

@SuppressWarnings({"static-method"})
public class TestSymmetry010Chronology_test_era_yearDay_loop {

    @Test
    public void test_era_yearDay_loop() {
        // All positive proleptic years belong to the CE era in the Symmetry010 calendar.
        // Verify that dateYearDay(year, day) and dateYearDay(era, year, day) produce
        // identical dates, and that the year and year-of-era fields are correct.
        IsoEra era = IsoEra.CE;
        for (int year = 1; year < 200; year++) {
            Symmetry010Date base = Symmetry010Chronology.INSTANCE.dateYearDay(year, 1);
            assertEquals(year, base.get(YEAR));
            assertEquals(era, base.getEra());
            assertEquals(year, base.get(YEAR_OF_ERA));
            Symmetry010Date eraBased = Symmetry010Chronology.INSTANCE.dateYearDay(era, year, 1);
            assertEquals(base, eraBased);
        }
    }
}
