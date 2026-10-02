package org.threeten.extra.scale;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link UtcInstant#toTaiInstant()}.
 */
public class TestUtcInstant_test_toTaiInstant {

    private static final long NANOS_PER_SEC = 1_000_000_000L;
    private static final long SECS_PER_DAY = 24L * 60 * 60;

    /**
     * Modified Julian Day 36204 corresponds to 1958-01-01, the epoch at which the
     * TAI - UTC offset is exactly 10 seconds under the system leap-second rules.
     */
    private static final long MJD_TAI_EPOCH = 36204;
    private static final long TAI_UTC_OFFSET_SECONDS = 10;

    /**
     * Converting a UTC instant to TAI must preserve the nanosecond fraction and apply
     * the constant 10-second offset, with each Modified Julian Day mapping to a full
     * day of TAI seconds.
     */
    @Test
    public void test_toTaiInstant() {
        for (int dayOffset = -1000; dayOffset < 1000; dayOffset++) {
            for (int extraSeconds = 0; extraSeconds < 10; extraSeconds++) {
                long mjDay = MJD_TAI_EPOCH + dayOffset;
                long nanoOfDay = extraSeconds * NANOS_PER_SEC + 2L;
                UtcInstant utc = UtcInstant.ofModifiedJulianDay(mjDay, nanoOfDay);

                TaiInstant tai = utc.toTaiInstant();

                long expectedTaiSeconds =
                        dayOffset * SECS_PER_DAY + extraSeconds + TAI_UTC_OFFSET_SECONDS;
                assertEquals(expectedTaiSeconds, tai.getTaiSeconds());
                assertEquals(2, tai.getNano());
            }
        }
    }
}
