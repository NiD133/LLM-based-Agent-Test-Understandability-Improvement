package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.time.chrono.Chronology;

import org.junit.jupiter.api.Test;

/**
 * Tests that the Symmetry010 chronology can be looked up by its ID and exposes
 * the expected identity metadata.
 */
@SuppressWarnings({ "static-method" })
public class TestSymmetry010Chronology_test_chronology {

    @Test
    public void test_chronology() {
        // The chronology must be discoverable through the standard registry by its ID.
        Chronology chrono = Chronology.of("Sym010");

        assertNotNull(chrono);
        // Looking it up by ID must return the shared singleton instance.
        assertEquals(Symmetry010Chronology.INSTANCE, chrono);
        // Identity metadata: a fixed ID and no LDML calendar type.
        assertEquals("Sym010", chrono.getId());
        assertNull(chrono.getCalendarType());
    }
}
