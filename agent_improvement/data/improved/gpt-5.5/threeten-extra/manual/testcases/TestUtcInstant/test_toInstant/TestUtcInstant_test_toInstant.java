package org.threeten.extra.scale;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Instant;

import org.junit.jupiter.api.Test;

public class TestUtcInstant_test_toInstant {

    private static final long SECS_PER_DAY = 24L * 60L * 60L;
    private static final long NANOS_PER_SEC = 1_000_000_000L;

    private static final long MJD_1980_01_01 = 44239L;
    private static final long EPOCH_SECONDS_1980_01_01 = 315_532_800L;
    private static final long TWO_NANOS = 2L;

    @Test
    public void test_toInstant() {
        for (int dayOffset = -1000; dayOffset < 1000; dayOffset++) {
            for (int secondsIntoDay = 0; secondsIntoDay < 10; secondsIntoDay++) {
                Instant expected = Instant
                        .ofEpochSecond(EPOCH_SECONDS_1980_01_01 + dayOffset * SECS_PER_DAY + secondsIntoDay)
                        .plusNanos(TWO_NANOS);
                UtcInstant test = UtcInstant.ofModifiedJulianDay(
                        MJD_1980_01_01 + dayOffset,
                        secondsIntoDay * NANOS_PER_SEC + TWO_NANOS);

                assertEquals(expected, test.toInstant());
            }
        }
    }
}
