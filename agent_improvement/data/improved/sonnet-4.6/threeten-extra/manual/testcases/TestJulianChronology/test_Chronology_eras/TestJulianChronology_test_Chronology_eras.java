package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.chrono.Era;
import java.util.List;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link JulianChronology#eras()} returns exactly the two Julian eras: BC and AD.
 */
public class TestJulianChronology_test_Chronology_eras {

    @Test
    public void test_Chronology_eras() {
        List<Era> eras = JulianChronology.INSTANCE.eras();
        assertEquals(2, eras.size());
        assertTrue(eras.contains(JulianEra.BC));
        assertTrue(eras.contains(JulianEra.AD));
    }
}
