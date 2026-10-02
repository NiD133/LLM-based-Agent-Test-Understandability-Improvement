package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.time.chrono.Chronology;

import org.junit.jupiter.api.Test;

@SuppressWarnings({ "static-method" })
public class TestSymmetry010Chronology_test_chronology {

    @Test
    public void test_chronology() {
        Chronology chronology = Chronology.of("Sym010");

        assertNotNull(chronology);
        assertEquals(Symmetry010Chronology.INSTANCE, chronology);
        assertEquals("Sym010", chronology.getId());
        assertNull(chronology.getCalendarType());
    }
}
