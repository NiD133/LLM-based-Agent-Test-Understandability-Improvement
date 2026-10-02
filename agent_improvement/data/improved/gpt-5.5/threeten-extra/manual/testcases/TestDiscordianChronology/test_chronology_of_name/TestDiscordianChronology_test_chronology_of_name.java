package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.chrono.Chronology;

import org.junit.jupiter.api.Test;

public class TestDiscordianChronology_test_chronology_of_name {

    @Test
    public void test_chronology_of_name() {
        Chronology chrono = Chronology.of("Discordian");

        assertNotNull(chrono);
        assertEquals(DiscordianChronology.INSTANCE, chrono);
        assertEquals("Discordian", chrono.getId());
        assertEquals("discordian", chrono.getCalendarType());
    }
}
