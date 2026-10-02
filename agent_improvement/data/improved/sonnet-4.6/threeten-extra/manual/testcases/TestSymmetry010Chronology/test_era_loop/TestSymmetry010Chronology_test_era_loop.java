package org.threeten.extra.chrono;

import static java.time.temporal.ChronoField.YEAR;
import static java.time.temporal.ChronoField.YEAR_OF_ERA;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.chrono.IsoEra;

import org.junit.jupiter.api.Test;

@SuppressWarnings({ "static-method" })
public class TestSymmetry010Chronology_test_era_loop {

    @Test
    public void test_era_loop() {
        for (int year = 1; year < 200; year++) {
            assertCeYearProperties(year);
        }
        for (int year = -200; year < 0; year++) {
            assertBceYearProperties(year);
        }
    }

    private void assertCeYearProperties(int year) {
        Symmetry010Date date = Symmetry010Chronology.INSTANCE.date(year, 1, 1);
        assertEquals(year, date.get(YEAR));
        assertEquals(IsoEra.CE, date.getEra());
        assertEquals(year, date.get(YEAR_OF_ERA));
        // Confirm that constructing via era + year-of-era yields the same date
        Symmetry010Date eraBased = Symmetry010Chronology.INSTANCE.date(IsoEra.CE, year, 1, 1);
        assertEquals(date, eraBased);
    }

    private void assertBceYearProperties(int year) {
        Symmetry010Date date = Symmetry010Chronology.INSTANCE.date(year, 1, 1);
        assertEquals(year, date.get(YEAR));
        assertEquals(IsoEra.BCE, date.getEra());
        // BCE year-of-era counts upward as the proleptic year decreases below 0:
        // proleptic year -1 → year-of-era 2, proleptic year -2 → year-of-era 3, etc.
        assertEquals(1 - year, date.get(YEAR_OF_ERA));
        // Confirm that constructing via era + year-of-era yields the same date
        Symmetry010Date eraBased = Symmetry010Chronology.INSTANCE.date(IsoEra.BCE, year, 1, 1);
        assertEquals(date, eraBased);
    }
}
