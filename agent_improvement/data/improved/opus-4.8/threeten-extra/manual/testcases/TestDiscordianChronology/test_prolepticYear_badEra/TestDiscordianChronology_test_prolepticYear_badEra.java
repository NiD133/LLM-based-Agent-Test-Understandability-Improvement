package org.threeten.extra.chrono;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.chrono.IsoEra;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link DiscordianChronology#prolepticYear(java.time.chrono.Era, int)}
 * rejects an era that does not belong to the Discordian calendar system.
 */
public class TestDiscordianChronology_test_prolepticYear_badEra {

    @Test
    public void prolepticYear_withNonDiscordianEra_throwsClassCastException() {
        // IsoEra is not a DiscordianEra, so converting it to a proleptic year must fail.
        assertThrows(
                ClassCastException.class,
                () -> DiscordianChronology.INSTANCE.prolepticYear(IsoEra.CE, 4));
    }
}
