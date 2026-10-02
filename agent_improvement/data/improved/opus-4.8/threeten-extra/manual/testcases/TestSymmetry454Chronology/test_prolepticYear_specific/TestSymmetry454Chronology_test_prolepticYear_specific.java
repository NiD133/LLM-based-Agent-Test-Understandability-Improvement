package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.chrono.IsoEra;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link Symmetry454Chronology#prolepticYear(java.time.chrono.Era, int)}
 * returns the year-of-era unchanged for the CE era, since the Symmetry454
 * calendar shares the ISO era model where the proleptic year equals the
 * year-of-era within the Common Era.
 */
@SuppressWarnings({ "static-method" })
public class TestSymmetry454Chronology_test_prolepticYear_specific {

    @Test
    public void test_prolepticYear_specific() {
        Symmetry454Chronology chronology = Symmetry454Chronology.INSTANCE;

        // For the CE era, the proleptic year is identical to the year-of-era.
        assertEquals(4, chronology.prolepticYear(IsoEra.CE, 4));
        assertEquals(3, chronology.prolepticYear(IsoEra.CE, 3));
        assertEquals(2, chronology.prolepticYear(IsoEra.CE, 2));
        assertEquals(1, chronology.prolepticYear(IsoEra.CE, 1));
        assertEquals(2000, chronology.prolepticYear(IsoEra.CE, 2000));
        assertEquals(1582, chronology.prolepticYear(IsoEra.CE, 1582));
    }
}
