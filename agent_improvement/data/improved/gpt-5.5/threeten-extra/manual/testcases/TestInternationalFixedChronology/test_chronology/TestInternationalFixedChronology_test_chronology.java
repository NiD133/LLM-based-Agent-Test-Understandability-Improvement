package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.time.chrono.Chronology;

import org.junit.jupiter.api.Test;

@SuppressWarnings("static-method")
public class TestInternationalFixedChronology_test_chronology {

    private static final String INTERNATIONAL_FIXED_ID = "Ifc";

    @Test
    public void test_chronology() {
        Chronology chrono = Chronology.of(INTERNATIONAL_FIXED_ID);

        assertNotNull(chrono);
        assertEquals(InternationalFixedChronology.INSTANCE, chrono);
        assertEquals(INTERNATIONAL_FIXED_ID, chrono.getId());
        assertNull(chrono.getCalendarType());
    }
}
