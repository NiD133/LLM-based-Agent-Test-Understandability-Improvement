package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.chrono.IsoEra;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link Symmetry010Chronology#eraOf(int)}.
 * <p>
 * Symmetry010 shares the ISO eras, so era value 0 must map to {@link IsoEra#BCE}
 * and era value 1 to {@link IsoEra#CE}.
 */
@SuppressWarnings({ "static-method" })
public class TestSymmetry010Chronology_test_Chronology_eraOf {

    @Test
    public void test_Chronology_eraOf() {
        assertEquals(IsoEra.BCE, Symmetry010Chronology.INSTANCE.eraOf(0));
        assertEquals(IsoEra.CE, Symmetry010Chronology.INSTANCE.eraOf(1));
    }
}
