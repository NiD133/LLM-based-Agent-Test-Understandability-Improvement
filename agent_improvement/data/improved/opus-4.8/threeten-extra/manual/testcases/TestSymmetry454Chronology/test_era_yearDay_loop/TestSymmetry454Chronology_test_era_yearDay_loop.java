package org.threeten.extra.chrono;

import static java.time.temporal.ChronoField.YEAR;
import static java.time.temporal.ChronoField.YEAR_OF_ERA;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.chrono.IsoEra;

import org.junit.jupiter.api.Test;

@SuppressWarnings({ "static-method" })
public class TestSymmetry454Chronology_test_era_yearDay_loop {

    /**
     * For every year in the range, a date built directly via {@code dateYearDay(year, day)}
     * must be identical to the date built via the era-aware
     * {@code dateYearDay(era, year, day)}. Along the way we confirm that a freshly created
     * date reports the expected proleptic year, the {@code IsoEra.CE} era, and the matching
     * year-of-era (these are equal for CE dates).
     */
    @Test
    public void test_era_yearDay_loop() {
        final int firstDayOfYear = 1;
        final IsoEra expectedEra = IsoEra.CE;

        for (int year = 1; year < 200; year++) {
            Symmetry454Date dateByYearDay =
                Symmetry454Chronology.INSTANCE.dateYearDay(year, firstDayOfYear);

            assertEquals(year, dateByYearDay.get(YEAR));
            assertEquals(expectedEra, dateByYearDay.getEra());
            assertEquals(year, dateByYearDay.get(YEAR_OF_ERA));

            Symmetry454Date dateByEraYearDay =
                Symmetry454Chronology.INSTANCE.dateYearDay(expectedEra, year, firstDayOfYear);
            assertEquals(dateByYearDay, dateByEraYearDay);
        }
    }
}
