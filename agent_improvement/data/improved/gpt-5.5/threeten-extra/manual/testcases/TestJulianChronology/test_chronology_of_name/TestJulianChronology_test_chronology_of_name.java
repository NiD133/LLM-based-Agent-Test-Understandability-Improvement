package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.chrono.Chronology;

import org.junit.jupiter.api.Test;

public class TestJulianChronology_test_chronology_of_name {

    @Test
    public void test_chronology_of_name() {
        Chronology julianChronology = Chronology.of("Julian");

        assertNotNull(julianChronology);
        assertEquals(JulianChronology.INSTANCE, julianChronology);
        assertEquals("Julian", julianChronology.getId());
        assertEquals("julian", julianChronology.getCalendarType());
    }
}
