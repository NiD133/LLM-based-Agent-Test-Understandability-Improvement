package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.chrono.IsoEra;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link Symmetry010Chronology#prolepticYear(java.time.chrono.Era, int)}
 * returns the year-of-era unchanged for the Common Era (CE).
 * <p>
 * In the Symmetry010 calendar the proleptic-year is defined to be identical to the
 * year-of-era for the CE era, so each call should simply echo back the year passed in.
 */
@SuppressWarnings({ "static-method" })
public class TestSymmetry010Chronology_test_prolepticYear_specific {

    @Test
    public void prolepticYear_inCommonEra_returnsYearOfEraUnchanged() {
        Symmetry010Chronology chronology = Symmetry010Chronology.INSTANCE;

        assertEquals(1, chronology.prolepticYear(IsoEra.CE, 1));
        assertEquals(2, chronology.prolepticYear(IsoEra.CE, 2));
        assertEquals(3, chronology.prolepticYear(IsoEra.CE, 3));
        assertEquals(4, chronology.prolepticYear(IsoEra.CE, 4));
        assertEquals(1582, chronology.prolepticYear(IsoEra.CE, 1582));
        assertEquals(2000, chronology.prolepticYear(IsoEra.CE, 2000));
    }
}
