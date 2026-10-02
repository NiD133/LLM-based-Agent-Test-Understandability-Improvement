package org.threeten.extra.scale;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link TaiInstant#of(UtcInstant)}.
 */
public class TestTaiInstant_factory_of_UtcInstant {

    /** Modified Julian Day of 1958-01-01, the TAI epoch (0 TAI seconds). */
    private static final long MJD_TAI_EPOCH = 36204;
    /** Nanoseconds in one second. */
    private static final long NANOS_PER_SECOND = 1_000_000_000L;
    /** Seconds in one day. */
    private static final long SECONDS_PER_DAY = 24 * 60 * 60;
    /** TAI - UTC offset (in seconds) applied by the system rules near the TAI epoch. */
    private static final long TAI_UTC_OFFSET_SECONDS = 10;
    /** A fixed sub-second nanosecond component used for every conversion below. */
    private static final long NANO_OF_SECOND = 2L;

    @Test
    public void factory_of_UtcInstant() {
        // Sweep a range of days around the TAI epoch and a few whole seconds within each day.
        for (int dayOffset = -1000; dayOffset < 1000; dayOffset++) {
            for (int secondOfDay = 0; secondOfDay < 10; secondOfDay++) {
                long modifiedJulianDay = MJD_TAI_EPOCH + dayOffset;
                long nanoOfDay = secondOfDay * NANOS_PER_SECOND + NANO_OF_SECOND;

                TaiInstant test = TaiInstant.of(UtcInstant.ofModifiedJulianDay(modifiedJulianDay, nanoOfDay));

                long expectedTaiSeconds =
                        dayOffset * SECONDS_PER_DAY + secondOfDay + TAI_UTC_OFFSET_SECONDS;
                assertEquals(expectedTaiSeconds, test.getTaiSeconds());
                assertEquals(NANO_OF_SECOND, test.getNano());
            }
        }
    }
}
