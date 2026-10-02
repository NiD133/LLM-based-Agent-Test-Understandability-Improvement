package org.threeten.extra.scale;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link UtcInstant#of(TaiInstant)}, the factory that converts a
 * {@link TaiInstant} into the equivalent {@link UtcInstant}.
 */
public class TestUtcInstant_factory_of_TaiInstant {

    private static final long SECS_PER_DAY = 24L * 60 * 60;
    private static final long NANOS_PER_SEC = 1_000_000_000L;

    // Modified Julian Day that corresponds to TAI second 10 (the reference point
    // the conversion is anchored to in the loop below).
    private static final long MJD_REFERENCE = 36204;

    // TAI offset, in seconds, that lines up with MJD_REFERENCE at nano-of-day 2.
    private static final long TAI_SECONDS_AT_REFERENCE = 10;

    // Fixed nanosecond fraction used for both the TAI input and the expected UTC result.
    private static final int NANO_FRACTION = 2;

    @Test
    public void factory_of_TaiInstant() {
        // Sweep a range of whole days around the reference day, and within each day
        // a few whole-second offsets, checking that converting the TAI instant back
        // to UTC yields the directly-constructed UTC instant for the same point.
        for (int dayOffset = -1000; dayOffset < 1000; dayOffset++) {
            for (int secondOfDay = 0; secondOfDay < 10; secondOfDay++) {
                UtcInstant expected = UtcInstant.ofModifiedJulianDay(
                        MJD_REFERENCE + dayOffset,
                        secondOfDay * NANOS_PER_SEC + NANO_FRACTION);

                TaiInstant tai = TaiInstant.ofTaiSeconds(
                        dayOffset * SECS_PER_DAY + secondOfDay + TAI_SECONDS_AT_REFERENCE,
                        NANO_FRACTION);

                assertEquals(expected, UtcInstant.of(tai));
            }
        }
    }
}
