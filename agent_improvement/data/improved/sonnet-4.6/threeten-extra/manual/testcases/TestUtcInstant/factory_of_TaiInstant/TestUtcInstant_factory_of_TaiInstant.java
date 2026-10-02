package org.threeten.extra.scale;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestUtcInstant_factory_of_TaiInstant {

    // MJD of 1958-01-01, which is the conventional start of the TAI time-scale
    private static final long MJD_TAI_EPOCH = 36204;

    // Before any leap seconds were introduced (pre-1972), TAI ran exactly 10 seconds
    // ahead of UTC.  Every TAI instant must have this offset subtracted to arrive at
    // the corresponding UTC instant.
    private static final long INITIAL_TAI_UTC_OFFSET_SECS = 10;

    private static final long SECS_PER_DAY = 24L * 60 * 60;

    private static final long NANOS_PER_SEC = 1_000_000_000L;

    /**
     * Verifies that {@link UtcInstant#of(TaiInstant)} correctly converts TAI instants
     * to UTC instants across a wide range of days and intra-day seconds.
     *
     * <p>For days in the pre-leap-second era the TAI–UTC difference is exactly
     * {@value #INITIAL_TAI_UTC_OFFSET_SECS} seconds, so a TAI instant at second
     * {@code (dayIndex * SECS_PER_DAY + secondIndex + INITIAL_TAI_UTC_OFFSET_SECS)}
     * should map to the UTC instant at MJD {@code MJD_TAI_EPOCH + dayIndex},
     * nano-of-day {@code secondIndex * NANOS_PER_SEC + extraNanos}.
     */
    @Test
    public void factory_of_TaiInstant() {
        final long extraNanos = 2L;

        for (int dayIndex = -1000; dayIndex < 1000; dayIndex++) {
            for (int secondIndex = 0; secondIndex < 10; secondIndex++) {
                long taiSeconds = (long) dayIndex * SECS_PER_DAY + secondIndex + INITIAL_TAI_UTC_OFFSET_SECS;
                TaiInstant tai = TaiInstant.ofTaiSeconds(taiSeconds, extraNanos);

                long expectedMjDay = MJD_TAI_EPOCH + dayIndex;
                long expectedNanoOfDay = secondIndex * NANOS_PER_SEC + extraNanos;
                UtcInstant expected = UtcInstant.ofModifiedJulianDay(expectedMjDay, expectedNanoOfDay);

                assertEquals(expected, UtcInstant.of(tai));
            }
        }
    }
}
