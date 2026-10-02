package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.time.chrono.Chronology;

import org.junit.jupiter.api.Test;

/**
 * Verifies that the British cutover chronology can be looked up by its name
 * via {@link Chronology#of(String)} and that the resolved instance exposes the
 * expected identity.
 */
public class TestBritishCutoverChronology_test_chronology_of_name {

    @Test
    public void test_chronology_of_name() {
        Chronology chrono = Chronology.of("BritishCutover");

        // Lookup by name returns the singleton British cutover chronology.
        assertNotNull(chrono);
        assertEquals(BritishCutoverChronology.INSTANCE, chrono);

        // The resolved chronology reports "BritishCutover" as its id and has no calendar type.
        assertEquals("BritishCutover", chrono.getId());
        assertNull(chrono.getCalendarType());
    }
}
