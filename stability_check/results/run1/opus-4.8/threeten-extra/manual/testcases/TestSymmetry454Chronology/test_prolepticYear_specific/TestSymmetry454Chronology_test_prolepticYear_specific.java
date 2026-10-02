package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.chrono.IsoEra;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link Symmetry454Chronology#prolepticYear(java.time.chrono.Era, int)}
 * returns the year-of-era unchanged for the ISO Common Era (CE), which is the only
 * era supported by the Symmetry454 calendar.
 */
@SuppressWarnings({ "static-method" })
public class TestSymmetry454Chronology_test_prolepticYear_specific {

    @Test
    public void test_prolepticYear_specific() {
        // For the CE era the proleptic year equals the year-of-era, so each input maps to itself.
        assertEquals(4, Symmetry454Chronology.INSTANCE.prolepticYear(IsoEra.CE, 4));
        assertEquals(3, Symmetry454Chronology.INSTANCE.prolepticYear(IsoEra.CE, 3));
        assertEquals(2, Symmetry454Chronology.INSTANCE.prolepticYear(IsoEra.CE, 2));
        assertEquals(1, Symmetry454Chronology.INSTANCE.prolepticYear(IsoEra.CE, 1));
        assertEquals(2000, Symmetry454Chronology.INSTANCE.prolepticYear(IsoEra.CE, 2000));
        assertEquals(1582, Symmetry454Chronology.INSTANCE.prolepticYear(IsoEra.CE, 1582));
    }
}
