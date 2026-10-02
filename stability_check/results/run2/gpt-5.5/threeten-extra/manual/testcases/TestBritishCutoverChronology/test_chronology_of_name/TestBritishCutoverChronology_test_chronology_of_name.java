package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.time.chrono.Chronology;

import org.junit.jupiter.api.Test;

public class TestBritishCutoverChronology_test_chronology_of_name {

    private static final String BRITISH_CUTOVER_ID = "BritishCutover";

    @Test
    public void test_chronology_of_name() {
        Chronology chronology = Chronology.of(BRITISH_CUTOVER_ID);

        assertNotNull(chronology);
        assertEquals(BritishCutoverChronology.INSTANCE, chronology);
        assertEquals(BRITISH_CUTOVER_ID, chronology.getId());
        assertNull(chronology.getCalendarType());
    }
}
