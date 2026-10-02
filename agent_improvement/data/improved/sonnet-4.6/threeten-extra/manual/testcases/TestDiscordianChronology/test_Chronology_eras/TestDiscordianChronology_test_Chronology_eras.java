package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.chrono.Era;
import java.util.List;

import org.junit.jupiter.api.Test;

public class TestDiscordianChronology_test_Chronology_eras {

    // The Discordian calendar has exactly one era: YOLD (Year of Our Lady of Discord)
    @Test
    public void test_Chronology_eras() {
        List<Era> eras = DiscordianChronology.INSTANCE.eras();
        assertEquals(1, eras.size());
        assertTrue(eras.contains(DiscordianEra.YOLD));
    }
}
