package org.threeten.extra.chrono;

import static java.time.temporal.ChronoField.YEAR;
import static java.time.temporal.ChronoField.YEAR_OF_ERA;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.chrono.IsoEra;

import org.junit.jupiter.api.Test;

@SuppressWarnings("static-method")
public class TestSymmetry454Chronology_test_era_loop {

    /**
     * Asserts that a Symmetry454 date created from {@code prolepticYear} has the expected era and
     * year-of-era, and that re-creating it via the era-based factory produces an equal date.
     *
     * Note: the chronology's {@code prolepticYear(Era, int)} implementation ignores the era and
     * returns the supplied value unchanged, so the era-based factory call passes the raw proleptic
     * year (which may be negative for BCE) as the yearOfEra argument — matching the original test.
     */
    private static void assertEraFieldsAndRoundTrip(int prolepticYear, IsoEra expectedEra, int expectedYearOfEra) {
        Symmetry454Date base = Symmetry454Chronology.INSTANCE.date(prolepticYear, 1, 1);
        assertEquals(prolepticYear, base.get(YEAR));
        assertEquals(expectedEra, base.getEra());
        assertEquals(expectedYearOfEra, base.get(YEAR_OF_ERA));
        Symmetry454Date eraBased = Symmetry454Chronology.INSTANCE.date(expectedEra, prolepticYear, 1, 1);
        assertEquals(base, eraBased);
    }

    @Test
    public void test_era_loop() {
        // CE years: proleptic year equals year-of-era (1 … 199)
        for (int year = 1; year < 200; year++) {
            assertEraFieldsAndRoundTrip(year, IsoEra.CE, year);
        }
        // BCE years: proleptic year is negative; year-of-era = 1 − prolepticYear (-200 … -1)
        for (int year = -200; year < 0; year++) {
            assertEraFieldsAndRoundTrip(year, IsoEra.BCE, 1 - year);
        }
    }
}
