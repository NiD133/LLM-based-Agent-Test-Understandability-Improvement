package org.threeten.extra.scale;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Instant;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link UtcInstant#toInstant()}.
 * <p>
 * A {@code UtcInstant} is identified by a Modified Julian Day plus a
 * nanosecond-of-day. This test verifies that converting such an instant back to
 * a {@link Instant} yields the matching epoch-second/nanosecond value, sweeping
 * across a wide range of days and seconds around a fixed reference point.
 */
public class TestUtcInstant_test_toInstant {

    private static final long SECS_PER_DAY = 24L * 60 * 60;
    private static final long NANOS_PER_SEC = 1_000_000_000L;

    // Reference point: 1980-01-01T00:00:00Z, expressed both ways.
    private static final long REFERENCE_MJD = 44239;          // Modified Julian Day of 1980-01-01
    private static final long REFERENCE_EPOCH_SECONDS = 315532800L; // epoch seconds of 1980-01-01

    // A fixed sub-second offset added to every case, to exercise the nanosecond part.
    private static final long EXTRA_NANOS = 2;

    @Test
    public void test_toInstant() {
        // Sweep across roughly +/-1000 days around the reference date...
        for (int dayOffset = -1000; dayOffset < 1000; dayOffset++) {
            // ...and the first 10 seconds within each day.
            for (int secondOfDay = 0; secondOfDay < 10; secondOfDay++) {
                Instant expected = Instant
                        .ofEpochSecond(REFERENCE_EPOCH_SECONDS + dayOffset * SECS_PER_DAY + secondOfDay)
                        .plusNanos(EXTRA_NANOS);

                UtcInstant test = UtcInstant.ofModifiedJulianDay(
                        REFERENCE_MJD + dayOffset,
                        secondOfDay * NANOS_PER_SEC + EXTRA_NANOS);

                assertEquals(expected, test.toInstant());
            }
        }
    }
}
