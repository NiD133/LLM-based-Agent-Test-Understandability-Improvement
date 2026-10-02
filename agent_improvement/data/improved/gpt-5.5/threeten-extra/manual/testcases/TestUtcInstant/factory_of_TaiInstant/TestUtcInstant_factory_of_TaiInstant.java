package org.threeten.extra.scale;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestUtcInstant_factory_of_TaiInstant {

    private static final long UTC_START_MODIFIED_JULIAN_DAY = 36204L;
    private static final long SECONDS_PER_DAY = 24L * 60L * 60L;
    private static final long NANOS_PER_SECOND = 1_000_000_000L;
    private static final long TAI_UTC_OFFSET_SECONDS = 10L;
    private static final int TAI_NANO_ADJUSTMENT = 2;

    @Test
    public void factory_of_TaiInstant() {
        for (int dayOffset = -1000; dayOffset < 1000; dayOffset++) {
            for (int secondOffset = 0; secondOffset < 10; secondOffset++) {
                UtcInstant expectedUtcInstant = UtcInstant.ofModifiedJulianDay(
                        UTC_START_MODIFIED_JULIAN_DAY + dayOffset,
                        secondOffset * NANOS_PER_SECOND + TAI_NANO_ADJUSTMENT);
                TaiInstant taiInstant = TaiInstant.ofTaiSeconds(
                        dayOffset * SECONDS_PER_DAY + secondOffset + TAI_UTC_OFFSET_SECONDS,
                        TAI_NANO_ADJUSTMENT);

                assertEquals(expectedUtcInstant, UtcInstant.of(taiInstant));
            }
        }
    }
}
