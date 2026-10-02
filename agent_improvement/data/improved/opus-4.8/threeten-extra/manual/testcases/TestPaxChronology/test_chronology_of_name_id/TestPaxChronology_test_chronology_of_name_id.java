package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.chrono.Chronology;

import org.junit.jupiter.api.Test;

/**
 * Tests that the Pax chronology can be looked up by name and exposes the
 * expected identifiers.
 */
@SuppressWarnings({ "static-method" })
public class TestPaxChronology_test_chronology_of_name_id {

    @Test
    public void test_chronology_of_name_id() {
        // Look up the Pax chronology via its calendar type registered with the JDK.
        Chronology chrono = Chronology.of("pax");

        // The lookup must succeed and return the Pax singleton.
        assertNotNull(chrono);
        assertEquals(PaxChronology.INSTANCE, chrono);

        // Verify the chronology reports its expected ID and calendar type.
        assertEquals("Pax", chrono.getId());
        assertEquals("pax", chrono.getCalendarType());
    }
}
