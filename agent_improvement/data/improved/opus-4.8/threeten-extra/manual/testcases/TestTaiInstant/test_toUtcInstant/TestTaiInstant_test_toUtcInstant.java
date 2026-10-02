package org.threeten.extra.scale;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link TaiInstant#toUtcInstant()}.
 */
public class TestTaiInstant_test_toUtcInstant {

    /** Seconds in a (leap-second free) day, used to step the TAI seconds one day at a time. */
    private static final int SECONDS_PER_DAY = 24 * 60 * 60;

    /**
     * Modified Julian Day that, together with the {@code +10} TAI-seconds offset below,
     * anchors the conversion at a known TAI/UTC alignment point.
     */
    private static final int BASE_MODIFIED_JULIAN_DAY = 36204;

    /** Fixed offset (in TAI seconds) that aligns TAI second 0 with the base MJD. */
    private static final int TAI_TO_UTC_OFFSET_SECONDS = 10;

    /** Constant nano-of-second carried through every conversion in this test. */
    private static final int NANO_OF_SECOND = 2;

    /**
     * Verifies that converting a {@code TaiInstant} to a {@code UtcInstant} yields the
     * expected modified-Julian-day / nano-of-day pair.
     * <p>
     * The test sweeps a wide range of days (1000 days before and after the base day) and,
     * within each day, the first 10 seconds. For every combination the TAI instant is built
     * by stepping whole days plus a few seconds from the base alignment, and the expected
     * UTC instant is built directly from the corresponding modified Julian day and nano-of-day.
     */
    @Test
    public void test_toUtcInstant() {
        for (int dayOffset = -1000; dayOffset < 1000; dayOffset++) {
            for (int secondInDay = 0; secondInDay < 10; secondInDay++) {
                long modifiedJulianDay = BASE_MODIFIED_JULIAN_DAY + dayOffset;
                long nanoOfDay = secondInDay * 1_000_000_000L + NANO_OF_SECOND;
                UtcInstant expected = UtcInstant.ofModifiedJulianDay(modifiedJulianDay, nanoOfDay);

                long taiSeconds = (long) dayOffset * SECONDS_PER_DAY + secondInDay + TAI_TO_UTC_OFFSET_SECONDS;
                TaiInstant test = TaiInstant.ofTaiSeconds(taiSeconds, NANO_OF_SECOND);

                assertEquals(expected, test.toUtcInstant());
            }
        }
    }
}
