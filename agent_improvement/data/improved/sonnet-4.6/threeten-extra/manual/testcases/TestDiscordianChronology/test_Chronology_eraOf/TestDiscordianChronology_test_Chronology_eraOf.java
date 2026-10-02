package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestDiscordianChronology_test_Chronology_eraOf {

    @Test
    public void test_Chronology_eraOf() {
        // Era value 1 represents the single Discordian era: YOLD (Year of Our Lady of Discord)
        assertEquals(DiscordianEra.YOLD, DiscordianChronology.INSTANCE.eraOf(1));
    }
}
