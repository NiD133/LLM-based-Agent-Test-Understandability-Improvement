package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.chrono.Chronology;

import org.junit.jupiter.api.Test;

/**
 * Verifies that the Julian chronology can be looked up by its calendar-type name
 * and that the resolved instance exposes the expected identifiers.
 */
public class TestJulianChronology_test_chronology_of_name_id {

    @Test
    public void test_chronology_of_name_id() {
        // Look up the Julian chronology using its calendar-type name.
        Chronology chrono = Chronology.of("julian");

        // The lookup must succeed and return the singleton Julian chronology.
        assertNotNull(chrono);
        assertEquals(JulianChronology.INSTANCE, chrono);

        // The resolved chronology should report the expected ID and calendar type.
        assertEquals("Julian", chrono.getId());
        assertEquals("julian", chrono.getCalendarType());
    }
}
