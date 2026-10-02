package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link DiscordianChronology#eraOf(int)} rejects era indices outside
 * the single valid Discordian era (YOLD = 1).
 */
public class TestDiscordianChronology_test_Chronology_eraOf_invalid {

    // The Discordian calendar has exactly one era: YOLD (Year of Our Lady of Discord), index 1.
    // Any other index — whether too low (0) or too high (2) — is not a valid era.

    @Test
    public void test_Chronology_eraOf_invalid() {
        assertThrows(DateTimeException.class, () -> DiscordianChronology.INSTANCE.eraOf(2));
        assertThrows(DateTimeException.class, () -> DiscordianChronology.INSTANCE.eraOf(0));
    }
}
