package org.threeten.extra.scale;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Instant;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link TaiInstant#toInstant()}.
 */
public class TestTaiInstant_test_toInstant {

    /** Number of SI seconds in one day (no leap seconds on the TAI scale). */
    private static final int SECONDS_PER_DAY = 24 * 60 * 60;

    /**
     * Offset, in seconds, between the {@code Instant} (UTC) epoch (1970-01-01)
     * and the day on the TAI time-line that corresponds to it.
     */
    private static final long INSTANT_EPOCH_OFFSET_SECONDS = -378691200L;

    /**
     * The TAI-minus-UTC offset (in seconds) applied at the start of the scanned
     * range, so that each TAI instant maps onto the expected {@code Instant}.
     */
    private static final long TAI_UTC_OFFSET_SECONDS = 10L;

    /** A fixed sub-second amount carried through the conversion unchanged. */
    private static final int NANO_OF_SECOND = 2;

    @Test
    public void test_toInstant() {
        // Sweep a wide range of whole days around the epoch, plus a few seconds
        // within each day, and confirm every TAI instant converts to the
        // matching UTC Instant.
        for (int dayOffset = -1000; dayOffset < 1000; dayOffset++) {
            for (int secondOfDay = 0; secondOfDay < 10; secondOfDay++) {
                long instantEpochSecond =
                        INSTANT_EPOCH_OFFSET_SECONDS + (long) dayOffset * SECONDS_PER_DAY + secondOfDay;
                Instant expected = Instant.ofEpochSecond(instantEpochSecond).plusNanos(NANO_OF_SECOND);

                long taiSeconds = (long) dayOffset * SECONDS_PER_DAY + secondOfDay + TAI_UTC_OFFSET_SECONDS;
                TaiInstant test = TaiInstant.ofTaiSeconds(taiSeconds, NANO_OF_SECOND);

                assertEquals(expected, test.toInstant());
            }
        }
    }
}
