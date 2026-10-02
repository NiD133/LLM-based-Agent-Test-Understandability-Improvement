package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.time.chrono.Chronology;

import org.junit.jupiter.api.Test;

@SuppressWarnings({ "static-method" })
public class TestSymmetry454Chronology_test_chronology {

    /**
     * Verifies that the Symmetry454 chronology can be looked up by its registered ID "Sym454",
     * that the resolved instance is the expected singleton, and that it carries the correct
     * ID string while returning null for the (unregistered) CLDR calendar type.
     */
    @Test
    public void test_chronology() {
        Chronology chrono = Chronology.of("Sym454");

        assertNotNull(chrono);
        assertEquals(Symmetry454Chronology.INSTANCE, chrono);
        assertEquals("Sym454", chrono.getId());
        assertNull(chrono.getCalendarType());
    }
}
