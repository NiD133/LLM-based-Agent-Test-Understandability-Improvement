package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.chrono.IsoEra;

import org.junit.jupiter.api.Test;

@SuppressWarnings({ "static-method" })
public class TestSymmetry454Chronology_test_prolepticYear_specific {

    /**
     * In the Symmetry454 calendar the proleptic year equals the year-of-era for CE dates,
     * so prolepticYear(CE, n) must return n unchanged.
     */
    @Test
    public void test_prolepticYear_specific() {
        // Small positive years
        assertEquals(1, Symmetry454Chronology.INSTANCE.prolepticYear(IsoEra.CE, 1));
        assertEquals(2, Symmetry454Chronology.INSTANCE.prolepticYear(IsoEra.CE, 2));
        assertEquals(3, Symmetry454Chronology.INSTANCE.prolepticYear(IsoEra.CE, 3));
        assertEquals(4, Symmetry454Chronology.INSTANCE.prolepticYear(IsoEra.CE, 4));

        // Historically notable years
        assertEquals(1582, Symmetry454Chronology.INSTANCE.prolepticYear(IsoEra.CE, 1582));
        assertEquals(2000, Symmetry454Chronology.INSTANCE.prolepticYear(IsoEra.CE, 2000));
    }
}
