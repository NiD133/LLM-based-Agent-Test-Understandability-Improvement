package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.chrono.Chronology;

import org.junit.jupiter.api.Test;

/**
 * Verifies that the Discordian chronology can be looked up by its calendar-type
 * name and that, once found, it reports the expected singleton, id and calendar type.
 */
public class TestDiscordianChronology_test_chronology_of_name_id {

    @Test
    public void test_chronology_of_name_id() {
        // Look up the chronology by its calendar-type name.
        Chronology chrono = Chronology.of("discordian");

        // The lookup must resolve to the Discordian singleton.
        assertNotNull(chrono);
        assertEquals(DiscordianChronology.INSTANCE, chrono);

        // The resolved chronology should expose its canonical id and calendar type.
        assertEquals("Discordian", chrono.getId());
        assertEquals("discordian", chrono.getCalendarType());
    }
}
