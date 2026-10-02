package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.chrono.Chronology;

import org.junit.jupiter.api.Test;

public class TestDiscordianChronology_test_chronology_of_name_id {

    @Test
    public void test_chronology_of_name_id() {
        // Look up the Discordian chronology by its calendar-type string
        Chronology chrono = Chronology.of("discordian");

        assertNotNull(chrono);
        assertEquals(DiscordianChronology.INSTANCE, chrono);
        // getId() returns the human-readable name; getCalendarType() returns the LDML key
        assertEquals("Discordian", chrono.getId());
        assertEquals("discordian", chrono.getCalendarType());
    }
}
