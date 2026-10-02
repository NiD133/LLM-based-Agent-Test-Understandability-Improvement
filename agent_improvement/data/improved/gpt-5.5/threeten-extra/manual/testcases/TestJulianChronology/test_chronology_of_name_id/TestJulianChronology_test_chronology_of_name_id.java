package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.chrono.Chronology;

import org.junit.jupiter.api.Test;

public class TestJulianChronology_test_chronology_of_name_id {

    @Test
    public void test_chronology_of_name_id() {
        Chronology chronology = Chronology.of("julian");

        assertNotNull(chronology);
        assertEquals(JulianChronology.INSTANCE, chronology);
        assertEquals("Julian", chronology.getId());
        assertEquals("julian", chronology.getCalendarType());
    }
}
