package org.threeten.extra.scale;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Instant;

import org.junit.jupiter.api.Test;

public class TestUtcInstant_test_toInstant {

    // MJD 44239 corresponds to 1980-01-01 (Unix epoch + 3652 days)
    private static final long MJD_1980_01_01 = 44239;

    // Unix epoch seconds at 1980-01-01T00:00:00Z (3652 days * 86400 s/day)
    private static final long EPOCH_SECOND_1980_01_01 = 315532800L;

    private static final long SECS_PER_DAY = 24L * 60 * 60;

    private static final long NANOS_PER_SEC = 1_000_000_000L;

    @Test
    public void test_toInstant() {
        for (int i = -1000; i < 1000; i++) {
            for (int j = 0; j < 10; j++) {
                Instant expected = Instant.ofEpochSecond(EPOCH_SECOND_1980_01_01 + i * SECS_PER_DAY + j).plusNanos(2);
                UtcInstant test = UtcInstant.ofModifiedJulianDay(MJD_1980_01_01 + i, j * NANOS_PER_SEC + 2);
                assertEquals(expected, test.toInstant());
            }
        }
    }
}
