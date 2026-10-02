package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.chrono.IsoEra;

import org.junit.jupiter.api.Test;

public class TestSymmetry010Chronology_test_Chronology_eraOf {

    @Test
    public void test_Chronology_eraOf() {
        assertEquals(IsoEra.BCE, Symmetry010Chronology.INSTANCE.eraOf(0));
        assertEquals(IsoEra.CE, Symmetry010Chronology.INSTANCE.eraOf(1));
    }
}
