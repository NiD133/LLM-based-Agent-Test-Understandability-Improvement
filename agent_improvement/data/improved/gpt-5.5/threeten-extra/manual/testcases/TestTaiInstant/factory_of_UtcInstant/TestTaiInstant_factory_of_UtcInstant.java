package org.threeten.extra.scale;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestTaiInstant_factory_of_UtcInstant {

    private static final int DAYS_AROUND_TAI_EPOCH = 1000;
    private static final int BASE_MODIFIED_JULIAN_DAY = 36204;
    private static final int HOURS_PER_DAY = 24;
    private static final int MINUTES_PER_HOUR = 60;
    private static final int SECONDS_PER_MINUTE = 60;
    private static final int SECONDS_PER_DAY = HOURS_PER_DAY * MINUTES_PER_HOUR * SECONDS_PER_MINUTE;
    private static final long NANOS_PER_SECOND = 1_000_000_000L;
    private static final long UTC_NANO_OFFSET = 2L;
    private static final int TAI_OFFSET_AT_TEST_RANGE = 10;

    //-----------------------------------------------------------------------
    @Test
    public void factory_of_UtcInstant() {
        for (int dayOffset = -DAYS_AROUND_TAI_EPOCH; dayOffset < DAYS_AROUND_TAI_EPOCH; dayOffset++) {
            for (int secondOfDay = 0; secondOfDay < TAI_OFFSET_AT_TEST_RANGE; secondOfDay++) {
                TaiInstant test = TaiInstant.of(
                        UtcInstant.ofModifiedJulianDay(
                                BASE_MODIFIED_JULIAN_DAY + dayOffset,
                                secondOfDay * NANOS_PER_SECOND + UTC_NANO_OFFSET));

                assertEquals(expectedTaiSecond(dayOffset, secondOfDay), test.getTaiSeconds());
                assertEquals(UTC_NANO_OFFSET, test.getNano());
            }
        }
    }

    private static long expectedTaiSecond(int dayOffset, int secondOfDay) {
        return dayOffset * SECONDS_PER_DAY + secondOfDay + TAI_OFFSET_AT_TEST_RANGE;
    }
}
