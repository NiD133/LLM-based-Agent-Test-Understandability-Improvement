package org.threeten.extra.scale;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestTaiInstant_factory_of_UtcInstant {

    // MJD of 1961-01-01, the date UTC was formally defined and TAI-UTC offset was set to 10 seconds
    private static final long UTC_EPOCH_MJD = 36204L;
    // TAI was defined to be 10 seconds ahead of UTC at the UTC epoch (1961-01-01)
    private static final int INITIAL_TAI_UTC_OFFSET_SECONDS = 10;

    private static final int SECONDS_PER_DAY = 24 * 60 * 60;

    @Test
    public void factory_of_UtcInstant() {
        for (int dayOffset = -1000; dayOffset < 1000; dayOffset++) {
            for (int secondOfDay = 0; secondOfDay < 10; secondOfDay++) {
                // Build a UTC instant: (UTC_EPOCH_MJD + dayOffset) days, secondOfDay seconds + 2 nanos
                UtcInstant utcInstant = UtcInstant.ofModifiedJulianDay(
                        UTC_EPOCH_MJD + dayOffset,
                        secondOfDay * 1_000_000_000L + 2L);

                TaiInstant test = TaiInstant.of(utcInstant);

                // TAI = UTC + (days-from-epoch * seconds-per-day) + second-of-day + initial-offset
                long expectedTaiSeconds = (long) dayOffset * SECONDS_PER_DAY
                        + secondOfDay
                        + INITIAL_TAI_UTC_OFFSET_SECONDS;
                assertEquals(expectedTaiSeconds, test.getTaiSeconds());
                assertEquals(2, test.getNano());
            }
        }
    }
}
