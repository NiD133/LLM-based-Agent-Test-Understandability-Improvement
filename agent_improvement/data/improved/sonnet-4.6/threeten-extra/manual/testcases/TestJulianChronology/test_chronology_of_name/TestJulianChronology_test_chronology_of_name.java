package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.chrono.Chronology;

import org.junit.jupiter.api.Test;

public class TestJulianChronology_test_chronology_of_name {

    /**
     * Verifies that the Julian chronology can be looked up by name and that its
     * ID and calendar-type strings match the documented values.
     */
    @Test
    public void test_chronology_of_name() {
        Chronology chrono = Chronology.of("Julian");
        assertNotNull(chrono);
        assertEquals(JulianChronology.INSTANCE, chrono);
        assertEquals("Julian", chrono.getId());
        assertEquals("julian", chrono.getCalendarType());
    }
}
