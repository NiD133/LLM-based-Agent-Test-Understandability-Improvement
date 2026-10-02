package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.time.chrono.Chronology;

import org.junit.jupiter.api.Test;

/**
 * Verifies that the British cutover calendar can be looked up by its name
 * through the standard {@link Chronology#of(String)} registry.
 */
public class TestBritishCutoverChronology_test_chronology_of_name {

    @Test
    public void test_chronology_of_name() {
        Chronology chrono = Chronology.of("BritishCutover");

        // The lookup must resolve to the singleton BritishCutover chronology.
        assertNotNull(chrono);
        assertEquals(BritishCutoverChronology.INSTANCE, chrono);

        // The chronology exposes "BritishCutover" as its id and has no CLDR calendar type.
        assertEquals("BritishCutover", chrono.getId());
        assertNull(chrono.getCalendarType());
    }
}
