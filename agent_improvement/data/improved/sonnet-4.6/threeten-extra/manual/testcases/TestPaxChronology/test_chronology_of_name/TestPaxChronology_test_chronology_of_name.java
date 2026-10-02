package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.chrono.Chronology;

import org.junit.jupiter.api.Test;

@SuppressWarnings({ "static-method" })
public class TestPaxChronology_test_chronology_of_name {

    // Verifies that the Pax chronology can be looked up by its registered ID
    // and that its ID and calendar-type strings match the spec.
    @Test
    public void test_chronology_of_name() {
        Chronology chrono = Chronology.of("Pax");
        assertNotNull(chrono);
        assertEquals(PaxChronology.INSTANCE, chrono);
        assertEquals("Pax", chrono.getId());
        assertEquals("pax", chrono.getCalendarType());
    }
}
