package org.threeten.extra.chrono;

import static java.time.temporal.ChronoField.YEAR;
import static java.time.temporal.ChronoField.YEAR_OF_ERA;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.chrono.IsoEra;

import org.junit.jupiter.api.Test;

@SuppressWarnings({ "static-method" })
public class TestSymmetry010Chronology_test_era_yearDay_loop {

    /**
     * For every proleptic year in the range, the date built from a plain
     * (year, dayOfYear) pair must be consistent with its era view:
     * the year fields read back unchanged, the era is always CE, and
     * building the same date through the era-based factory yields an equal date.
     */
    @Test
    public void test_era_yearDay_loop() {
        // The Symmetry010 calendar shares the ISO eras, so every CE date reports IsoEra.CE.
        IsoEra expectedEra = IsoEra.CE;
        int firstDayOfYear = 1;

        for (int year = 1; year < 200; year++) {
            Symmetry010Date date = Symmetry010Chronology.INSTANCE.dateYearDay(year, firstDayOfYear);

            assertEquals(year, date.get(YEAR));
            assertEquals(expectedEra, date.getEra());
            assertEquals(year, date.get(YEAR_OF_ERA));

            Symmetry010Date dateViaEra =
                    Symmetry010Chronology.INSTANCE.dateYearDay(expectedEra, year, firstDayOfYear);
            assertEquals(date, dateViaEra);
        }
    }
}
