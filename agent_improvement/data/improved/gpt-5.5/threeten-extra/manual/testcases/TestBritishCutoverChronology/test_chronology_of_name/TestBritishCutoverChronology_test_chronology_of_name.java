package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.time.chrono.Chronology;

import org.junit.jupiter.api.Test;

public class TestBritishCutoverChronology_test_chronology_of_name {

    @Test
    public void test_chronology_of_name() {
        Chronology chrono = Chronology.of("BritishCutover");

        assertNotNull(chrono);
        assertEquals(BritishCutoverChronology.INSTANCE, chrono);
        assertEquals("BritishCutover", chrono.getId());
        assertNull(chrono.getCalendarType());
    }
}
