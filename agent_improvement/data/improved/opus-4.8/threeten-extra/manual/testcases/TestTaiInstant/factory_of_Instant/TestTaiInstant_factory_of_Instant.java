package org.threeten.extra.scale;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Instant;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link TaiInstant#of(Instant)}, which converts a UTC {@code Instant}
 * into a point on the TAI time-scale.
 */
public class TestTaiInstant_factory_of_Instant {

    @Test
    public void factory_of_Instant_convertsEpochInstantToTai() {
        // An Instant at the Unix epoch (1970-01-01T00:00:00Z) plus 2 nanoseconds.
        Instant unixEpochPlus2Nanos = Instant.ofEpochSecond(0, 2);

        TaiInstant tai = TaiInstant.of(unixEpochPlus2Nanos);

        // The TAI epoch is 1958-01-01, the Unix epoch is 1970-01-01.
        // 40587 and 36204 are the Modified Julian Days of those two epochs,
        // so (40587 - 36204) is the number of whole days between them.
        // Multiplying by the seconds-per-day gives the gap in seconds, and a
        // fixed +10 second offset accounts for the TAI-UTC difference at 1970.
        long daysBetweenTaiAndUnixEpoch = 40587L - 36204L;
        long secondsPerDay = 24L * 60 * 60;
        long expectedTaiSeconds = daysBetweenTaiAndUnixEpoch * secondsPerDay + 10;

        assertEquals(expectedTaiSeconds, tai.getTaiSeconds());
        assertEquals(2, tai.getNano());
    }
}
