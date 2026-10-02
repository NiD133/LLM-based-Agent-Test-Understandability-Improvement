package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.chrono.Era;
import java.time.chrono.IsoEra;
import java.util.List;

import org.junit.jupiter.api.Test;

@SuppressWarnings({ "static-method" })
public class TestSymmetry454Chronology_test_Chronology_eras {

    @Test
    public void test_Chronology_eras() {
        List<Era> eras = Symmetry454Chronology.INSTANCE.eras();

        assertEquals(2, eras.size());
        assertTrue(eras.contains(IsoEra.BCE));
        assertTrue(eras.contains(IsoEra.CE));
    }
}
