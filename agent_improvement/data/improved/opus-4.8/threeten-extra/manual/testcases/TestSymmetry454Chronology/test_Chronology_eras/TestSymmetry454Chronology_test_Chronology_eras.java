package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.chrono.Era;
import java.time.chrono.IsoEra;
import java.util.List;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link Symmetry454Chronology} exposes the ISO eras (BCE and CE).
 */
@SuppressWarnings({ "static-method" })
public class TestSymmetry454Chronology_test_Chronology_eras {

    @Test
    public void test_Chronology_eras() {
        List<Era> eras = Symmetry454Chronology.INSTANCE.eras();

        assertEquals(2, eras.size(), "Symmetry454 should expose exactly the two ISO eras");
        assertTrue(eras.contains(IsoEra.BCE), "eras should contain BCE");
        assertTrue(eras.contains(IsoEra.CE), "eras should contain CE");
    }
}
