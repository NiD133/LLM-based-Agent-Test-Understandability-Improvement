package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.chrono.Chronology;

import org.junit.jupiter.api.Test;

public class TestDiscordianChronology_test_chronology_of_name {

    /**
     * Looking up the Discordian chronology by its ID should return the
     * singleton instance and expose the expected ID and calendar type.
     */
    @Test
    public void test_chronology_of_name() {
        Chronology chrono = Chronology.of("Discordian");

        assertNotNull(chrono);
        assertEquals(DiscordianChronology.INSTANCE, chrono);
        assertEquals("Discordian", chrono.getId());
        assertEquals("discordian", chrono.getCalendarType());
    }
}
