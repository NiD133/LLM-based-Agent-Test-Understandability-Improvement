package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.chrono.Chronology;

import org.junit.jupiter.api.Test;

@SuppressWarnings("static-method")
public class TestPaxChronology_test_chronology_of_name {

    private static final String PAX_CHRONOLOGY_ID = "Pax";
    private static final String PAX_CALENDAR_TYPE = "pax";

    @Test
    public void test_chronology_of_name() {
        Chronology chrono = Chronology.of(PAX_CHRONOLOGY_ID);

        assertNotNull(chrono);
        assertEquals(PaxChronology.INSTANCE, chrono);
        assertEquals(PAX_CHRONOLOGY_ID, chrono.getId());
        assertEquals(PAX_CALENDAR_TYPE, chrono.getCalendarType());
    }
}
