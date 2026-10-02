package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.chrono.Era;
import java.util.List;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link PaxChronology} reports exactly its two eras: BCE and CE.
 */
@SuppressWarnings({ "static-method" })
public class TestPaxChronology_test_Chronology_eras {

    @Test
    public void test_Chronology_eras() {
        List<Era> eras = PaxChronology.INSTANCE.eras();

        assertEquals(2, eras.size(), "Pax chronology should define exactly two eras");
        assertTrue(eras.contains(PaxEra.BCE), "eras() should contain BCE");
        assertTrue(eras.contains(PaxEra.CE), "eras() should contain CE");
    }
}
