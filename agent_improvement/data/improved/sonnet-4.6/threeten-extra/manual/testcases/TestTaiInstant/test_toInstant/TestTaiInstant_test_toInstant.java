package org.threeten.extra.scale;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Instant;

import org.junit.jupiter.api.Test;

public class TestTaiInstant_test_toInstant {

    // Unix timestamp (seconds since 1970-01-01T00:00:00 UTC) of the TAI epoch (1958-01-01T00:00:00 TAI).
    // 12 years × 365 days + 3 leap-year days (1960, 1964, 1968) = 4383 days = 378,691,200 seconds before Unix epoch.
    private static final long TAI_EPOCH_UNIX_TIMESTAMP = -378691200L;

    // Initial TAI-UTC offset in seconds, defined when TAI was established in 1958 (before any leap seconds).
    private static final long INITIAL_TAI_UTC_OFFSET = 10L;

    //-----------------------------------------------------------------------
    @Test
    public void test_toInstant() {
        for (int i = -1000; i < 1000; i++) {
            for (int j = 0; j < 10; j++) {
                // baseSeconds: seconds elapsed from the TAI epoch, expressed on the UTC-SLS timeline (no TAI-UTC offset).
                long baseSeconds = (long) i * 24 * 60 * 60 + j;

                // The TAI instant sits INITIAL_TAI_UTC_OFFSET seconds ahead of the equivalent UTC moment.
                TaiInstant taiInstant = TaiInstant.ofTaiSeconds(baseSeconds + INITIAL_TAI_UTC_OFFSET, 2);

                // The expected Java Instant is baseSeconds after the TAI epoch expressed as a Unix timestamp.
                Instant expected = Instant.ofEpochSecond(TAI_EPOCH_UNIX_TIMESTAMP + baseSeconds).plusNanos(2);

                assertEquals(expected, taiInstant.toInstant());
            }
        }
    }
}
