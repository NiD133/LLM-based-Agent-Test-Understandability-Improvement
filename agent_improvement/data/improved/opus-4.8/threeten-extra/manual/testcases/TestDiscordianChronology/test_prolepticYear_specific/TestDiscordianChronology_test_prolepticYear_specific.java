package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link DiscordianChronology#prolepticYear} returns the year-of-era
 * unchanged for the single Discordian era (YOLD), where proleptic-year and
 * year-of-era are defined to be identical.
 */
public class TestDiscordianChronology_test_prolepticYear_specific {

    @Test
    public void test_prolepticYear_specific() {
        DiscordianChronology chronology = DiscordianChronology.INSTANCE;

        // For the YOLD era, the proleptic year equals the year-of-era.
        assertEquals(4, chronology.prolepticYear(DiscordianEra.YOLD, 4));
        assertEquals(3, chronology.prolepticYear(DiscordianEra.YOLD, 3));
        assertEquals(2, chronology.prolepticYear(DiscordianEra.YOLD, 2));
        assertEquals(1, chronology.prolepticYear(DiscordianEra.YOLD, 1));
    }
}
