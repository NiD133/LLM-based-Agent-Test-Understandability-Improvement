package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.chrono.Chronology;

import org.junit.jupiter.api.Test;

@SuppressWarnings({ "static-method" })
public class TestPaxChronology_test_chronology_of_name_id {

    @Test
    public void test_chronology_of_name_id() {
        Chronology chrono = Chronology.of("pax");
        assertNotNull(chrono);
        assertEquals(PaxChronology.INSTANCE, chrono);
        assertEquals("Pax", chrono.getId());
        assertEquals("pax", chrono.getCalendarType());
    }
}
