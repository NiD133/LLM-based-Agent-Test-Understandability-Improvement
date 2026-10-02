package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.time.chrono.Chronology;

import org.junit.jupiter.api.Test;

/**
 * Tests that the Symmetry454 chronology can be looked up by its identifier and
 * exposes the expected, stable metadata.
 */
@SuppressWarnings({ "static-method" })
public class TestSymmetry454Chronology_test_chronology {

    @Test
    public void test_chronology() {
        // Looking up the chronology by its ID must succeed...
        Chronology chrono = Chronology.of("Sym454");
        assertNotNull(chrono);

        // ...and must return the singleton instance with its expected metadata.
        assertEquals(Symmetry454Chronology.INSTANCE, chrono);
        assertEquals("Sym454", chrono.getId());
        assertNull(chrono.getCalendarType());
    }
}
