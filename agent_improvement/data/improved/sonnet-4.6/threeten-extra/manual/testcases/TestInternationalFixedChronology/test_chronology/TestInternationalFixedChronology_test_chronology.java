package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.time.chrono.Chronology;

import org.junit.jupiter.api.Test;

@SuppressWarnings({ "static-method" })
public class TestInternationalFixedChronology_test_chronology {

    @Test
    public void test_chronology() {
        Chronology chrono = Chronology.of("Ifc");
        assertNotNull(chrono);
        assertEquals(InternationalFixedChronology.INSTANCE, chrono);
        assertEquals("Ifc", chrono.getId());
        assertNull(chrono.getCalendarType());
    }
}
