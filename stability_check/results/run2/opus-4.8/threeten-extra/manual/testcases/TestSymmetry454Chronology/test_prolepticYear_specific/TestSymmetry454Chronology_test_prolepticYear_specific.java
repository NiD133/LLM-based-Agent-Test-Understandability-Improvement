package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.chrono.IsoEra;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link Symmetry454Chronology#prolepticYear(java.time.chrono.Era, int)}
 * returns the year-of-era unchanged when the era is the ISO Common Era (CE),
 * for which the proleptic year and the year-of-era are identical.
 */
@SuppressWarnings({ "static-method" })
public class TestSymmetry454Chronology_test_prolepticYear_specific {

    @Test
    public void test_prolepticYear_specific() {
        Symmetry454Chronology chronology = Symmetry454Chronology.INSTANCE;

        // For the CE era, prolepticYear should echo the supplied year-of-era.
        assertEquals(4, chronology.prolepticYear(IsoEra.CE, 4));
        assertEquals(3, chronology.prolepticYear(IsoEra.CE, 3));
        assertEquals(2, chronology.prolepticYear(IsoEra.CE, 2));
        assertEquals(1, chronology.prolepticYear(IsoEra.CE, 1));
        assertEquals(2000, chronology.prolepticYear(IsoEra.CE, 2000));
        assertEquals(1582, chronology.prolepticYear(IsoEra.CE, 1582));
    }
}
