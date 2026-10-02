package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.chrono.Era;
import java.util.List;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link DiscordianChronology} exposes exactly the single Discordian era.
 */
public class TestDiscordianChronology_test_Chronology_eras {

    @Test
    public void eras_containOnlyTheSingleYoldEra() {
        List<Era> eras = DiscordianChronology.INSTANCE.eras();

        assertEquals(1, eras.size(), "Discordian calendar should define exactly one era");
        assertTrue(eras.contains(DiscordianEra.YOLD), "The single era should be YOLD");
    }
}
