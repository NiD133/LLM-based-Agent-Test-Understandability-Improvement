package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.chrono.Chronology;

import org.junit.jupiter.api.Test;

public class TestJulianChronology_test_chronology_of_name_id {

    @Test
    public void test_chronology_of_name_id() {
        // Verify that the Julian chronology can be looked up by its registered name,
        // and that it reports the correct ID and LDML calendar type.
        Chronology chrono = Chronology.of("julian");
        assertNotNull(chrono);
        assertEquals(JulianChronology.INSTANCE, chrono);
        assertEquals("Julian", chrono.getId());
        assertEquals("julian", chrono.getCalendarType());
    }
}
