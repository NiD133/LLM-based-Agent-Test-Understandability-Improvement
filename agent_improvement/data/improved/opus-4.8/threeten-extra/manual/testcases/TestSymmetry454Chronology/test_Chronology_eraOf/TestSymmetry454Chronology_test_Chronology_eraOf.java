package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.chrono.IsoEra;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link Symmetry454Chronology#eraOf(int)} maps the proleptic era
 * value to the matching {@link IsoEra}: 0 is the era before the common era
 * (BCE) and 1 is the common era (CE).
 */
@SuppressWarnings({ "static-method" })
public class TestSymmetry454Chronology_test_Chronology_eraOf {

    @Test
    public void test_Chronology_eraOf() {
        assertEquals(IsoEra.BCE, Symmetry454Chronology.INSTANCE.eraOf(0));
        assertEquals(IsoEra.CE, Symmetry454Chronology.INSTANCE.eraOf(1));
    }
}
