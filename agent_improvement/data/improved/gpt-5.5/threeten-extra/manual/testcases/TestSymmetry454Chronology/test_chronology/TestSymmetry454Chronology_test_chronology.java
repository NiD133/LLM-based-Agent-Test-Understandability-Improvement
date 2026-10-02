package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.time.chrono.Chronology;

import org.junit.jupiter.api.Test;

@SuppressWarnings({ "static-method" })
public class TestSymmetry454Chronology_test_chronology {

    @Test
    public void test_chronology() {
        Chronology chronology = Chronology.of("Sym454");

        assertNotNull(chronology);
        assertEquals(Symmetry454Chronology.INSTANCE, chronology);
        assertEquals("Sym454", chronology.getId());
        assertNull(chronology.getCalendarType());
    }
}
