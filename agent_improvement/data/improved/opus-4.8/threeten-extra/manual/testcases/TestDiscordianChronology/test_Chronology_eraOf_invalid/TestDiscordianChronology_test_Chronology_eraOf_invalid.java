package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.DateTimeException;

import org.junit.jupiter.api.Test;

/**
 * Verifies that {@link DiscordianChronology#eraOf(int)} rejects era values
 * that do not correspond to a valid {@link DiscordianEra}.
 */
public class TestDiscordianChronology_test_Chronology_eraOf_invalid {

    @Test
    public void eraOf_rejectsValuesOutsideTheValidEraRange() {
        // The only valid Discordian era is YOLD (value 1); anything else must fail.
        int eraAboveValidRange = 2;
        int eraBelowValidRange = 0;

        assertThrows(DateTimeException.class,
                () -> DiscordianChronology.INSTANCE.eraOf(eraAboveValidRange));
        assertThrows(DateTimeException.class,
                () -> DiscordianChronology.INSTANCE.eraOf(eraBelowValidRange));
    }
}
